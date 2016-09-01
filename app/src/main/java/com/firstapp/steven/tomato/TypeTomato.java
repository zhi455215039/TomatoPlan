package com.firstapp.steven.tomato;

import android.animation.TypeEvaluator;
import android.graphics.Point;

/**
 * Created by steven on 2016/8/29.
 */

public class TypeTomato implements TypeEvaluator<TomatoSingle> {

    @Override
    public TomatoSingle evaluate(float v, TomatoSingle tomatoSingle, TomatoSingle t1) {
        TomatoSingle single=new TomatoSingle();
        Point point=new Point();
        Point p1=tomatoSingle.getPoint();
        Point p2=t1.getPoint();
        point.set((int)(p1.x+v*(p2.x-p1.x)),(int)(p1.y+v*(p2.y-p1.y)));
        single.setBitmap(tomatoSingle.getBitmap());
        single.setPoint(point);
        single.setPosition(t1.getPosition());
        return single;
    }
}
