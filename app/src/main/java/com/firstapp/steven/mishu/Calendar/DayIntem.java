package com.firstapp.steven.mishu.Calendar;

import android.content.Context;
import android.graphics.Color;

import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firstapp.steven.mishu.R;
import com.firstapp.steven.mishu.data.Day_item;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by steven on 2016/8/26.
 */

public class DayIntem extends FrameLayout {
   private LinearLayout linearLayout;
    private TextView day_of_month;
    private TextView festival;
    private TextView item1,item2,item3,item4;
    private float density;
    private Calendar calendar;
    private OnDayItemOnclick onDayItemOnclick;
    private int row=-1;
    private boolean check=false;
    private boolean del=false;
    private ArrayList<Day_item> day_data=new ArrayList<>();
    public DayIntem(Context context) {
        this(context,null);
    }

    public DayIntem(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DayIntem(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        linearLayout= (LinearLayout) LayoutInflater.from(context).inflate(R.layout.day_item_content,null);
        addView(linearLayout,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        day_of_month= (TextView) linearLayout.findViewById(R.id.day_of_month);
        festival= (TextView) linearLayout.findViewById(R.id.festival);
        item1= (TextView) linearLayout.findViewById(R.id.item_1);
        item2= (TextView) linearLayout.findViewById(R.id.item_2);
        item3= (TextView) linearLayout.findViewById(R.id.item_3);
        item4= (TextView) linearLayout.findViewById(R.id.item_4);
        density=getResources().getDisplayMetrics().density;
        setBackgroundColor(Color.WHITE);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int wid=getMeasuredWidth();
        int hei=getMeasuredHeight();
        day_of_month.setTextColor(Color.BLACK);
        day_of_month.setTextSize(wid/13);
        //festival.setTextColor(Color.BLACK);
        festival.setTextSize(wid/15);
        festival.setPadding(wid/30,0,wid/30,0);
        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) item1.getLayoutParams();
        params.setMargins(wid/30,wid/30,wid/30,wid/30);
        item1.setPadding(wid/30,0,0,0);
        params= (LinearLayout.LayoutParams) item2.getLayoutParams();
        params.setMargins(wid/30,wid/30,wid/30,wid/30);
        item2.setPadding(wid/30,0,0,0);
        params= (LinearLayout.LayoutParams) item3.getLayoutParams();
        params.setMargins(wid/30,wid/30,wid/30,wid/30);
        item3.setPadding(wid/30,0,0,0);
        params= (LinearLayout.LayoutParams) item4.getLayoutParams();
        params.setMargins(wid/30,wid/30,wid/30,wid/30);
        item4.setPadding(wid/30,0,0,0);
    }
    public void setDay_of_month(String s)
    {

        day_of_month.setText(s);
    }
    public void setFestival(String s)
    {
        if(s.endsWith("F"))
        {s=s.substring(0,s.length()-1);
        festival.setTextColor(getResources().getColor(R.color.colorAccent));}
        else if(s.equals("初一")){
            LunarCalendar c=new LunarCalendar();
            s=c.getLunarDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DAY_OF_MONTH),true);
        }
        festival.setText(s);
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
        Calendar calendar1=Calendar.getInstance();
        if(calendar1.get(Calendar.YEAR)==calendar.get(Calendar.YEAR)&&calendar1.get(Calendar.MONTH)==calendar.get(Calendar.MONTH)&&calendar1.get(Calendar.DAY_OF_MONTH)==calendar.get(Calendar.DAY_OF_MONTH))
         setBackgroundColor(getResources().getColor(R.color.today));
    }

    public Calendar getCalendar() {
        return calendar;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction()==MotionEvent.ACTION_UP)
        {
            check=!check;
           if(onDayItemOnclick!=null)
               onDayItemOnclick.onClick();

            if (check)
                linearLayout.setBackgroundColor(getResources().getColor(R.color.blue));
            else
                linearLayout.setBackgroundColor(Color.TRANSPARENT);
        }
        return true;
    }
    public interface OnDayItemOnclick{
        void onClick();
    }

    public void setOnDayItemOnclick(OnDayItemOnclick onDayItemOnclick) {
        this.onDayItemOnclick = onDayItemOnclick;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
        if (check)
            linearLayout.setBackgroundColor(getResources().getColor(R.color.blue));
        else
            linearLayout.setBackgroundColor(Color.TRANSPARENT);
    }

    public void setDay_data(ArrayList<Day_item> day_data) {

        TextView textView[]={item1,item2,item3,item4};
       clearTextView();
        this.day_data = day_data;
        for(int i=0;i<4&&i<day_data.size();i++)
        {
            Day_item day_item=day_data.get(i);
            textView[i].setVisibility(VISIBLE);
            textView[i].setText(day_item.getTheme());
            if(day_item.isFinish())
                textView[i].getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        }
    }

    public ArrayList<Day_item> getDay_data() {
        return day_data;
    }

    public boolean isDel() {
        return del;
    }

    public void setDel(boolean del) {
        this.del = del;
    }
    public void clearTextView()
    {
        day_data.clear();
        item1.setVisibility(INVISIBLE);
        item2.setVisibility(INVISIBLE);
        item3.setVisibility(INVISIBLE);
        item4.setVisibility(INVISIBLE);
        item1.setText("");
        item2.setText("");
        item3.setText("");
        item4.setText("");
    }

}
