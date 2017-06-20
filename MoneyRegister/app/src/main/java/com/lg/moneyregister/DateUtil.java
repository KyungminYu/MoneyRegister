package com.lg.moneyregister;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    private static final String DATE_FORMAT = "yyyy.MM.dd";

    public static String getToday() {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.KOREA);
        Date currentTime = new Date();
        return mSimpleDateFormat.format(currentTime);
    }
    public static long getDate(String date_str){
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.KOREA);
        long date_long = 0;
        try {
            Date date = mSimpleDateFormat.parse(date_str);
            date_long = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date_long;
    }
    public static String getDate(long date_long){
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.KOREA);
        Date currentTime = new Date(date_long);
        return mSimpleDateFormat.format(currentTime);
    }
}
