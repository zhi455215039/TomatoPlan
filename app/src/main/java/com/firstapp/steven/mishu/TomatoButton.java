package com.firstapp.steven.mishu;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.firstapp.steven.tomato.TomatoDialog;

import java.io.IOException;
import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by steven on 2016/8/29.
 */

public class TomatoButton extends TextView {
   private Paint paint;
   private float density;
    private Path path;

    private boolean click=false;
    private ValueAnimator alpha;

    private OnTomotoButtonOnclick onTomotoButtonOnclick;
    private int minutes=1;
    private int seconds=Integer.MAX_VALUE;


    private float pathLength;
    private int restMinutes=5;
    private FrameLayout parent;
    private int circleColor;
    private boolean viber=true;
    private boolean ring=true;
    private  MediaPlayer  mMediaPlayer;
    private boolean ok=false;

    private int wid,hei;
    private String text;
    private Vibrator  vibrator;
    private Handler handler;
    private RectF rectF;
    private SharedPreferences.Editor editor;
    private SharedPreferences reader;
    private Calendar calendar;
private TomatoActivity activity;
    private com.firstapp.steven.Timer timer;

    public TomatoButton(Context context) {
        this(context,null);
    }

    public TomatoButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TomatoButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        SharedPreferences reader=getContext().getSharedPreferences("tomatoSetting",Context.MODE_PRIVATE);
        minutes=reader.getInt("worktime",25);
        restMinutes=reader.getInt("resttime",5);
        viber=reader.getBoolean("vibr",true);
        ring=reader.getBoolean("ring",true);
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        circleColor=Color.rgb(253,0,6);
        paint.setColor(circleColor);
        paint.setStyle(Paint.Style.STROKE);

       setText("开始");
        density=context.getResources().getDisplayMetrics().density;
        paint.setStrokeWidth(5*density);
        path=new Path();
        rectF=new RectF();

        calendar=Calendar.getInstance();
handler=new Handler(){
    @Override
    public void handleMessage(Message msg) {
        if(msg.what==1){
            seconds--;
            if(alpha.isRunning())
                alpha.end();
            setAlpha(1f);
        int m=seconds/60;
        int s=seconds%60;
        if(seconds>=0)
        {
            setText((m>9?m:("0"+m))+":"+(s>9?s:("0"+s)));
            activity.setNotificationText((m>9?m:("0"+m))+":"+(s>9?s:("0"+s)));
        }
        else {
            m=(restMinutes*60+seconds)/60;
            s=(restMinutes*60+seconds)%60;
            setText("休息中"+"\n"+(m>9?m:("0"+m))+":"+(s>9?s:("0"+s)));
            activity.setNotificationText("休息中"+"\n"+(m>9?m:("0"+m))+":"+(s>9?s:("0"+s)));
            circleColor=Color.rgb(20,140,10);
        }
        if(seconds==0)
        {
            ok=true;
            if(ring)
                try {
                    startAlarm();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if(viber)
            {
                vibrator = (Vibrator)getContext().getSystemService(Context.VIBRATOR_SERVICE);
                long [] pattern = {100,400,100,400}; // 停止 开启 停止 开启
                vibrator.vibrate(pattern,2);

            }
            parent.setBackgroundColor(getResources().getColor(R.color.tomato_end));
            new TomatoDialog(getContext(),TomatoButton.this).show();
            SharedPreferences   reader=getContext().getSharedPreferences("tomato",Context.MODE_PRIVATE);
            int all=reader.getInt("allGood",0);
            int mon=reader.getInt(calendar.get(Calendar.YEAR)+""+(calendar.get(Calendar.MONTH)+1),0);
            int week=reader.getInt(calendar.get(Calendar.YEAR)+""+calendar.get(Calendar.WEEK_OF_YEAR),0);
            int todayMinute=reader.getInt(calendar.get(Calendar.YEAR)+""+(calendar.get(Calendar.MONTH)+1)+""+calendar.get(Calendar.DAY_OF_MONTH)+"min",0);
            int allMinute=reader.getInt("allMin",0);
            int todayTomatoNum= reader.getInt(calendar.get(Calendar.YEAR)+""+(calendar.get(Calendar.MONTH)+1)+""+calendar.get(Calendar.DAY_OF_MONTH)+"good",0);
            SharedPreferences.Editor    editor=getContext().getSharedPreferences("tomato",Context.MODE_PRIVATE).edit();
            editor.putInt("allGood",all+1);
            editor.putInt("allMin",allMinute+minutes);


            ////////////////////
            editor.putInt(calendar.get(Calendar.YEAR)+""+calendar.get(Calendar.WEEK_OF_YEAR),week+1);
            editor.putInt(calendar.get(Calendar.YEAR)+""+(calendar.get(Calendar.MONTH)+1),mon+1);
            editor.putInt(calendar.get(Calendar.YEAR)+""+(calendar.get(Calendar.MONTH)+1)+""+calendar.get(Calendar.DAY_OF_MONTH)+"min",todayMinute+minutes);
            editor.putInt(calendar.get(Calendar.YEAR)+""+(calendar.get(Calendar.MONTH)+1)+""+calendar.get(Calendar.DAY_OF_MONTH)+"good",todayTomatoNum+1);
            editor.apply();

        }
        if(seconds==-restMinutes*60)
        {
end();

            activity.unbindService(activity.getmConnection());
        }


        invalidate();
    }}
};

    timer=new com.firstapp.steven.Timer(handler);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onTomotoButtonOnclick!=null)
                    onTomotoButtonOnclick.onClick();

            }
        });
       alpha=ValueAnimator.ofFloat(0.2f,1f,0.2f);
        alpha.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                TomatoButton.this.setAlpha((Float) valueAnimator.getAnimatedValue());
            }
        });
        alpha.setDuration(6000);
        alpha.setRepeatCount(-1);
        alpha.setRepeatMode(ValueAnimator.RESTART);
        alpha.setInterpolator(new LinearInterpolator());
        alpha.start();


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        wid=getMeasuredWidth();
        hei=getMeasuredHeight();
        path.addCircle(getMeasuredWidth()/2,getMeasuredHeight()/2,getMeasuredWidth()/2-5*density, Path.Direction.CW);
       // pathMeasure.setPath(path,true);

        rectF.set(wid/2-(getMeasuredWidth()/2-5*density),hei/2-(getMeasuredWidth()/2-5*density),wid/2+(getMeasuredWidth()/2-5*density),hei/2+(getMeasuredWidth()/2-5*density));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
