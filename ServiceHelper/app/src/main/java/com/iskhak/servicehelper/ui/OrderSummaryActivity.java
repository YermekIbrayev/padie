package com.iskhak.servicehelper.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.iskhak.servicehelper.R;
import com.iskhak.servicehelper.data.DataManager;
import com.iskhak.servicehelper.data.model.PackageModel;
import com.iskhak.servicehelper.helpers.DataHolder;
import com.iskhak.servicehelper.ui.base.BaseActivity;
import com.iskhak.servicehelper.ui.chooseprovider.ChooseProviderActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class OrderSummaryActivity extends BaseActivity {

    @BindView(R.id.companyNameTV)
    TextView companyNameTV;
    @BindView(R.id.orderSummaryTV)
    TextView orderSummaryTV;

    @Inject
    DataManager mDataManager;
    private Subscription mSubscription;
    private Context mContext;

    public static Intent getStartIntent(Context context){
        Intent intent = new Intent(context, OrderSummaryActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_order_summary);
        ButterKnife.bind(this);
        companyNameTV.setText(DataHolder.getInstance().getProvider().name());
        orderSummaryTV.setText(DataHolder.getInstance().getSelectedService());
        mContext = this;
    }

    @OnClick(R.id.order_summary_submit_button)
    public void onFinishButton(View view){
        if(mSubscription!=null && !mSubscription.isUnsubscribed()) mSubscription.unsubscribe();
        mSubscription = mDataManager.sendOrder(DataHolder.getInstance().getOrder())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PackageModel>() {
                    @Override
                    public void onCompleted() {
                        Timber.i("got packageModel");
                        showDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(PackageModel packageModel) {
                        Timber.i(packageModel.toString());
                        DataHolder.getInstance().setOrder(packageModel);
                    }
                });
        //startActivity(LoadActivity.getStartIntent(this, LoadActivity.PROCESSING));
    }

    @OnClick(R.id.order_summary_cancel_button)
    public void onCancelButton(View view){
        onBackPressed();
    }

    ///private functions
    private void showDialog(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext)
                .setTitle(getString(R.string.thanks_title))
                .setMessage(getString(R.string.thanks_content))
                .setNeutralButton(R.string.dialog_action_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(MainActivity.newStartIntent(mContext));
                        finish();
                    }
                });
        alertDialog.create().show();
        Timber.d("Dialog showed");
    }
}
