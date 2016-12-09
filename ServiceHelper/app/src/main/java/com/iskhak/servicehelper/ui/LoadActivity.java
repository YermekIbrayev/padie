package com.iskhak.servicehelper.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.iskhak.servicehelper.data.SyncService;
import com.iskhak.servicehelper.helpers.DataHolder;
import com.iskhak.servicehelper.R;

public class LoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        startService(SyncService.getStartIntent(this));
        DataHolder.getInstance().setContext(this);
    }


}
