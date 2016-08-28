package com.firstapp.steven.mishu.data;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by steven on 2016/8/20.
 */

public class Day_item implements Serializable,Comparable<Day_item>{
    private String theme;
    private Calendar from;
    private Calendar to;
    private int alertMinutes;
    private int repeatDays=-1;
    private String beiwang="";
    private String category="";
    private int year=-1;
    private int month=-1;
    private int day=-1;
    private int fromHour=-1;
    private int fromMinute=-1;
    private int toHour=-1;
    private int toMinute=-1;
    private boolean isFinish=false;
    private boolean del=false;
    public int getYear() {
        return year;
    }
    private String creatTime="";

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public boolean isDel() {
        return del;
    }

    public void setDel(boolean del) {
        this.del = del;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setFromHour(int fromHour) {
        this.fromHour = fromHour;
    }

    public int getFromHour() {
        return fromHour;
    }

    public void setFromMinute(int fromMinute) {
        this.fromMinute = fromMinute;
    }

    public int getFromMinute() {
        return fromMinute;
    }

    public void setToHour(int toHour) {
        this.toHour = toHour;
    }

    public int getToHour() {
        return toHour;
    }

    public void setToMinute(int toMinute) {
        this.toMinute = toMinute;
    }

    public int getToMinute() {
        return toMinute;
    }

    public Calendar getFrom() {
        return from;
    }

    public void setFrom(Calendar from) {
        this.from = from;
    }

    public Calendar getTo() {
        return to;
    }

    public void setTo(Calendar to) {
        this.to = to;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getTheme() {
        return theme;
    }

    public void setAlertMinutes(int alertMinutes) {
        this.alertMinutes = alertMinutes;
    }

    public int getAlertMinutes() {
        return alertMinutes;
    }

    public int getRepeatDays() {
        return repeatDays;
    }

    public void setRepeatDays(int repeatDays) {
        this.repeatDays = repeatDays;
    }

    public void setBeiwang(String beiwang) {
        this.beiwang = beiwang;
    }

    public String getBeiwang() {
        return beiwang;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public int compareTo(Day_item item) {
        Calendar calendar=Calendar.getInstance();
        Calendar calendar1=Calendar.getInstance();
        calendar.set(year,month,day,fromHour,fromMinute,0);
        calendar1.set(item.getYear(),item.getMonth(),item.getDay(),item.getFromHour(),item.getFromMinute(),0);
        if(calendar.getTimeInMillis()>calendar1.getTimeInMillis())
            return  1;
        else if (calendar.getTimeInMillis()<calendar1.getTimeInMillis())
            return  -1;
      else   return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Day_item)) return false;
        Day_item item= (Day_item) obj;
        if(year==item.getYear()&&month==item.getMonth()&&day==item.getDay()&&theme.equals(item.getTheme())&&fromHour==item.getFromHour()&&fromMinute==item.getFromMinute()&&toHour==item.getToHour()&&toMinute==item.getToMinute())
            return  true;
        else
        return false;
    }


    public Day_item cloneItem()
    {
        Day_item item=new Day_item();
        item.setDay(day);
        item.setYear(year);
        item.setMonth(month);
        item.setFromHour(fromHour);
        item.setFromMinute(fromMinute);
        item.setToHour(toHour);
        item.setToMinute(toMinute);
        item.setBeiwang(new String(beiwang));
        item.setCategory(new String(category));
        item.setRepeatDays(repeatDays);
        item.setFinish(isFinish);
        item.setTheme(new String(theme));
        item.setCreatTime(new String(creatTime));
        return item;

    }
}
