package com.firstapp.steven.mishu.Calendar;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firstapp.steven.mishu.EditDay.EditDayActivity;
import com.firstapp.steven.mishu.OnDaySelectedListener;
import com.firstapp.steven.mishu.R;
import com.firstapp.steven.mishu.data.Day_item;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by steven on 2016/8/26.
 */

public class MyCalendarView extends FrameLayout {
  private   MyCalendar left;
  private   MyCalendar right;
  private   MyCalendar middle;
  private   int wid,hei;
  private   boolean isIntercept=false;
 private    float pressX=0;
 private    float translate=0;
 private    boolean canMove=true;
 private    Handler handle=new Handler();
 private    Random random=new Random();
    private AppCompatActivity parent;
    private int year;
    private int month;
    private int slop;
    private float firstX=0;
    protected   static ArrayList<Day_item> todayList;
    protected static CalendarAda ada;
    private RecyclerView recyclerView;
    private FrameLayout add_new_day;
    private OnDaySelectedListener selectedListener;
    public MyCalendarView(
            Context context) {
        this(context,null);
    }

    public MyCalendarView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyCalendarView(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        todayList=new ArrayList<>();
       // TextView button=new TextView(context);
         recyclerView=new RecyclerView(context);
        add_new_day= (FrameLayout) LayoutInflater.from(context).inflate(R.layout.add_new_day,null);
       addView(add_new_day,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        add_new_day.setVisibility(GONE);
        add_new_day.findViewById(R.id.add_new_p).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, EditDayActivity.class);
                ((AppCompatActivity)context).startActivityForResult(intent,444);
            }
        });
        addView(recyclerView,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        LinearLayoutManager manager=new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        ada=new CalendarAda(context,todayList);
        recyclerView.setAdapter(ada);
recyclerView.setVisibility(GONE);
        left= new MyCalendar(context);
        right= new MyCalendar(context);
        middle= new MyCalendar(context);
        final Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        ViewConfiguration configuration=ViewConfiguration.get(context);
        slop=configuration.getScaledPagingTouchSlop();

        middle.setTime(year,month);
        setTime(middle.getYear(),middle.getMonth());
        right.setTime(middle.getMonth()+1>12?middle.getYear()+1:middle.getYear(),middle.getMonth()+1>12?1:middle.getMonth()+1);

        left.setTime(middle.getMonth()-1<1?middle.getYear()-1:middle.getYear(),middle.getMonth()-1<1?12:middle.getMonth()-1);

        addView(left,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(right,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        addView(middle,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        selectedListener=new OnDaySelectedListener() {
            @Override
            public void onDaySelected(int y,int m,int d,DayIntem intem) {
                todayList.clear();
                ada.notifyDataSetChanged();
                if(intem.isCheck()) recyclerView.setVisibility(VISIBLE);
                else {
                    recyclerView.setVisibility(GONE);
                    add_new_day.setVisibility(GONE);
                }

              {

                   for(int i=0;i<intem.getDay_data().size();i++)
                       todayList.add(intem.getDay_data().get(i));


                  Collections.sort(todayList);
                ada.notifyDataSetChanged();
              if(todayList.isEmpty()&&intem.isCheck())
              {
                  recyclerView.setVisibility(GONE);
                  add_new_day.setVisibility(VISIBLE);
              }
                  else {
                  add_new_day.setVisibility(GONE);
              }
              }
            }
        };
        left.setOnDaySelectedListener(selectedListener);
        middle.setOnDaySelectedListener(selectedListener);
        right.setOnDaySelectedListener(selectedListener);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        wid=getMeasuredWidth();
        hei=getMeasuredHeight();
        left.setTranslationX(-wid);
        right.setTranslationX(wid);
       /* LayoutParams p= (LayoutParams) add_new_day.getLayoutParams();
        p.topMargin=(getMeasuredHeight()-add_new_day.getMeasuredHeight())/2;
        p.leftMargin=(getMeasuredWidth()-add_new_day.getMeasuredWidth())/2;*/
        LayoutParams params= (LayoutParams) recyclerView.getLayoutParams();
        params.height=(middle.getLine().size()-2)*getMeasuredHeight()/middle.getLine().size();
        params.topMargin=getMeasuredHeight()/middle.getLine().size();;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(canMove)
            switch (event.getAction())
            {

                case MotionEvent.ACTION_DOWN:


                    break;
                case MotionEvent.ACTION_MOVE:


                    float x=event.getX();
                    translate=x-pressX;

                    left.setTranslationX(left.getTranslationX()+translate);
                    right.setTranslationX(right.getTranslationX()+translate);
                    middle.setTranslationX(middle.getTranslationX()+translate);
                    recyclerView.setTranslationX(recyclerView.getTranslationX()+translate);
                    add_new_day.setTranslationX(add_new_day.getTranslationX()+translate);
                    pressX=x;
                    break;
                case MotionEvent.ACTION_UP:
                    isIntercept=false;
                    onActionUp();
                    break;
            }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                firstX = ev.getX();
                pressX=ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
if(firstX!=0&&Math.abs(ev.getX()-firstX)>slop)
                return true;
        }
        return false;
    }
    public void onActionUp()
    {

        final float translateX=middle.getTranslationX();
        ValueAnimator animator;
        if(translateX<-wid/3)
        {
            animator=ValueAnimator.ofFloat(translateX%wid,-wid);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float num= (float) valueAnimator.getAnimatedValue();
                    left.setTranslationX(-wid+num);
                    right.setTranslationX(wid+num);
                    middle.setTranslationX(num);
                    recyclerView.setTranslationX(num);
                    add_new_day.setTranslationX(num);

                }
            });
            animator.setDuration(150);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    canMove=false;
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    recyclerView.setTranslationX(0);
                    MyCalendar t1=middle;
                    MyCalendar t2=left;
                    middle=right;
                    LayoutParams params= (LayoutParams) recyclerView.getLayoutParams();
                    params.height=(middle.getLine().size()-2)*getMeasuredHeight()/middle.getLine().size();
                    params.topMargin=getMeasuredHeight()/middle.getLine().size();;
                    requestLayout();
                    left=t1;
                    right=t2;
                    right.setTranslationX(middle.getTranslationX()+wid);

                    right.setTime(middle.getMonth()+1>12?middle.getYear()+1:middle.getYear(),middle.getMonth()+1>12?1:middle.getMonth()+1);
                    handle.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            canMove=true;
                        }
                    },50);
                    setTime(middle.getYear(),middle.getMonth());
                    left.closeCalendar();
                    add_new_day.setVisibility(GONE);
                   // recyclerView.setVisibility(GONE);
                    add_new_day.setTranslationX(0);

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

        else if (translateX>wid/3){
            animator=ValueAnimator.ofFloat(translateX%wid,wid);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float num= (float) valueAnimator.getAnimatedValue();
                    left.setTranslationX(-wid+num);
                    right.setTranslationX(wid+num);
                    middle.setTranslationX(num);
                    recyclerView.setTranslationX(num);
                    add_new_day.setTranslationX(num);
                }
            });
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    canMove=false;
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    recyclerView.setTranslationX(0);
                    final MyCalendar t1=middle;
                    MyCalendar t2=right;
                    middle=left;
                    LayoutParams params= (LayoutParams) recyclerView.getLayoutParams();
                    params.height=(middle.getLine().size()-2)*getMeasuredHeight()/middle.getLine().size();
                    params.topMargin=getMeasuredHeight()/middle.getLine().size();;
                    requestLayout();
                    right=t1;
                    left=t2;
                    left.setTranslationX(middle.getTranslationX()-wid);

                    left.setTime(middle.getMonth()-1<1?middle.getYear()-1:middle.getYear(),middle.getMonth()-1<1?12:middle.getMonth()-1);
                    handle.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            canMove=true;
                        }
                    },50);
                    setTime(middle.getYear(),middle.getMonth());
                    right.closeCalendar();
                   // recyclerView.setVisibility(GONE);
                    add_new_day.setVisibility(GONE);
                    add_new_day.setTranslationX(0);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            animator.setDuration(150);
            animator.start();
        }
        else
        {
            animator=ValueAnimator.ofFloat(translateX%wid,0);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float num= (float) valueAnimator.getAnimatedValue();
                    left.setTranslationX(-wid+num);
                    right.setTranslationX(wid+num);
                    middle.setTranslationX(num);
                    recyclerView.setTranslationX(num);
                    add_new_day.setTranslationX(num);

                }
            });
            animator.setDuration(200);
            animator.start();
        }
    }

    public int getYear() {
        return year;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public int getMonth() {
        return month;
    }
    public void setTime(int year,int month)
    {
        this.year=year;
        this.month=month;
        if(parent!=null)
        parent.getSupportActionBar().setTitle(year+"年"+month+"月");
    }

    public void setParent(AppCompatActivity parent) {
        this.parent = parent;
    }

    public MyCalendar getMiddle() {
        return middle;
    }


    public MyCalendar getLeftCalendar() {
        return left;
    }


    public MyCalendar getRightCalendar() {
        return right;
    }

    public FrameLayout getAdd_new_day() {
        return add_new_day;
    }

    public ArrayList<Day_item> getTodayList() {
        return todayList;
    }

    public CalendarAda getAda() {
        return ada;
    }
}
