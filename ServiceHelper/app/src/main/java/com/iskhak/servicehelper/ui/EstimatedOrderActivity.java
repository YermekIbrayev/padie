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

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class EstimatedOrderActivity extends BaseActivity {

    @Inject
    DataManager mDataManager;
    private Subscription mSubscribtion;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_estamated_order);

        TextView totalEstimated = (TextView)findViewById(R.id.totalEstimated);
        int totalSum = getIntent().getIntExtra("TotalSum", 0);
        totalEstimated.setText("$"+totalSum);
        mContext = this;
        Timber.d("Created");
    }

    public void onCancelButton(View view){
        finish();
    }

    public void onOrderButton(View view){
        if(mSubscribtion!=null && !mSubscribtion.isUnsubscribed()) mSubscribtion.unsubscribe();
        mSubscribtion = mDataManager.sendOrder(DataHolder.getInstance().getOrder())
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
    }

    private void showDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext)
                .setTitle(getString(R.string.thanks_title))
                .setMessage(getString(R.string.thanks_content))
                .setNeutralButton(R.string.dialog_action_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(mContext, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
        alertDialog.create().show();
        Timber.d("Dialog showed");
    }
}
