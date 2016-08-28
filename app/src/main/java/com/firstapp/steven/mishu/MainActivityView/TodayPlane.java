package com.firstapp.steven.mishu.MainActivityView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by steven on 2016/8/15.
 */

public class TodayPlane extends RecyclerView {
    public TodayPlane(Context context) {
        this(context,null);
    }

    public TodayPlane(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TodayPlane(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
