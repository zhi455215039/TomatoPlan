package com.firstapp.steven.mishu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.util.Random;

/**
 * Created by steven on 2016/8/19.
 */

public class UserIcon extends ImageView {
    private int wid,hei;
    private Path path;
    private Paint paint;
    public static int []photos={R.drawable.photo1,R.drawable.photo2,R.drawable.photo3,R.drawable.photo4,R.drawable.photo5,R.drawable.photo7,R.drawable.photo6};
private Random random=new Random();
    private int id;
    public UserIcon(Context context) {
        this(context,null);


    }

    public UserIcon(Context context, AttributeSet attrs) {
        this(context, attrs,0);


    }

    public UserIcon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        path=new Path();
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);

        paint.setStrokeWidth(3*getResources().getDisplayMetrics().density);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        wid=getMeasuredWidth();
        hei=getMeasuredHeight();

        path.addCircle(wid/2,hei/2,wid/2, Path.Direction.CW);

    }

    @Override
    protected void onDraw(Canvas canvas) {
canvas.clipPath(path);
        super.onDraw(canvas);
        canvas.drawCircle(wid/2,hei/2,wid/2,paint);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
