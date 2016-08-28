package com.firstapp.steven.mishu;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.firstapp.steven.mishu.EditDay.EditDayActivity;
import com.firstapp.steven.mishu.data.Day_item;

/**
 * Created by steven on 2016/8/27.
 */

public class DialogRepeat extends Dialog {
   private TextView dialog0,dialog1,dialog7,dialog14,dialog30,dialog365;
    private Day_item day_item;
    private EditDayActivity.OnDialogDismiss onDialogDismiss;
    public DialogRepeat(Context context,Day_item item) {
        this(context,0);
        day_item=item;
    }

    public DialogRepeat(Context context, int themeResId) {
        super(context, R.style.color_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_repeat);
        dialog0= (TextView) findViewById(R.id.repeat_no);
        dialog1= (TextView) findViewById(R.id.repeat_1);
        dialog7= (TextView) findViewById(R.id.repeat_7);

        dialog14= (TextView) findViewById(R.id.repeat_14);
        dialog30= (TextView) findViewById(R.id.repeat_30);
        dialog365= (TextView) findViewById(R.id.repeat_365);
        dialog0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day_item.setRepeatDays(0);
                dismiss();
            }
        });
        dialog1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day_item.setRepeatDays(1);
                dismiss();
            }
        });dialog7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day_item.setRepeatDays(7);
                dismiss();
            }
        });dialog14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day_item.setRepeatDays(14);
                dismiss();
            }
        });dialog30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day_item.setRepeatDays(30);
                dismiss();
            }
        });dialog365.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day_item.setRepeatDays(365);
                dismiss();
            }
        });

    }
    public DialogRepeat setOnDialogDismiss(EditDayActivity.OnDialogDismiss onDialogDismiss) {
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
