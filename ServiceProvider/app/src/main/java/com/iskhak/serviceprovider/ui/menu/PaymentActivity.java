package com.iskhak.serviceprovider.ui.menu;

import android.os.Bundle;

import com.iskhak.serviceprovider.R;

public class PaymentActivity extends BasicSettingsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        setSubtitleText("Payment");
    }
}
