package com.firstapp.steven.tomato;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.firstapp.steven.mishu.R;
import com.firstapp.steven.mishu.TomatoButton;

/**
 * Created by steven on 2016/8/29.
 */

public class TomatoCalcel extends Dialog {
   private TextView yes,no;
    private TomatoButton button;
    private TextView textView,textView1;
    private OnDialogClick onDialogClick;

    public TomatoCalcel(Context context,TomatoButton button) {
        this(context,0);
        this.button=button;
    }

    public TomatoCalcel(Context context, int themeResId) {
        super(context, R.style.color_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tomato_cancel);
        textView= (TextView) findViewById(R.id.tomato_cancel_content);
        textView1= (TextView) findViewById(R.id.tomato_cancel__);
        if(button.isOk())
        {
            textView.setText("要重新开始吗？");
            textView1.setText("重新开始");
        }
        yes= (TextView) findViewById(R.id.tomato_yes);
        no= (TextView) findViewById(R.id.tomato_calcel);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onDialogClick!=null)
                    onDialogClick.onClick();
                button.setOk(false);
                dismiss();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
    public interface OnDialogClick{
        void onClick();
    }

        public TomatoCalcel setOnDialogClick(OnDialogClick onDialogClick) {
        this.onDialogClick = onDialogClick;
            return this;
    }
}
