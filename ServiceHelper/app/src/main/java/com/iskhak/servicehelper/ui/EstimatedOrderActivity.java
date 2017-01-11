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
import com.iskhak.servicehelper.helpers.DialogFactory;
import com.iskhak.servicehelper.ui.base.BaseActivity;
import com.iskhak.servicehelper.ui.chooseprovider.ChooseProviderActivity;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class EstimatedOrderActivity extends BaseActivity {

    public static final String KEY_TOTAL_SUM = "TotalSum";

    public static Intent getStartIntent(Context context, int totalSum){
        Intent intent = new Intent(context, EstimatedOrderActivity.class);
        intent.putExtra(KEY_TOTAL_SUM, totalSum);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_estamated_order);

        TextView totalEstimated = (TextView)findViewById(R.id.totalEstimated);
        int totalSum = getIntent().getIntExtra(KEY_TOTAL_SUM, 0);
        totalEstimated.setText("$"+totalSum);
        Timber.d("Created");
    }

    public void onCancelButton(View view){
        finish();
    }

    public void onOrderButton(View view){
        Intent intent = ChooseProviderActivity.getStartIntent(this);
                //LoadActivity.getStartIntent(this, LoadActivity.PROCESSING);
        startActivity(intent);
    }


}
