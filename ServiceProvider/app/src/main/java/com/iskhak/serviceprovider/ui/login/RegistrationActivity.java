package com.iskhak.serviceprovider.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.iskhak.serviceprovider.R;
import com.iskhak.serviceprovider.ui.BaseActivity;

public class RegistrationActivity extends BaseActivity {

    public static Intent newStartIntent(Context context) {
        Intent intent = new Intent(context, RegistrationActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }
}
