package com.firstapp.steven.tomato;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.firstapp.steven.mishu.R;

import java.util.Arrays;

/**
 * Created by steven on 2016/8/29.
 */

public class ShowTomato extends View {
    private int wid,hei;
    private static int draw[]={R.drawable.fanqie1,R.drawable.fanqie2,R.drawable.fanqie3,R.drawable.fanqie4,R.drawable.fanqie5,R.drawable.fanqie6,R.drawable.fanqie7,R.drawable.fanqie8,R.drawable.fanqie9,R.drawable.fanqie10,R.drawable.fanqie11,R.drawable.fanqie12,R.drawable.fanqie13,R.drawable.fanqie14,R.drawable.fanqie15,R.drawable.fanqie16};
    private Bitmap bitmap[];
    private float [][]position;
    private TomatoSingle point[]=new TomatoSingle[15];
    private boolean canShow=false;
    private float x=0f,y=-10000f;
    private OnAnimEnd onAnimEnd;
    public ShowTomato(Context context) {
        this(context,null);
    }

    public ShowTomato(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShowTomato(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bitmap=new Bitmap[16];
        for(int i=0;i<16;i++)
            bitmap[i]= BitmapFactory.decodeResource(getResources(),draw[i]);
        position=new float[30][2];

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(resolveSize(0,widthMeasureSpec),resolveSize(0,heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        wid=getMeasuredWidth();
        hei=getMeasuredHeight();
        int centerX=wid/2;
        int centerY=hei/2;
        for(int i=0;i<16;i++)
        {
            Bitmap bm=bitmap[i];
            Matrix matrix=new Matrix();
            matrix.setScale(((float)wid)/(12*bm.getWidth()),((float)wid)/(12*bm.getWidth()));
            bitmap[i]=Bitmap.createBitmap(bm,0,0,bm.getWidth(),bm.getHeight(),matrix,false);
        }
        for(int i=0;i<30;i++)
        {
            float angle= (float) (2*Math.PI*i/30);
            position[i][0]= (float) (centerX+Math.cos(Math.PI/2+angle)*(wid/2-bitmap[0].getWidth()/2));
            position[i][1]= (float) (centerY-Math.sin(Math.PI/2+angle)*(wid/2-bitmap[0].getWidth()/2));
        }
        for(int i=0;i<30;i=i+2)
        {
            if(i%2==0){
                point[i/2]=new TomatoSingle((int)position[i][0],(int)position[i][1],bitmap[i/2],i);

            }}
        Arrays.sort(point);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(!canShow)
        {
            canvas.drawBitmap(point[0].getBitmap(),wid/2-(point[0].getBitmap().getWidth()/2),y-(point[0].getBitmap().getWidth()/2),null);

        }
        if(canShow)
        for(int i=0;i<point.length;i++)
            canvas.drawBitmap(point[i].getBitmap(),point[i].getPoint().x-(point[i].getBitmap().getWidth()/2),point[i].getPoint().y-(point[i].getBitmap().getWidth()/2),null);

    }
    public void startAnim()
    {
        ValueAnimator animator=ValueAnimator.ofFloat(-point[0].getBitmap().getHeight(),hei/2);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                y= (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
             canShow=true;
                ValueAnimator anim;
                for(int i=0;i<point.length;i++)
                {
                    TomatoSingle to=point[i];
                    TomatoSingle from=new TomatoSingle();
                    Point point1=new Point();
                    point1.set(wid/2,hei/2);
                    from.setPoint(point1);
                    from.setBitmap(to.getBitmap());
                    from.setPosition(to.getPosition());
                    ValueAnimator animator1=ValueAnimator.ofObject(new TypeTomato(),from,to);
                    final int finalI = i;
                    animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            point[finalI]= (TomatoSingle) valueAnimator.getAnimatedValue();
                            invalidate();
                        }
                    });
                    if(i==point.length-1)
                    {
                       animator1.addListener(new Animator.AnimatorListener() {
                           @Override
                           public void onAnimationStart(Animator animator) {

                           }

                           @Override
                           public void onAnimationEnd(Animator animator) {
                            if(onAnimEnd!=null)
                                onAnimEnd.onEnd();
                           }

                           @Override
                           public void onAnimationCancel(Animator animator) {

                           }

                           @Override
                           public void onAnimationRepeat(Animator animator) {

                           }
                       });
                    }
                    animator1.setDuration(1000);
                    animator1.start();
                }


            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.setDuration(1000);
        animator.start();

    }
    public interface OnAnimEnd{
        void onEnd();
    }

    public void setOnAnimEnd(OnAnimEnd onAnimEnd) {
        this.onAnimEnd = onAnimEnd;
    }
    public void cancel()
    {
        y=-10000f;

        canShow=false;
        invalidate();
        wid=getMeasuredWidth();
        hei=getMeasuredHeight();
        int centerX=wid/2;
        int centerY=hei/2;
        for(int i=0;i<16;i++)
        {
            Bitmap bm=bitmap[i];
            Matrix matrix=new Matrix();
            matrix.setScale(((float)wid)/(12*bm.getWidth()),((float)wid)/(12*bm.getWidth()));
            bitmap[i]=Bitmap.createBitmap(bm,0,0,bm.getWidth(),bm.getHeight(),matrix,false);
        }
        for(int i=0;i<30;i++)
        {
            float angle= (float) (2*Math.PI*i/30);
            position[i][0]= (float) (centerX+Math.cos(Math.PI/2+angle)*(wid/2-bitmap[0].getWidth()/2));
            position[i][1]= (float) (centerY-Math.sin(Math.PI/2+angle)*(wid/2-bitmap[0].getWidth()/2));
        }
        for(int i=0;i<30;i=i+2)
        {
            if(i%2==0){
                point[i/2]=new TomatoSingle((int)position[i][0],(int)position[i][1],bitmap[i/2],i);

            }}
        Arrays.sort(point);
    }
}
