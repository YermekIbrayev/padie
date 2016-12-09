package com.iskhak.servicehelper.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.iskhak.servicehelper.data.DataManager;
import com.iskhak.servicehelper.data.model.PackageModel;
import com.iskhak.servicehelper.data.remote.ConnectionService;
import com.iskhak.servicehelper.helpers.DataHolder;
import com.iskhak.servicehelper.helpers.NetworkHelper;
import com.iskhak.servicehelper.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class CleaningAddressActivity extends BaseActivity {

    private static final String DATE_FORMAT="MM/dd/yy";
    private static final String TIME_FORMAT="h:mm a";
    @Inject
    DataManager mDataManager;
    private Subscription mSubscribtion;

    EditText dateEdit;
    EditText timeEdit;
    EditText orderNotesEdit;
    EditText addressEdit;

    Calendar dateCalendar = Calendar.getInstance();
    Calendar timeCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateCalendar.set(Calendar.YEAR, year);
            dateCalendar.set(Calendar.MONTH, monthOfYear);
            dateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateFormat(dateEdit, DATE_FORMAT);
        }
    };

    TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            timeCalendar.set(Calendar.HOUR, hourOfDay);
            timeCalendar.set(Calendar.MINUTE, minute);
            updateFormat(timeEdit, TIME_FORMAT);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_cleaning_address);
        dateEdit = (EditText) findViewById(R.id.dateEdit);
        timeEdit = (EditText) findViewById(R.id.timeEdit);
        orderNotesEdit = (EditText) findViewById(R.id.orderNotesEdit) ;
        addressEdit = (EditText) findViewById(R.id.addressEdit);
        addressEdit.setText(DataHolder.getInstance().getUserPreferences().getCurrentUserAddress());
    }

    public void onBackButton(View view){
        finish();
    }

    public void onNextButton(View view){
        DateFormat df = new SimpleDateFormat(DATE_FORMAT+" "+TIME_FORMAT);
        Date orderDate = new Date(0);

        try {
            orderDate= df.parse(dateEdit.getText().toString()+" "+timeEdit.getText().toString());
            String newDateString = df.format(orderDate);
            System.out.println(newDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DataHolder.getInstance().setOrderDate(orderDate);
        DataHolder.getInstance().setOrderNote(orderNotesEdit.getText().toString());
        DataHolder.getInstance().setOrderAddress(addressEdit.getText().toString());
        if (DataHolder.getInstance().getNetworkHelper().isNetworkConnected(this)) {
            //DataHolder.getInstance().getNetworkHelper().sendJSON();
            if(mSubscribtion!=null && !mSubscribtion.isUnsubscribed()) mSubscribtion.unsubscribe();
            mSubscribtion = mDataManager.sendOrder(DataHolder.getInstance().generatePackageModel())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<PackageModel>() {
                        @Override
                        public void onCompleted() {
                            Timber.i("got packageModel");

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onNext(PackageModel packageModel) {
                            Timber.i(packageModel.toString());
                            Intent intent = new Intent(getApplicationContext(), EstimatedOrderActivity.class);
                            int totalSum = packageModel.price().intValue();
                            intent.putExtra("TotalSum", totalSum);
                            startActivity(intent);
                        }
                    });
        }

        Intent intent = new Intent(this, GettingDataActivity.class);
        startActivity(intent);

    }

    private void updateFormat(EditText view, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        view.setText(sdf.format(dateCalendar.getTime()));
    }

    public void onDateClick(View view){
        DatePickerDialog datePickerDialog = new DatePickerDialog(CleaningAddressActivity.this, dateListener, dateCalendar.get(Calendar.YEAR),dateCalendar.get(Calendar.MONTH), dateCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        datePickerDialog.show();
    }

    public void onTimeClick(View view){
        new TimePickerDialog(CleaningAddressActivity.this, timeListener, timeCalendar.get(Calendar.HOUR), timeCalendar.get(Calendar.MINUTE), false).show();
    }
}
