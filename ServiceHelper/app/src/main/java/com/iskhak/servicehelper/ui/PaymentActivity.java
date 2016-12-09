package com.iskhak.servicehelper.ui;

import android.os.Bundle;

import com.iskhak.servicehelper.R;
import com.iskhak.servicehelper.ui.BasicSettingsActivity;

public class PaymentActivity extends BasicSettingsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        setSubtitleText("Payment");
    }
}
