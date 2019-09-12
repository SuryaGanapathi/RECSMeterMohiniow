package com.recsmeterreading.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Converter {

    public static String getEndDate() {
        Calendar calendar = Calendar.getInstance();
        int lastDate = calendar.getActualMaximum(Calendar.DATE);

        calendar.set(Calendar.DATE, lastDate);
        int lastDay = calendar.get(Calendar.DAY_OF_WEEK);

        System.out.println("Last Date: " + calendar.getTime());

        System.out.println("Last Day : " + lastDay);
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

        return dateFormat.format(calendar.getTime());
    }

    public static Date getEndDateOfTheMonth() {
        Calendar calendar = Calendar.getInstance();
        int lastDate = calendar.getActualMaximum(Calendar.DATE);

        calendar.set(Calendar.DATE, lastDate);
        int lastDay = calendar.get(Calendar.DAY_OF_WEEK);

        System.out.println("Last Date: " + calendar.getTime());

        System.out.println("Last Day : " + lastDay);
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

        return calendar.getTime();
    }


    public static String addDays(Date date, int days) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);

        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        return dateFormat.format(cal.getTime());
    }


    public static String getEndDateForERONine() {
        Calendar calendar = Calendar.getInstance();
        int lastDate = calendar.getActualMaximum(Calendar.DATE);

//        int max = todayC.getActualMaximum(Calendar.DAY_OF_MONTH);
//        calendar.set(Calendar.DAY_OF_MONTH, lastDate);

        int mon = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        calendar.set(year,mon,20);

//        calendar.set(Calendar.DATE, 20);
//        int lastDay = calendar.get(Calendar.DAY_OF_WEEK);

//        System.out.println("Last Date: " + calendar.getTime());
//
//        System.out.println("Last Day : " + lastDay);
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

        return dateFormat.format(calendar.getTime());
    }

    public static Date getEndDateForERONineDis() {
        Calendar calendar = Calendar.getInstance();
        int lastDate = calendar.getActualMaximum(Calendar.DATE);

//        int max = todayC.getActualMaximum(Calendar.DAY_OF_MONTH);
//        calendar.set(Calendar.DAY_OF_MONTH, lastDate);

        int mon = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        calendar.set(year,mon,20);

//        calendar.set(Calendar.DATE, 20);
//        int lastDay = calendar.get(Calendar.DAY_OF_WEEK);

//        System.out.println("Last Date: " + calendar.getTime());
//
//        System.out.println("Last Day : " + lastDay);
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

        return calendar.getTime();
    }
}
