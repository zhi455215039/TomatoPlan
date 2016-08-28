package com.firstapp.steven.mishu.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

/**
 * Created by steven on 2016/8/21.
 */

public class Birthday implements Comparable<Birthday>,Serializable{
    private String name;
    private Calendar date;
    private String content;
    private int day_num;
private String phone;
    private int photo_id=-1;
    private String relationship;
    private String sex;
    private String icon_add="";
    private HashSet<String> hashSet;
private Calendar thisyear;
    private ArrayList<Integer> notifyDays=new ArrayList<>();

    public HashSet<String> getHashSet() {
        return hashSet;
    }

    public void setThisyear(Calendar thisyear) {
        this.thisyear = thisyear;
    }

    public Calendar getThisyear() {
        return thisyear;
    }

    public void setHashSet(HashSet<String> hashSet) {
        this.hashSet = hashSet;
    }

    public String getContent() {
        return content;
    }

    public String getIcon_add() {
        return icon_add;
    }

    public void setIcon_add(String icon_add) {
        this.icon_add = icon_add;
    }

    public int getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(int photo_id) {
        this.photo_id = photo_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getDay_num()
    {
        Calendar calendar=Calendar.getInstance();
        Calendar calendar1=Calendar.getInstance();
        Calendar calendar2=Calendar.getInstance();
        calendar1.set(calendar.get(Calendar.YEAR),thisyear.get(Calendar.MONTH),thisyear.get(Calendar.DAY_OF_MONTH));
        calendar2.set(calendar.get(Calendar.YEAR)+1,thisyear.get(Calendar.MONTH),thisyear.get(Calendar.DAY_OF_MONTH));
        int day1= (int) ((calendar1.getTimeInMillis()-calendar.getTimeInMillis())/(1000*3600*24));
        int day2=(int) ((calendar2.getTimeInMillis()-calendar.getTimeInMillis())/(1000*3600*24));
        if(day1<0)
        return  day2;
        else return Math.min(day1,day2);
    }

    @Override
    public int compareTo(Birthday birthday) {
       int num=getDay_num()-birthday.getDay_num();
        if(num>0) return 1;
        else if(num<0) return -1;
        else
        return 0;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;


    }
}
