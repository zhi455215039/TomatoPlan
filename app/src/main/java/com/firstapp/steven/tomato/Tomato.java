package com.firstapp.steven.tomato;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.firstapp.steven.mishu.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by steven on 2016/8/29.
 */

public class Tomato extends View implements ViewTreeObserver.OnGlobalLayoutListener{
    public static int draw[]={R.drawable.fanqie1,R.drawable.fanqie2,R.drawable.fanqie3,R.drawable.fanqie4,R.drawable.fanqie5,R.drawable.fanqie6,R.drawable.fanqie7,R.drawable.fanqie8,R.drawable.fanqie9,R.drawable.fanqie10,R.drawable.fanqie11,R.drawable.fanqie12,R.drawable.fanqie13,R.drawable.fanqie14,R.drawable.fanqie15,R.drawable.fanqie16};
    private int wid;
    private int hei;
    private Paint paint;
    private int count=0;
    private float [][]position;
    private float firstX=0,firstY=0;
    private ArrayList<Integer > points=new ArrayList<>();
    private Bitmap bitmap[];
    private static int num=30;
    private float rotate=0;
    private long current;
    private TomatoSingle point[]=new TomatoSingle[15];
    private Thread thread;
private AnimatorSet set;
    private boolean canAnim=true;

   private ValueAnimator RotateAnimator;
    public Tomato(Context context) {
        this(context,null);
    }

    public Tomato(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Tomato(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        position=new float[num][2];
        paint.setColor(Color.RED);
        bitmap=new Bitmap[16];
       // set=new AnimatorSet();
        RotateAnimator = ValueAnimator.ofFloat(0, 360);
        for(int i=0;i<16;i++)
            bitmap[i]= BitmapFactory.decodeResource(getResources(),draw[i]);



        getViewTreeObserver().addOnGlobalLayoutListener(this);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(resolveSize(0,widthMeasureSpec),resolveSize(0,heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);




    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
      canvas.rotate(rotate,wid/2,hei/2);

        for (TomatoSingle aPoint : point)
            canvas.drawBitmap(aPoint.getBitmap(), aPoint.getPoint().x - (aPoint.getBitmap().getWidth() / 2), aPoint.getPoint().y - (aPoint.getBitmap().getWidth() / 2), paint);
canvas.restore();
    }
    public void startAnim()
    {
        set=new AnimatorSet();

        if(!canAnim)
            set.setStartDelay(1000);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            canAnim=false;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Arrays.sort(point);


                    startAnim();
                //   Toast.makeText(getContext(),(System.currentTimeMillis()-current)+"",Toast.LENGTH_SHORT).show();
                // current=System.currentTimeMillis();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        {
      List<Animator> animators = new ArrayList<>();
      for (int i = 0; i < point.length; i++) {
          TomatoSingle to = new TomatoSingle();
          TomatoSingle th = point[i];
          to.setPosition((th.getPosition() + 15) % 30);
          to.setBitmap(th.getBitmap());
          final Point point1 = new Point();
          point1.set((int) position[to.getPosition()][0], (int) position[to.getPosition()][1]);
          to.setPoint(point1);
          ValueAnimator animator = ValueAnimator.ofObject(new TypeTomato(), th, to);
          final int finalI = i;
          animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
              @Override
              public void onAnimationUpdate(ValueAnimator valueAnimator) {
                  point[finalI] = (TomatoSingle) valueAnimator.getAnimatedValue();
                  invalidate();
              }
          });
          //animator.setInterpolator(new LinearInterpolator());
          animator.setDuration(800);
          animator.setStartDelay(140 * i);
          animators.add(animator);

      }

      set.playTogether(animators);


      set.start();
  }
    }
public void startRotate()
{


        RotateAnimator.setDuration(25000);
        RotateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rotate = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        RotateAnimator.start();
        RotateAnimator.setInterpolator(new LinearInterpolator());
        RotateAnimator.setRepeatMode(ValueAnimator.RESTART);
        RotateAnimator.setRepeatCount(-1);

}

    @Override
    public void onGlobalLayout() {
        wid=getMeasuredWidth();
        hei=getMeasuredHeight();
        int centerX=wid/2;
        int centerY=hei/2;
        for(int i=0;i<16;i++)
        {
            Bitmap bm=bitmap[i];
            Matrix matrix=new Matrix();
            matrix.setScale(((float)wid)/(11*bm.getWidth()),((float)wid)/(11*bm.getWidth()));
            bitmap[i]=Bitmap.createBitmap(bm,0,0,bm.getWidth(),bm.getHeight(),matrix,false);
        }
        for(int i=0;i<num;i++)
        {
            float angle= (float) (2*Math.PI*i/num);
            position[i][0]= (float) (centerX+Math.cos(Math.PI/2+angle)*(wid/2-bitmap[0].getWidth()/2));
            position[i][1]= (float) (centerY-Math.sin(Math.PI/2+angle)*(wid/2-bitmap[0].getWidth()/2));
        }
        for(int i=0;i<num;i=i+2)
        {
            if(i%2==0){
                point[i/2]=new TomatoSingle((int)position[i][0],(int)position[i][1],bitmap[i/2],i);

            }}
        if(!RotateAnimator.isRunning()){
        startAnim();
        startRotate();}
    }


}
