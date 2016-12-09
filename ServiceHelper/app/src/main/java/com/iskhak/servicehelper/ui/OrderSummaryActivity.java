package com.iskhak.servicehelper.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.iskhak.servicehelper.R;

public class OrderSummaryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
    }

    public void onCancelButton(View view){
        finish();
    }

    public void onFinishButton(View view){
        finish();
    }
}
