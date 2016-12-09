package com.iskhak.serviceprovider.ui;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.iskhak.serviceprovider.R;
import com.iskhak.serviceprovider.data.model.PackageModel;
import com.iskhak.serviceprovider.data.model.RequestElement;

import static com.iskhak.serviceprovider.helpers.DataHolder.DATE_FORMAT;
import static com.iskhak.serviceprovider.helpers.DataHolder.TIME_FORMAT;
import static com.iskhak.serviceprovider.helpers.DataHolder.updateFormat;

public class RequestHolder {
    private TextView jobName;
    private TextView jobAddress;
    private TextView jobDate;
    private TextView jobTime;
    private TextView jobPrice;

    private final static String DATE_PREF = "Date: ";
    private final static String TIME_PREF = "Time: ";
    private final static String JOB_COUNT_PREF = "Jobs Count: ";
    private final static String ADDRESS_PREF = "Address: ";
    private final static String PRICE_PREF = "Price $";

    public RequestHolder(View view){
        jobName = (TextView) view.findViewById(R.id.job_name_tv);
        jobAddress = (TextView) view.findViewById(R.id.job_address_tv);
        jobDate = (TextView) view.findViewById(R.id.job_date_tv);
        jobTime = (TextView) view.findViewById(R.id.job_time_tv);
        jobPrice = (TextView) view.findViewById(R.id.job_price_tv);

    }

    public void setViewsText(PackageModel order){
        jobName.setText(JOB_COUNT_PREF+order.selectedItems().size());
        jobAddress.setText(ADDRESS_PREF+order.address());
        jobDate.setText(DATE_PREF+updateFormat( order.orderDate(), DATE_FORMAT));
        jobTime.setText(TIME_PREF+updateFormat( order.orderDate(), TIME_FORMAT));
        jobPrice.setText(PRICE_PREF+order.price());
    }

}
