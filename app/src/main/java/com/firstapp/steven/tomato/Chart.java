package com.firstapp.steven.tomato;

/**
 * Created by steven on 2016/8/31.
 */

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by steven on 2016/8/30.
 */

public class Chart extends View {
    private String []xAxis={"1","2","3","4","5","6","7"};
    private int []yAxis={0,0,0,0,0,0,0};
    private Paint paintLine;
    private Paint paintPoint;
    private int wid,hei;
    private float density;
    private int yMax=9;
    private int xMax=5;
    private Path path=new Path();
    private Path drawPath;
    private PathMeasure pathMeasure;
    private ValueAnimator animator;
    public Chart(Context context) {
        this(context,null);
    }

    public Chart(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Chart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paintLine=new Paint(Paint.ANTI_ALIAS_FLAG);
        paintPoint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paintLine.setColor(Color.RED);
        density=context.getResources().getDisplayMetrics().density;
        paintPoint.setTextSize(density*15);
        paintPoint.setColor(Color.RED);
        paintLine.setStyle(Paint.Style.STROKE);
        pathMeasure=new PathMeasure();
        paintLine.setPathEffect(new CornerPathEffect(40));
        drawPath=new Path();
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

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paintLine.setStrokeWidth(density);
        canvas.drawLine(wid/16+paintPoint.measureText(xAxis[0])/2,hei*7/8,wid/16+6*wid/xAxis.length+paintPoint.measureText(xAxis[6])/2,hei*7/8,paintLine);
        for(int i=0;i<xAxis.length;i++)
        {  float acent=paintPoint.ascent();
            float decent=paintPoint.descent();
            float len=paintPoint.measureText(xAxis[i]);
            canvas.drawCircle(wid/16+i*wid/xAxis.length+len/2,hei*7/8,2*density,paintPoint);
            canvas.drawText(xAxis[i],wid/16+i*wid/xAxis.length,hei*7/8-acent-decent+hei/20,paintPoint);
            paintPoint.setColor(Color.GRAY);
            paintPoint.setTextSize(10*density);
            canvas.drawText(yAxis[i]+"",wid/16+i*wid/xAxis.length+len/2-paintPoint.measureText(yAxis[i]+"")/2,hei/8+3*hei/4*(1-((float)yAxis[i])/yMax)+acent/2,paintPoint);
            paintPoint.setTextSize(density*15);
            paintPoint.setColor(Color.RED);
            paintLine.setStrokeWidth(density*2);
            canvas.drawPath(drawPath,paintLine);


        }
    }

    public void setxAxis(String[] xAxis) {
        this.xAxis = xAxis;
    }

    public void setyAxis(int[] yAxis) {
        this.yAxis = yAxis;
      path=new Path();
        xMax=0;
        yMax=yAxis[0];
        for(int i=0;i<yAxis.length;i++)
        {
            if(yAxis[i]>yMax)
            {
                xMax=i;
                yMax=yAxis[i];
            }
        }
        for(int i=0;i<xAxis.length;i++)
        { float len=paintPoint.measureText(xAxis[i]);
            if(i==0)
                path.moveTo(wid/16+i*wid/xAxis.length+len/2,hei/8+3*hei/4*(1-((float)yAxis[i])/yMax));
            else path.lineTo(wid/16+i*wid/xAxis.length+len/2,hei/8+3*hei/4*(1-((float)yAxis[i])/yMax));
        }
        pathMeasure.setPath(path,false);

    }
    public void startAnim()
    {
        drawPath=new Path();
        if(animator!=null&&animator.isRunning()) animator.end();
       animator=ValueAnimator.ofFloat(0,pathMeasure.getLength());
        animator.setDuration(1500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                drawPath.reset();
                pathMeasure.getSegment(0, (Float) valueAnimator.getAnimatedValue(),drawPath,true);
                invalidate();
            }
        });
        animator.start();
    }
}

