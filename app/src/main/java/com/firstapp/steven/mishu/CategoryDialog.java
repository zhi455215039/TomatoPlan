package com.firstapp.steven.mishu;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.firstapp.steven.mishu.EditDay.EditDayActivity;
import com.firstapp.steven.mishu.EditDay.MyButton;
import com.firstapp.steven.mishu.data.Day_item;

/**
 * Created by steven on 2016/8/27.
 */

public class CategoryDialog extends Dialog {
    private MyButton work,play,study,sport,train,activity;
    private Day_item day_item;
    private TextView textView;
    private EditDayActivity.OnDialogDismiss onDialogDismiss;
    public CategoryDialog(Context context,Day_item item) {
        super(context,R.style.color_dialog);
        day_item=item;
    }

    public CategoryDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_dialog);
        work= (MyButton) findViewById(R.id.dialog_work);
        play= (MyButton) findViewById(R.id.dialog_play);
        study= (MyButton) findViewById(R.id.dialog_study);
        sport= (MyButton) findViewById(R.id.dialog_sport);
        train= (MyButton) findViewById(R.id.dialog_train);
        activity= (MyButton) findViewById(R.id.dialog_activity);
        MyButton.single.add(work);
        MyButton.single.add(play);
        MyButton.single.add(study);
        MyButton.single.add(sport);
        MyButton.single.add(train);
        MyButton.single.add(activity);
        Day_item item=day_item;
        if(item.getCategory().equals("工作") ){
            work.start();
        }
        if(item.getCategory().equals("娱乐") ){
            play.start();
        }
        if(item.getCategory().equals("学习") ){
            study.start();
        }
        if(item.getCategory().equals("运动") ){
           sport.start();
        }
        if(item.getCategory().equals("旅行") ){
            train.start();
        }
        if(item.getCategory().equals("活动") ){
            activity.start();
        }
        textView= (TextView) findViewById(R.id.dialog_save);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(work.isChecked())
                    day_item.setCategory("工作");
                else  if(play.isChecked())
                    day_item.setCategory("娱乐");
                else  if(study.isChecked())
                    day_item.setCategory("学习");
                else  if(sport.isChecked())
                    day_item.setCategory("运动");
                else  if(train.isChecked())
                    day_item.setCategory("旅行");
                else  if(activity.isChecked())
                    day_item.setCategory("活动");
                dismiss();
            }
        });

    }
    public CategoryDialog setOnDialogDismiss(EditDayActivity.OnDialogDismiss onDialogDismiss) {
        this.onDialogDismiss = onDialogDismiss;
        return this;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if(onDialogDismiss!=null)
            onDialogDismiss.OnDismiss();
    }
}
