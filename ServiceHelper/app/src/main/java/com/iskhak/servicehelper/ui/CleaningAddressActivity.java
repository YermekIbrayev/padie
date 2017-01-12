package com.iskhak.servicehelper.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.iskhak.servicehelper.data.DataManager;
import com.iskhak.servicehelper.data.model.PackageModel;
import com.iskhak.servicehelper.helpers.DataHolder;
import com.iskhak.servicehelper.R;
import com.iskhak.servicehelper.helpers.NetworkUtil;
import com.iskhak.servicehelper.ui.base.BaseActivity;
import com.iskhak.servicehelper.ui.views.MultiLineEditView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscription;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class CleaningAddressActivity extends BaseActivity {

    private static final String DATE_FORMAT="MM/dd/yy";
    private static final String TIME_FORMAT="h:mm a";
    private static final int NOTES_LINE_COUNT = 4;
    @Inject
    DataManager mDataManager;
    private Subscription mSubscribtion;

    @BindView(R.id.dateEdit)
    EditText dateEdit;
    @BindView(R.id.timeEdit)
    EditText timeEdit;
    @BindView(R.id.addressEdit)
    EditText addressEdit;
    MultiLineEditView orderNotesEdit;

    Calendar dateCalendar = Calendar.getInstance();
    Calendar timeCalendar = Calendar.getInstance();

    TimePickerDialog timePicker;
    DatePickerDialog datePicker;

    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateCalendar.set(Calendar.YEAR, year);
            dateCalendar.set(Calendar.MONTH, monthOfYear);
            dateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateFormat(dateEdit, DATE_FORMAT, dateCalendar);
        }
    };

    TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            timeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            timeCalendar.set(Calendar.MINUTE, minute);
/*            timeCalendar.set(Calendar.AM_PM, hourOfDay>12?Calendar.PM:Calendar.AM);*/
            updateFormat(timeEdit, TIME_FORMAT, timeCalendar);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_cleaning_address);
        ButterKnife.bind(this);
        orderNotesEdit = (MultiLineEditView)findViewById(R.id.orderNotesEdit);
        orderNotesEdit.setHorizontallyScrolling(false);
        orderNotesEdit.setMaxLines(Integer.MAX_VALUE);
        orderNotesEdit.setLines(NOTES_LINE_COUNT);
        orderNotesEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.next || id == EditorInfo.IME_NULL) {
                    onNextButton();
                    return true;
                }
                return false;
            }
        });
        addressEdit.setText(DataHolder.getInstance().getUserPreferences().getCurrentUserAddress());
    }

    @OnClick(R.id.backButton)
    public void onBackButton(){
        finish();
    }

    @OnClick(R.id.nextButton)
    public void onNextButton(){
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
        if (NetworkUtil.isNetworkConnected(this)) {
            if(mSubscribtion!=null && !mSubscribtion.isUnsubscribed()) mSubscribtion.unsubscribe();
            mSubscribtion = mDataManager.getOrderPrice(DataHolder.getInstance().generatePackageModel())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<PackageModel>() {
                        @Override
                        public void onCompleted() {
                            Timber.i("got price");
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onNext(PackageModel packageModel) {
                            Timber.i(packageModel.toString());
                            DataHolder.getInstance().setOrder(packageModel);

                            Intent intent = EstimatedOrderActivity.getStartIntent(getApplicationContext(),
                                    packageModel.price().intValue());
                            startActivity(intent);
                        }
                    });
        }

        Intent intent = LoadActivity.getStartIntent(this, LoadActivity.PROCESSING);
        startActivity(intent);

    }

    private void updateFormat(EditText view, String format, Calendar calendar){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        view.setText(sdf.format(calendar.getTime()));
    }

    public void onDateClick(View view){
        if(datePicker == null) {
            datePicker = new DatePickerDialog(
                    CleaningAddressActivity.this,
                    dateListener,
                    dateCalendar.get(Calendar.YEAR),
                    dateCalendar.get(Calendar.MONTH),
                    dateCalendar.get(Calendar.DAY_OF_MONTH)
            );
            datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        }
        datePicker.show();
    }

    public void onTimeClick(View view){
        if(timePicker == null)
            timePicker = new TimePickerDialog(CleaningAddressActivity.this, timeListener, timeCalendar.get(Calendar.HOUR), timeCalendar.get(Calendar.MINUTE), false);
        timePicker.show();
    }
}
