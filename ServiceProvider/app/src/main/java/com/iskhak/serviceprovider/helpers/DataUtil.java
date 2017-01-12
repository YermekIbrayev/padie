package com.iskhak.serviceprovider.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtil {
    public static String updateFormat(Date date, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
}
