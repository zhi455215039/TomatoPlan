package com.firstapp.steven.tomato;

import android.graphics.Bitmap;
import android.graphics.Point;

/**
 * Created by steven on 2016/8/29.
 */

public class TomatoSingle implements Comparable<TomatoSingle>{
    private Point point;
    private Bitmap bitmap;
    private int position;
    public TomatoSingle(int x,int y,Bitmap bitmap,int position)
    {
        point=new Point();
        point.set(x,y);
        this.bitmap=bitmap;
        this.position=position;
    }
    public TomatoSingle()
    {

    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }

    @Override
    public int compareTo(TomatoSingle tomatoSingle) {
        if(tomatoSingle.getPosition()>position)
             return -1;
        else if(position>tomatoSingle.getPosition())
            return 1;
        else
        return 0;
    }
}
