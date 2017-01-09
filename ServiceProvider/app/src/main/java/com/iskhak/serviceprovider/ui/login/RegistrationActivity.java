package com.iskhak.serviceprovider.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.iskhak.serviceprovider.R;
import com.iskhak.serviceprovider.helpers.DialogFactory;
import com.iskhak.serviceprovider.ui.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import timber.log.Timber;

public class RegistrationActivity extends BaseActivity {

    @BindView(R.id.registration_password)
    TextView passwordTV;
    @BindView(R.id.registration_confirm_password)
    TextView confirmPasswordTV;

    public static Intent newStartIntent(Context context) {
        Intent intent = new Intent(context, RegistrationActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
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
        validConfirmPassword();
        Timber.d("test");
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
            DialogFactory.createGenericErrorDialog(RegistrationActivity.this, R.string.passwords_not_equal).show();
        return result;
    }
}
