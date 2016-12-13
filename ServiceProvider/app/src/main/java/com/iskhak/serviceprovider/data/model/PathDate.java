package com.iskhak.serviceprovider.data.model;

import com.iskhak.serviceprovider.helpers.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PathDate {
    private static final ThreadLocal<DateFormat> DF = new ThreadLocal<DateFormat>() {
        @Override public DateFormat initialValue() {
            return new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
        }
    };

    private final Date date;

    public PathDate(Date date) {
        this.date = date;
    }

    @Override public String toString() {
        return DF.get().format(date);
    }
}
