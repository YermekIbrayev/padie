package com.iskhak.servicehelper.ui.menu;

import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iskhak.servicehelper.R;

public class BasicSettingsActivity extends AppCompatActivity {

    LinearLayout fullView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID){
        fullView = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_basic_settings, null);
        LinearLayout activityContainer = (LinearLayout) fullView.findViewById(R.id.activity_settings_content);
        getLayoutInflater().inflate(layoutResID,activityContainer, true);
        super.setContentView(fullView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);     //abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView titleTextView = (TextView) findViewById(R.id.titleTextView);
        Typeface arcater= Typeface.createFromAsset(getAssets(),  "fonts/ARCARTER.ttf");
        titleTextView.setTypeface(arcater);
        titleTextView.setTextSize(30.0f);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void setSubtitleText(String text){
        ((TextView)findViewById(R.id.basic_settings_subtitle)).setText(text);
    }
}
