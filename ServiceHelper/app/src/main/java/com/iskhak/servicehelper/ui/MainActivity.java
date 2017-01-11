package com.iskhak.servicehelper.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.iskhak.servicehelper.helpers.DataHolder;
import com.iskhak.servicehelper.R;
import com.iskhak.servicehelper.ui.base.BaseActivity;


public class MainActivity extends BaseActivity {

    public static Intent newStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

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
