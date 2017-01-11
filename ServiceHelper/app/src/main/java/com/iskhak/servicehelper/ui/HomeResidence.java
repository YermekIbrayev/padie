package com.iskhak.servicehelper.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.iskhak.servicehelper.helpers.DataHolder;
import com.iskhak.servicehelper.R;
import com.iskhak.servicehelper.data.model.ServiceGroup;
import com.iskhak.servicehelper.ui.base.BaseActivity;

import java.util.List;
import java.util.Map;

public class HomeResidence extends BaseActivity {
    Context context;
    ServiceGroupAdapter dataAdapter;
    Map<Integer, ServiceGroup> serviceGroupNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_residence);
        context = this;
        TextView titleTextView = (TextView) findViewById(R.id.titleTextView);
        Typeface arcater= Typeface.createFromAsset(getAssets(),  "fonts/ARCARTER.ttf");
        titleTextView.setTextSize(30.0f);
        titleTextView.setTypeface(arcater);
        displayListView();
    }

    private void initList(){
        serviceGroupNames = DataHolder.getInstance().getServiceGroupNames();
    }

    private void displayListView(){
        initList();
        dataAdapter = new ServiceGroupAdapter(this, R.layout.service_item_activity, serviceGroupNames);
        ListView serviceListView = (ListView) findViewById(R.id.service_class_lv);
        serviceListView.setAdapter(dataAdapter);
    }
}
