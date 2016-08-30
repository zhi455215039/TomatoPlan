package com.firstapp.steven.mishu.EditDay;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by steven on 2016/8/23.
 */

public class MyButton extends View {
    private int wid,hei;
    private boolean isCheck=false;
    private Paint circle;
    private float density;
    private Path path=new Path();
    private Point[] mTickPoints;
    private Paint background;
    private Paint drawPath;
    private int color=Color.BLUE;
private Paint whiteBack;
    private float r;
    private Path drawP=new Path();
    private PathMeasure measure;
    private OnMyButtomAnimEnd animEnd;
    public static ArrayList<MyButton> single=new ArrayList<>();
    public MyButton(Context context) {
        this(context, null);

    }

    public MyButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Random random=new Random(System.currentTimeMillis());
        color=Color.rgb(random.nextInt(250),random.nextInt(250),random.nextInt(250));
        circle=new Paint(Paint.ANTI_ALIAS_FLAG);
        circle.setStyle(Paint.Style.STROKE);
        circle.setColor(Color.GRAY);
        density=getResources().getDisplayMetrics().density;
        circle.setStrokeWidth(2*density);
        background=new Paint(Paint.ANTI_ALIAS_FLAG);
        drawPath=new Paint(Paint.ANTI_ALIAS_FLAG);
        background.setColor(color);
        background.setStyle(Paint.Style.FILL_AND_STROKE);
        drawPath=new Paint(Paint.ANTI_ALIAS_FLAG);
        drawPath.setStyle(Paint.Style.STROKE);
        drawPath.setColor(Color.WHITE);
        drawPath.setStrokeCap(Paint.Cap.ROUND);
        drawPath.setStrokeJoin(Paint.Join.ROUND);
        drawPath.setStrokeWidth(3*density);
whiteBack=new Paint(Paint.ANTI_ALIAS_FLAG);
        whiteBack.setColor(Color.WHITE);
measure=new PathMeasure();
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                start();

            }
        });

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        wid=getMeasuredWidth();
        hei=getMeasuredHeight();
       path.moveTo(Math.round((float) getMeasuredWidth() / 30 * 7),Math.round((float) getMeasuredHeight() / 30 * 14));
        path.lineTo( Math.round((float) getMeasuredWidth() / 30 * 13), Math.round((float) getMeasuredHeight() / 30 * 20));
       path.lineTo( Math.round((float) getMeasuredWidth() / 30 * 22), Math.round((float) getMeasuredHeight() / 30 * 10));
        measure.setPath(path,false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(resolveSize(0,heightMeasureSpec),resolveSize(0,heightMeasureSpec));
    }


    public boolean isChecked() {
        return isCheck;
    }


    public void setChecked(boolean checked) {

       isCheck=checked;
        if(isCheck&&single.contains(this))
        for(int i=0;i<single.size();i++)
        {
            MyButton button=single.get(i);
            if(button!=this)
                if(button.isChecked())
                button.start();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {


if(isCheck){
        canvas.drawCircle(wid/2,hei/2,wid/2-density,background);
    canvas.drawCircle(wid/2,hei/2,r,whiteBack);
canvas.drawPath(drawP,drawPath);}
        else  canvas.drawCircle(wid/2,hei/2,wid/2-density,circle);
    }
    public void start()
    {
ValueAnimator scaleAnimator=ValueAnimator.ofFloat(1.0f,0.8f,1.0f);
        scaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float scale= (float) valueAnimator.getAnimatedValue();
                setScaleX(scale);
                setScaleY(scale);
            }
        });
        scaleAnimator.setDuration(250);
        scaleAnimator.start();
        if(!isCheck)
        { setChecked(true);
            ValueAnimator animator=ValueAnimator.ofFloat(wid/2-density,0);
            animator.setDuration(300);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    r= (float) valueAnimator.getAnimatedValue();
                    invalidate();
                }
            });
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
ValueAnimator animator1=ValueAnimator.ofFloat(0,measure.getLength());
                    animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            float len= (float) valueAnimator.getAnimatedValue();
                            measure.getSegment(0,len,drawP,true);
                            invalidate();
                        }
                    });
                    animator1.setDuration(400);
                    animator1.start();
                    animator1.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            if(animEnd!=null)
                            animEnd.onEnd();
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            animator.start();
        }
        else {
            drawP=new Path();
            ValueAnimator animator=ValueAnimator.ofFloat(0,wid/2-density);
            animator.setDuration(300);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    r= (float) valueAnimator.getAnimatedValue();
                    invalidate();
                }
            });
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
setChecked(false);
                    invalidate();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            animator.start();
        }
    }
public interface OnMyButtomAnimEnd{
    void onEnd();

}

    public void setAnimEnd(OnMyButtomAnimEnd animEnd) {
        this.animEnd = animEnd;
    }
}
