package com.firstapp.steven.tomato;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;

import com.firstapp.steven.mishu.R;
import com.firstapp.steven.mishu.TomatoButton;

/**
 * Created by steven on 2016/8/30.
 */

public class TomatoDialog extends Dialog {
    TextView textView;
    private TomatoButton button;
    public TomatoDialog(Context context,TomatoButton button) {
        this(context,0);
        this.button=button;
    }

    public TomatoDialog(Context context, int themeResId) {
        super(context, R.style.color_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_photo);
        textView= (TextView) findViewById(R.id.get_press);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                
                dismiss();
            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if(button.getmMediaPlayer()!=null&&button.getmMediaPlayer().isPlaying())
        {
            button.getmMediaPlayer().stop();

        }
        if(button.getVibrator()!=null&&button.getVibrator().hasVibrator())
            button.getVibrator().cancel();
    }
}
