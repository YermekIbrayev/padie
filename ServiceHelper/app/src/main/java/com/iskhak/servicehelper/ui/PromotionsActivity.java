package com.iskhak.servicehelper.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.iskhak.servicehelper.R;

public class PromotionsActivity extends BasicSettingsActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotions);
        setSubtitleText("Promotions");
    }
}