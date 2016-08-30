package com.firstapp.steven.tomato;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.firstapp.steven.mishu.EditDay.MyButton;
import com.firstapp.steven.mishu.R;
import com.firstapp.steven.mishu.TomatoButton;

/**
 * Created by steven on 2016/8/30.
 */

public class TomatoSetting extends Dialog {
    private Context context;

    private TextView setWork;
    private TextView setRest;
    private SeekBar workBar;
    private SeekBar restBar;
    private MyButton setVibration;
    private MyButton setRing;
    private TextView textView;
    public TomatoSetting(Context context) {
        this(context,0);

        this.context=context;
    }

    public TomatoSetting(Context context, int themeResId) {
        super(context, R.style.color_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tomato_setting);
        setWork= (TextView) findViewById(R.id.tomato_setting_work_text);
        setRest= (TextView) findViewById(R.id.tomato_setting_rest_text);
        workBar= (SeekBar) findViewById(R.id.tomato_setting_work_seekbar);
        restBar= (SeekBar) findViewById(R.id.tomato_setting_rest_seekbar);
        setVibration= (MyButton) findViewById(R.id.tomato_setting_rest_zhen);
        setRing= (MyButton) findViewById(R.id.tomato_setting_ling);
        textView= (TextView) findViewById(R.id.tomato_setting_yes);
        SharedPreferences editor=context.getSharedPreferences("tomatoSetting",Context.MODE_PRIVATE);
        setWork.setText("工作时间: "+editor.getInt("worktime",25)+"分钟");
        setRest.setText("休息时间: "+editor.getInt("resttime",5)+"分钟");
        workBar.setProgress(editor.getInt("worktime",25));
        restBar.setProgress(editor.getInt("resttime",5));
        workBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(i>=1)
                setWork.setText("工作时间: "+i+"分钟");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        restBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(i>=1)
                setRest.setText("休息时间: "+i+"分钟");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        boolean isVibr=editor.getBoolean("vibr",true);
        boolean isRing=editor.getBoolean("ring",true);
        if(isVibr)
            setVibration.start();
        if(isRing)
            setRing.start();

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor1=context.getSharedPreferences("tomatoSetting",Context.MODE_PRIVATE).edit();
                editor1.putInt("worktime",workBar.getProgress()>0?workBar.getProgress():1);
                editor1.putInt("resttime",restBar.getProgress()>0?restBar.getProgress():1);
                editor1.putBoolean("vibr",setVibration.isChecked());
                editor1.putBoolean("ring",setRing.isChecked());
                editor1.apply();
dismiss();
            }
        });
    }
}
