package com.iskhak.serviceprovider.data.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PathDate {
    private static final ThreadLocal<DateFormat> DF = new ThreadLocal<DateFormat>() {
        @Override public DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
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