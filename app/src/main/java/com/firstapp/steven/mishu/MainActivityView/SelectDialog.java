package com.firstapp.steven.mishu.MainActivityView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.firstapp.steven.mishu.EditDay.MyButton;
import com.firstapp.steven.mishu.R;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by steven on 2016/8/25.
 */

public class SelectDialog extends Dialog {
    private HashSet<String> notify_days;
    private MyButton before_1;
    private MyButton today;
    private MyButton before_7;
    private MyButton before_15;
    private MyButton before_3;
    private MyButton before_30;
    private TextView save;
    private TextView change;
    public SelectDialog(Context context, int themeResId) {
        super(context, R.style.color_dialog);

    }

    public SelectDialog(Context context,TextView textView,HashSet<String> list) {
        this(context,0);
        if(list!=null)
            notify_days=list;
        else {
            notify_days=new HashSet<>();
            list=notify_days;
        }
        change=textView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_notify);
        before_1= (MyButton) findViewById(R.id.before_1);
        before_3= (MyButton) findViewById(R.id.before_3);
        before_7= (MyButton) findViewById(R.id.before_7);
        before_15= (MyButton) findViewById(R.id.before_15);
        before_30= (MyButton) findViewById(R.id.before_30);

        today= (MyButton) findViewById(R.id.today);
        if(notify_days.contains("0")) today.start();
        if(notify_days.contains("1")) before_1.start();
        if(notify_days.contains("3")) before_3.start();
        if(notify_days.contains("7")) before_7.start();
        if(notify_days.contains("15")) before_15.start();
        if(notify_days.contains("30")) before_30.start();
            save= (TextView) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notify_days.clear();
                String s="";
                if(today.isChecked()) {
                    notify_days.add("0");
                    s=s+"当天/ ";
                }
                if(before_1.isChecked()) {
                    notify_days.add("1");
                    s=s+"提前1天,";
                }
                if(before_3.isChecked()){
                    notify_days.add("3");
                    s=s+"提前3天,";
                }
                if(before_7.isChecked()) {
                    notify_days.add("7");
                    s=s+"提前7天,";
                }
                if(before_15.isChecked()) {
                    notify_days.add("15");
                    s=s+"提前15天,";
                }
                if(before_30.isChecked()) {
                    notify_days.add("30");
                    s=s+"提前30天";
                }
                if(notify_days.isEmpty())
                    s="如当天，提前1天，提前3天，提前7天，提前15天提前30天";
               change.setText(s);
dismiss();
            }
        });
    }
}
