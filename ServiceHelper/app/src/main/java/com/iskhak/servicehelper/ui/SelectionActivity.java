package com.iskhak.servicehelper.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.iskhak.servicehelper.helpers.DataHolder;
import com.iskhak.servicehelper.R;
import com.iskhak.servicehelper.data.model.ServiceItem;
import com.iskhak.servicehelper.extra.ServiceItemAdapter;
import com.iskhak.servicehelper.ui.base.BaseActivity;

import java.util.List;

public abstract class SelectionActivity extends BaseActivity {

    ServiceItemAdapter dataAdapter;
    List<ServiceItem> serviceList;
    enum State  {SELECTION, EXTRA};
    State currentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void init(int contentViewID, State currentState){
        this.currentState = currentState;
        setContentView(contentViewID);
        TextView titleTextView = (TextView) findViewById(R.id.titleTextView);
        titleTextView.setText(DataHolder.getInstance().getCurrentServiceName());
        TextView activityCaptionTV = (TextView) findViewById(R.id.activityCaptionTV);
        activityCaptionTV.setText(getCaptionText());
        displayListView();
    }

    private void displayListView(){
        serviceList = getItems();
        dataAdapter = new ServiceItemAdapter(this, R.layout.service_item_activity, serviceList, this);
        final ListView serviceListView = (ListView) findViewById(R.id.serviceListView);
        serviceListView.setAdapter(dataAdapter);
    }

    public abstract List<ServiceItem> getItems();

    public void onBackButton(View view){
        finish();
    }

    public abstract void addSelectionID(int id, int pid);

    public abstract void onNextButton(View view);

    protected abstract String getCaptionText();
}
