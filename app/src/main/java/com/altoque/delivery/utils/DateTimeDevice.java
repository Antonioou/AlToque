package com.altoque.delivery.utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeDevice {

    public DateTimeDevice() {

    }

    public static Integer getHour() {
        //Date date = new Date(0);
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("HH");
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Bogota"), Locale.getDefault());
        /*calendar.setTime(date);
        calendar.add(Calendar.MINUTE, 30);*/
        String currentHour = df.format(calendar.getTime());
        Integer result = Integer.parseInt(currentHour);
        return 15;
    }

    public static Integer getMinute() {
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("mm");
        //@SuppressLint("SimpleDateFormat") DateFormat d = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Bogota"), Locale.getDefault());
        String currentMin = df.format(calendar.getTime());
        Integer result = Integer.parseInt(currentMin);
        return result;
    }

    public static Integer getSecond() {
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("ss");
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Bogota"), Locale.getDefault());
        String currentSec = df.format(calendar.getTime());
        Integer result = Integer.parseInt(currentSec);
        return result;
    }

    /*public Date getDate() {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        Date date  = Calendar.getInstance().getTime();
        return date;

    }*/


    public static String getDate() {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentdate = df.format(Calendar.getInstance().getTime());
        return currentdate;
    }
}
