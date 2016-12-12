package com.iskhak.servicehelper.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.iskhak.servicehelper.R;
import com.iskhak.servicehelper.data.DataManager;
import com.iskhak.servicehelper.data.model.PackageModel;
import com.iskhak.servicehelper.helpers.DataHolder;
import com.iskhak.servicehelper.helpers.DialogFactory;

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
                        DialogFactory.createSimpleOkErrorDialog(mContext, R.string.thanks_title, R.string.thanks_content).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(PackageModel packageModel) {
                        Timber.i(packageModel.toString());
                        DataHolder.getInstance().setOrder(packageModel);
                        Intent intent = new Intent(getApplicationContext(), EstimatedOrderActivity.class);
                        int totalSum = packageModel.price().intValue();
                        intent.putExtra("TotalSum", totalSum);
                        startActivity(intent);

                        
                    }
                });
/*        Intent intent = new Intent(this, OrderSummaryActivity.class );
        startActivity(intent);*/
    }
}
