package com.firstapp.steven.mishu;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
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
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by steven on 2016/8/29.
 */

public class TomatoButton extends TextView {
   private Paint paint;
   private float density;
    private Path path;
    private PathMeasure pathMeasure;
    private Handler handler;
    private boolean click=false;
    private ValueAnimator alpha;
    private Path whitePath;
    private OnTomotoButtonOnclick onTomotoButtonOnclick;
    private int minutes=1;
    private int seconds=minutes*60;
    private Timer timer;
    private Runnable computeTime;
    private TimerTask timerTask;
    private float pathLength;
    private int restMinutes=5;
    private FrameLayout parent;
    private int circleColor;
    private boolean viber=true;
    private boolean ring=true;
    private  MediaPlayer  mMediaPlayer;
    private boolean ok=false;
    private Paint pen;
    private int wid,hei;
    private String text;
    private Vibrator  vibrator;
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
        pen=new Paint(Paint.ANTI_ALIAS_FLAG);
        pen.setColor(Color.WHITE);
        pen.setTextSize(30*getResources().getDisplayMetrics().density);
        text="开始";
        density=context.getResources().getDisplayMetrics().density;
        paint.setStrokeWidth(5*density);
        path=new Path();
        whitePath=new Path();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                 if(msg.what==1)
                 {

                     int m=seconds/60;
                     int s=seconds%60;
                     if(seconds>=0)
                     text=(m>9?m:("0"+m))+":"+(s>9?s:("0"+s));
                     else {
                         m=(restMinutes*60+seconds)/60;
                         s=(restMinutes*60+seconds)%60;
                         text="休息中"+" "+(m>9?m:("0"+m))+":"+(s>9?s:("0"+s));
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

                     }
                     if(seconds==-restMinutes*60)
                     {
                         end();
                     }
                     float fract=(float)(minutes*60-seconds)/(minutes*60);
                     pathMeasure.getSegment(0,fract*pathLength,whitePath,true);
                     invalidate();
                 }
            }
        };
        timerTask=new TimerTask() {
            @Override
            public void run() {
              seconds--;

                Message message=new Message();
                message.what=1;

                handler.sendMessage(message);
            }
        };
        timer=new Timer();

        pathMeasure=new PathMeasure();
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
        pathMeasure.setPath(path,true);
        pathLength=pathMeasure.getLength();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
canvas.drawPath(path,paint);
        paint.setColor(Color.WHITE);
      float len= pen.measureText(text,0,text.length());
        float h=Math.abs(pen.descent()+pen.ascent());
        canvas.drawText(text,(wid-len)/2,(hei+h)/2,pen);
        canvas.drawPath(whitePath,paint);
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
        SharedPreferences reader=getContext().getSharedPreferences("tomatoSetting",Context.MODE_PRIVATE);
        minutes=reader.getInt("worktime",25);

        restMinutes=reader.getInt("resttime",5);
        viber=reader.getBoolean("vibr",true);
        ring=reader.getBoolean("ring",true);
        circleColor=Color.rgb(253,0,6);
        parent.setBackgroundColor(getResources().getColor(R.color.tomato_background));
        timer=new Timer();
        timerTask.cancel();
        seconds=minutes*60;
        timerTask=new TimerTask() {
            @Override
            public void run() {
                seconds--;
                Bundle bundle=new Bundle();
                bundle.putInt("minutes",seconds/60);
                bundle.putInt("seconds",seconds%60);
                Message message=new Message();
                message.what=1;
                message.setData(bundle);
                handler.sendMessage(message);
            }
        };
timer.schedule(timerTask,0,1000);
        alpha.cancel();
        setAlpha(1f);
    }

    public Timer getTimer() {
        return timer;
    }
    public void end()
    {

        timer.purge();
        timer.cancel();
        alpha.start();
        circleColor=Color.rgb(253,0,6);
        parent.setBackgroundColor(getResources().getColor(R.color.tomato_background));
        if(parent.getChildAt(3)!=null)
        parent.getChildAt(3).setVisibility(INVISIBLE);
        whitePath=new Path();
        invalidate();
        text="开始";
    }
    public void onTimeEnd()
    {
        timer.purge();
        timer.cancel();
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
}
