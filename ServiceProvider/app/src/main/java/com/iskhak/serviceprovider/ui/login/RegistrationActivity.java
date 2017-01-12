package com.iskhak.serviceprovider.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.iskhak.serviceprovider.R;
import com.iskhak.serviceprovider.data.DataManager;
import com.iskhak.serviceprovider.data.model.LoginInfo;
import com.iskhak.serviceprovider.data.model.TokenModel;
import com.iskhak.serviceprovider.helpers.DataHolder;
import com.iskhak.serviceprovider.helpers.DialogFactory;
import com.iskhak.serviceprovider.helpers.RxUtil;
import com.iskhak.serviceprovider.ui.base.BaseActivity;
import com.iskhak.serviceprovider.ui.orders.activity.OrdersActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import retrofit2.Response;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class RegistrationActivity extends BaseActivity {

    @BindView(R.id.registration_email)
    TextView emailTV;
    @BindView(R.id.registration_username)
    TextView usernameTV;
    @BindView(R.id.registration_first_name)
    TextView firstnameTV;
    @BindView(R.id.registration_last_name)
    TextView lastnameTV;
    @BindView(R.id.registration_password)
    TextView passwordTV;
    @BindView(R.id.registration_confirm_password)
    TextView confirmPasswordTV;

    @Inject
    DataManager mDataManager;
    Subscription mSubscription;

    Context mContext;

    public static Intent newStartIntent(Context context) {
        Intent intent = new Intent(context, RegistrationActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        mContext = RegistrationActivity.this;
    }

    @OnFocusChange(R.id.registration_confirm_password)
    void onConfirmFocusChange(boolean focused){
        Timber.d("here");
        if(!focused){
            validConfirmPassword();
        }
    }

    @OnClick(R.id.registration_button)
    void onRegistrationButton(){
        if( !validConfirmPassword()){
            return;
        }

        LoginInfo loginInfo = LoginInfo.builder()
                .setEmail(emailTV.getText().toString())
                .setUsername(usernameTV.getText().toString())
                .setFirstname(firstnameTV.getText().toString())
                .setLastname(lastnameTV.getText().toString())
                .setPassword(passwordTV.getText().toString())
                .build();
        mSubscription = mDataManager.register(loginInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<TokenModel>>() {
                    @Override
                    public void onCompleted() {
                        RxUtil.unsubscribe(mSubscription);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "on login");
                    }

                    @Override
                    public void onNext(Response<TokenModel> token) {
                        if(token.body()!=null)
                            Timber.d(token.body().token());
                        //showProgress(false);
                        if(token.code()!=200){
                            Timber.d("response bad");
                        } else{
                            Timber.d("response ok");
                            DataHolder.getInstance().setToken(token.body());
                            mContext.startActivity(OrdersActivity.newStartIntent(mContext, null));
                        }
                    }
                });
        Timber.d("test: " + loginInfo.toString());
    }


    // private functions
    boolean validConfirmPassword(){
        boolean result = true;
        if(TextUtils.isEmpty(confirmPasswordTV.getText())&&!TextUtils.isEmpty(passwordTV.getText()))
            result = false;
        if(!result&&!confirmPasswordTV.getText().equals(passwordTV.getText())){
            confirmPasswordTV.requestFocus();
            result = false;
        }
        if(!result)
            DialogFactory.createGenericErrorDialog(mContext, R.string.passwords_not_equal).show();
        return result;
    }
}
