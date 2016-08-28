package com.firstapp.steven.mishu;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firstapp.steven.mishu.EditDay.EditDayActivity;
import com.firstapp.steven.mishu.EditDay.MyButton;
import com.firstapp.steven.mishu.data.Day_item;

/**
 * Created by steven on 2016/8/27.
 */

public class DialogRemark extends Dialog {
    private Day_item day_item;
    private EditText editText;
    private TextView cancel,save;
    private EditDayActivity.OnDialogDismiss onDialogDismiss;
    public DialogRemark(Context context, Day_item item) {
        this(context,0);
        day_item=item;
    }

    public DialogRemark(Context context, int themeResId) {
        super(context, R.style.color_dialog);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.remark_dialog);
        setContentView(R.layout.remark_dialog);
        editText= (EditText) findViewById(R.id.remark_dialog);
        cancel= (TextView) findViewById(R.id.remark_cancel);
        save= (TextView) findViewById(R.id.remark_save);
        editText.setText(day_item.getBeiwang());
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day_item.setBeiwang(editText.getText().toString());
                //Toast.makeText(getContext(),day_item.getBeiwang(),Toast.LENGTH_SHORT).show();
                View vi = getWindow().peekDecorView();
                if (vi != null) {
                    InputMethodManager inputmanger = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                dismiss();
            }
        });
    }

    public DialogRemark setOnDialogDismiss(EditDayActivity.OnDialogDismiss onDialogDismiss) {
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
