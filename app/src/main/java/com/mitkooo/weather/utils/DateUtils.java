package com.mitkooo.weather.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


//DateUtils is used for working with dates, including conversions and formatting
public class  DateUtils {

    public static Date stringToDate(String dateString) {
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

            return formatter.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String dateToString(Date date) {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        return df.format(date);
    }

    public static String getCurrentDateToStringForURL() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        return df.format(new Date());
    }

    public static String getNextWeekDateToStringForURL() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        //current date
        Calendar currentCal = Calendar.getInstance();
        currentCal.setTime(new Date());

        //new date with 7 days ahead;
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, currentCal.get(Calendar.DAY_OF_MONTH) + 7); //move date with 7 days

        Date d = cal.getTime();

        return df.format(d);
    }

}
