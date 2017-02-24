package com.iskhak.servicehelper.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.iskhak.servicehelper.R;
import com.iskhak.servicehelper.data.model.PackageModel;
import com.iskhak.servicehelper.ui.base.BaseActivity;

public class VerifyActivity extends BaseActivity {

    public static Intent newStartIntent(Context context, PackageModel order) {
        Intent intent = new Intent(context, VerifyActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
    }
}
