package com.jackzheng.jizhang.manager;

import java.util.Calendar;

/**
 * Created by jackzheng on 2018/1/1.
 */

public class TimeManager {
    public static int  getDate(){
        Calendar calendar = Calendar.getInstance();
        int date=  calendar.get(Calendar.DAY_OF_MONTH);
        int month=  calendar.get(Calendar.MONTH)+1;
        int year = calendar.get(Calendar.YEAR);
        int value =year*1000+month*100+date;
        return value;
    }
    public static int getTime(){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int value = hour*10000+minute*100+second;
        return value;
    }
    public static int  getYear(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return year;
    }
    public static int  getMonth(){
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        return month;
    }
    public static int  getDay(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return day;
    }

}
