package com.iskhak.servicehelper.ui.menu;

import android.os.Bundle;

import com.iskhak.servicehelper.R;

public class OrdersActivity extends BasicSettingsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        setSubtitleText("My Orders");
    }
}