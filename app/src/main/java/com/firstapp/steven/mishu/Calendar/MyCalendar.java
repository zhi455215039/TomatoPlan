package com.firstapp.steven.mishu.Calendar;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.firstapp.steven.mishu.EditDay.EditDayActivity;
import com.firstapp.steven.mishu.OnDaySelectedListener;
import com.firstapp.steven.mishu.R;
import com.firstapp.steven.mishu.data.Day_item;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;

import cn.aigestudio.datepicker.bizs.calendars.DPCNCalendar;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by steven on 2016/8/26.
 */

public class MyCalendar extends FrameLayout {
    private LinearLayout linearLayout;
    private  LinearLayout line1,line2,line3,line4,line5,line6;
    private DPCNCalendar dpcnCalendar;
    private int year;
    private int month;
    private String[][]month_calendar;
    private String[][]month_festival;
    protected DayIntem lastOne;
    protected LinearLayout up,down;
    private LinearLayout add_button;
private  ArrayList<LinearLayout> line;
    private OnDaySelectedListener onDaySelectedListener;
    public MyCalendar(Context context) {
        this(context,null);
    }

    public MyCalendar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyCalendar(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        linearLayout=new LinearLayout(context);
       linearLayout.setOrientation(LinearLayout.VERTICAL);
        addView(linearLayout,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        init(context);
        dpcnCalendar=new DPCNCalendar();
        //setTime(2016,8);
        line=new ArrayList<>();


    }
    public void init(Context context)
    {
        line1= (LinearLayout) LayoutInflater.from(context).inflate(R.layout.day_item,null);

        linearLayout.addView(line1,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        line2= (LinearLayout) LayoutInflater.from(context).inflate(R.layout.day_item,null);

        linearLayout. addView(line2,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        line3= (LinearLayout) LayoutInflater.from(context).inflate(R.layout.day_item,null);

        linearLayout. addView(line3,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        line4= (LinearLayout) LayoutInflater.from(context).inflate(R.layout.day_item,null);

        linearLayout.addView(line4,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        line5= (LinearLayout) LayoutInflater.from(context).inflate(R.layout.day_item,null);

        linearLayout.addView(line5,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
      // setTime(2016,8);
        line6= (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.day_item,null);

        linearLayout.addView(line6,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);


           if(month_calendar!=null&&month_calendar[5][0].equals(""))
           {
               for(int i=0;i<linearLayout.getChildCount();i++)
               {
                   View child=linearLayout.getChildAt(i);
                   LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) child.getLayoutParams();
                   params.height=linearLayout.getMeasuredHeight()/5;

               }
       }


        else {
       for(int i=0;i<linearLayout.getChildCount();i++)
       {
           View child=linearLayout.getChildAt(i);
           LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) child.getLayoutParams();
           params.height=linearLayout.getMeasuredHeight()/6;

       }
   }


    }
    public void setTime(int year,int month)
    {



        this.year=year;
        this.month=month;
        month_calendar=dpcnCalendar.buildMonthG(year,month);
        month_festival=dpcnCalendar.buildMonthFestival(year,month);
        line=new ArrayList<>();
        line.add(line1);
        line.add(line2);
        line.add(line3);
        line.add(line4);
        line.add(line5);
        line.add(line6);
if(month_calendar[5][0].equals(""))
{

   // linearLayout.requestLayout();
    line.remove(line6);
}
        for(int i=0;i<line.size();i++)
        {
            LinearLayout v= (LinearLayout) line.get(i);
            for(int j=0;j<v.getChildCount();j++)
            {
                final DayIntem intem= (DayIntem) v.getChildAt(j);
                intem.setBackgroundColor(Color.WHITE);
                intem.clearTextView();
                String s=month_festival[i][j];
                int day=0;
                if(!month_calendar[i][j].equals("")){
                    day=Integer.parseInt(month_calendar[i][j]);
                SharedPreferences editor=getContext().getSharedPreferences(year+""+month+""+day+"edit_day", MODE_PRIVATE);
                HashSet<String> hashSet= (HashSet<String>) editor.getStringSet("dayitem",new HashSet<String>());
                    ArrayList<Day_item> day_data=new ArrayList<>();


                  //  SharedPreferences.Editor editorRepeat= getContext(). getSharedPreferences("repeatdays",MODE_PRIVATE).edit();
                   SharedPreferences readerRepeat=getContext().getSharedPreferences("repeatdays",MODE_PRIVATE);
                    HashSet<String> repeatdays= (HashSet<String>) readerRepeat.getStringSet("dayitem",new HashSet<String>());
                    ArrayList<Day_item> repeatDays=new ArrayList<>();
                    HashSet<String> hashS= (HashSet<String>) editor.getStringSet("delName",new HashSet<String>());
                    for(String fileName:repeatdays) {
                        Day_item item1 = new Day_item();
                        item1.setDay(readerRepeat.getInt(fileName + "day", -1));
                        item1.setMonth(readerRepeat.getInt(fileName + "month", -1));
                        item1.setYear(readerRepeat.getInt(fileName + "year", -1));
                        item1.setFromHour(readerRepeat.getInt(fileName + "fromHour", -1));
                        item1.setFromMinute(readerRepeat.getInt(fileName + "fromMinute", -1));
                        item1.setToHour(readerRepeat.getInt(fileName + "toHour", -1));
                        item1.setToMinute(readerRepeat.getInt(fileName + "toMinute", -1));
                        item1.setTheme(readerRepeat.getString(fileName + "theme", ""));
                        item1.setCategory(readerRepeat.getString(fileName + "category", ""));
                        item1.setRepeatDays(readerRepeat.getInt(fileName + "repeat", -1));
                        item1.setBeiwang(readerRepeat.getString(fileName + "beiwang", ""));
                        item1.setFinish(readerRepeat.getBoolean(fileName + "finish", false));
                        item1.setCreatTime(readerRepeat.getString(fileName+"create",""));
                       repeatDays.add(item1);
                    }

                  for(int k=0;k<repeatDays.size();k++)
                  {
                      Day_item item=repeatDays.get(k);
                      Calendar now=Calendar.getInstance();
                      Calendar calendar=Calendar.getInstance();
                      calendar.set(item.getYear(),item.getMonth()-1,item.getDay());
                      now.set(year,month-1,Integer.parseInt(month_calendar[i][j]));
                      int days= (int) ((now.getTimeInMillis()-calendar.getTimeInMillis())/(3600*24*1000));
                      if(days>0&&item.getRepeatDays()!=-1&&item.getRepeatDays()!=0&&days%item.getRepeatDays()==0&&!hashS.contains(item.getCreatTime()))
                      {
                          item.setYear(year);
                          item.setMonth(month);
                          item.setDay(Integer.parseInt(month_calendar[i][j]));
                          day_data.add(item);
                      }
                  }



                    for(String fileName:hashSet) {
                    Day_item item1 = new Day_item();
                    item1.setDay(editor.getInt(fileName + "day", -1));
                    item1.setMonth(editor.getInt(fileName + "month", -1));
                    item1.setYear(editor.getInt(fileName + "year", -1));
                    item1.setFromHour(editor.getInt(fileName + "fromHour", -1));
                    item1.setFromMinute(editor.getInt(fileName + "fromMinute", -1));
                    item1.setToHour(editor.getInt(fileName + "toHour", -1));
                    item1.setToMinute(editor.getInt(fileName + "toMinute", -1));
                    item1.setTheme(editor.getString(fileName + "theme", ""));
                    item1.setCategory(editor.getString(fileName + "category", ""));
                    item1.setRepeatDays(editor.getInt(fileName + "repeat", -1));
                    item1.setBeiwang(editor.getString(fileName + "beiwang", ""));
                    item1.setFinish(editor.getBoolean(fileName + "finish", false));
                        item1.setCreatTime(editor.getString(fileName+"create",""));
                        if(day_data.contains(item1))
                            day_data.remove(item1);
                    day_data.add(item1);

                }
                    Collections.sort(day_data);
                    MyCalendarView.todayList.clear();
                    for(int k=0;k<day_data.size();k++)
                    {
                        MyCalendarView.todayList.add(day_data.get(k));

                    }
                    MyCalendarView.ada.notifyDataSetChanged();
                    intem.setDay_data(day_data);
                }
                Calendar calendar=Calendar.getInstance();
               if(!month_calendar[i][j].equals(""))
               {
                   calendar.set(year,month-1,Integer.parseInt(month_calendar[i][j]));
                   intem.setCalendar(calendar);
               }
                intem.setFestival(s);
                //intem.setCalendar(calendar);
                intem.setRow(i);
                final int finalI = i;
                final DayIntem ii=intem;
                intem.setOnDayItemOnclick(new DayIntem.OnDayItemOnclick() {
                    @Override
                    public void onClick() {
                       {
                           Calendar calendar1=intem.getCalendar();
                           if(calendar1!=null)
                           {int y=calendar1.get(Calendar.YEAR);
                               int m=calendar1.get(Calendar.MONTH)+1;
                               int d=calendar1.get(Calendar.DAY_OF_MONTH);
                           if(onDaySelectedListener!=null)
                           onDaySelectedListener.onDaySelected(y,m,d,intem);}
                          if(lastOne!=null&&intem.isCheck())
                          {
                              lastOne.setCheck(false);
                              lastOne=intem;

                          }
                           else{
                               startAnim(finalI,intem.isCheck());
                               if(intem.isCheck())
                                   lastOne=intem;

                           }

                       }

                    }
                });
                intem.setDay_of_month(month_calendar[i][j]);
            }
        }
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }
    public void startAnim(final int row, boolean cancel)
    {



        ValueAnimator  animator,animatorUp;

            final int size=line.size();
        if(row==size-1)
        {
            up=line.get(size-2);
            down=line.get(size-1);
        }
        else {
            up=line.get(row);
            down=line.get(row+1);
        }
            animator=ValueAnimator.ofInt(0,line.get(size-1).getTop()-line.get(row+1>=size?size-1:row+1).getTop());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int num= (int) valueAnimator.getAnimatedValue();

                  for(int k=row+1;k<size;k++)
                      line.get(k).setTranslationY(num);

                }
            });
            animator.setDuration(200);
            animatorUp=ValueAnimator.ofInt(0,-line.get(row).getTop());
        if(row==size-1)
            animatorUp=ValueAnimator.ofInt(0,-line.get(row-1).getTop());
            animatorUp.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int num= (int) valueAnimator.getAnimatedValue();
                    int r=row;
                    if(row==size-1)
                        r=row-1;
                   for(int k=0;k<=r;k++)
                   {
                       line.get(k).setTranslationY(num);
                   }
                }
            });
            animatorUp.setDuration(200);
            if(cancel){
            animator.start();
            animatorUp.start();}
            else {
                animator.reverse();
                animatorUp.reverse();
                up=null;
                down=null;
                lastOne=null;
                ((MyCalendarView) getParent()).getRecyclerView().setVisibility(GONE);
            }


    }
    public void closeCalendar()
    {
        if(lastOne!=null)
        {
            lastOne.setCheck(false);
            startAnim(lastOne.getRow(),lastOne.isCheck());
        }
    }

    public ArrayList<LinearLayout> getLine() {
        return line;
    }

    public void setOnDaySelectedListener(OnDaySelectedListener onDaySelectedListener) {
        this.onDaySelectedListener = onDaySelectedListener;
    }


}
