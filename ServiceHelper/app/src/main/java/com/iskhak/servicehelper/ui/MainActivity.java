package com.iskhak.servicehelper.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.iskhak.servicehelper.data.SyncService;
import com.iskhak.servicehelper.helpers.DataHolder;
import com.iskhak.servicehelper.R;


public class MainActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView titleTextView = (TextView) findViewById(R.id.titleTextView);
        Typeface arcater= Typeface.createFromAsset(getAssets(),  "fonts/ARCARTER.ttf");
        titleTextView.setTypeface(arcater);
        titleTextView.setTextSize(30.0f);


    }

    public void gotoResidence(View view){
        Intent intent = new Intent(this, HomeResidence.class);
        DataHolder.getInstance().clearSelected();
        startActivity(intent);
    }

}