canvas.drawCircle(getMeasuredWidth()/2,getMeasuredHeight()/2,getMeasuredWidth()/2-5*density,paint);
        paint.setColor(Color.WHITE);

       if(seconds!=Integer.MAX_VALUE)
       canvas.drawArc(rectF,0,seconds>=0?(360*((float)(minutes*60-seconds))/(minutes*60)):360,false,paint);
        paint.setColor(circleColor);
    }
    public interface OnTomotoButtonOnclick{
        void onClick();
    }

    public void setOnTomotoButtonOnclick(OnTomotoButtonOnclick onTomotoButtonOnclick) {
        this.onTomotoButtonOnclick = onTomotoButtonOnclick;
    }

    public boolean isClick() {
        return click;
    }

    public void setClick(boolean click) {
        this.click = click;
    }
    public void startTimer()
    {
  ok=false;
        SharedPreferences reader=getContext().getSharedPreferences("tomatoSetting",Context.MODE_PRIVATE);
        minutes=reader.getInt("worktime",25);

        restMinutes=reader.getInt("resttime",5);

        viber=reader.getBoolean("vibr",true);
        ring=reader.getBoolean("ring",true);
        seconds=minutes*60;
        timer.start();
        circleColor=Color.rgb(253,0,6);
        parent.setBackgroundColor(getResources().getColor(R.color.tomato_background));
        if(alpha.isRunning())

        alpha.end();
        setAlpha(1f);
        alpha.cancel();
    }


    public void end()
    {

       if(timer!=null)
          timer.stop();
        setText("开始");
        alpha.start();
        circleColor=Color.rgb(253,0,6);
        parent.setBackgroundColor(getResources().getColor(R.color.tomato_background));
        if(parent.getChildAt(3)!=null)
        parent.getChildAt(3).setVisibility(INVISIBLE);
        seconds=Integer.MAX_VALUE;
        invalidate();

    }

    public void setParent(FrameLayout parent) {
        this.parent = parent;
    }
    private void startAlarm() throws IllegalStateException, IOException {
        mMediaPlayer = MediaPlayer.create(getContext(), getSystemDefultRingtoneUri());
        mMediaPlayer.setLooping(true);
        try {
            mMediaPlayer.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mMediaPlayer.start();
    }

    //获取系统默认铃声的Uri
    private Uri getSystemDefultRingtoneUri() {
        return RingtoneManager.getActualDefaultRingtoneUri(getContext(),
                RingtoneManager.TYPE_RINGTONE);
    }

    public MediaPlayer getmMediaPlayer() {
        return mMediaPlayer;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public Vibrator getVibrator() {
        return vibrator;
    }

    public void setActivity(TomatoActivity activity) {
        this.activity = activity;
    }
}
