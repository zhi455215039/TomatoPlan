package com.firstapp.steven.mishu.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

/**
 * Created by steven on 2016/8/23.
 */

public class ImportantDay implements Comparable<ImportantDay>,Serializable {
    private String name;
    private Calendar date;
    private String people;
    private String story;
    private ArrayList<Integer> notify=new ArrayList<>();
    private String beizhu;
    private String icon_add="";
    private int photo_id=-1;
    private HashSet<String> hashSet;
private BirthdayAda.OnRecyclevireLongClick longClick;
    public String getName() {
        return name;
    }

    public HashSet<String> getHashSet() {
        return hashSet;
    }

    public void setHashSet(HashSet<String> hashSet) {
        this.hashSet = hashSet;
    }

    public int getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(int photo_id) {
        this.photo_id = photo_id;
    }

    public String getIcon_add() {
        return icon_add;
    }

    public void setIcon_add(String icon_add) {
        this.icon_add = icon_add;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public ArrayList<Integer> getNotify() {
        return notify;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setNotify(ArrayList<Integer> notify) {
        this.notify = notify;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }
    public int getDay_num()
    {
        Calendar calendar=Calendar.getInstance();
        Calendar calendar1=Calendar.getInstance();
        Calendar calendar2=Calendar.getInstance();
        calendar1.set(calendar.get(Calendar.YEAR),date.get(Calendar.MONTH),date.get(Calendar.DAY_OF_MONTH));
        calendar2.set(calendar.get(Calendar.YEAR)+1,date.get(Calendar.MONTH),date.get(Calendar.DAY_OF_MONTH));
        int day1= (int) ((calendar1.getTimeInMillis()-calendar.getTimeInMillis())/(1000*3600*24));
        int day2=(int) ((calendar2.getTimeInMillis()-calendar.getTimeInMillis())/(1000*3600*24));
        if(day1<0)
            return  day2;
        else return Math.min(day1,day2);
    }

    @Override
    public int compareTo(ImportantDay importantDay) {
        int num=getDay_num()-importantDay.getDay_num();
        if(num>0) return 1;
        else if(num<0) return -1;
        else
            return 0;
    }
    public int getYearCount()
    {
        Calendar calendar=Calendar.getInstance();
        Calendar calendar1=Calendar.getInstance();
        calendar1.set(Calendar.YEAR,date.get(Calendar.YEAR));
        if(calendar1.getTimeInMillis()<date.getTimeInMillis())
        return  calendar.get(Calendar.YEAR)-date.get(Calendar.YEAR);
        else  return calendar.get(Calendar.YEAR)-date.get(Calendar.YEAR)+1;
    }
    public int getDayCount()
    {
        Calendar calendar=Calendar.getInstance();
        return (int) ((calendar.getTimeInMillis()-date.getTimeInMillis())/(3600*24*1000));
    }

    public void setLongClick(BirthdayAda.OnRecyclevireLongClick longClick) {
        this.longClick = longClick;
    }
}
