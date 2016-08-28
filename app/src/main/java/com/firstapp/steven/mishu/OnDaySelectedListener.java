package com.firstapp.steven.mishu;

import com.firstapp.steven.mishu.Calendar.DayIntem;
import com.firstapp.steven.mishu.data.Day_item;

/**
 * Created by steven on 2016/8/27.
 */

public interface OnDaySelectedListener {
    void onDaySelected(int year,int month,int day,DayIntem item);
}
