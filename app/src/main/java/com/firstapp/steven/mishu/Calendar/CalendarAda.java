package com.firstapp.steven.mishu.Calendar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firstapp.steven.mishu.EditDay.EditDayActivity;
import com.firstapp.steven.mishu.R;
import com.firstapp.steven.mishu.data.Day_item;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by steven on 2016/8/27.
 */

public class CalendarAda extends RecyclerView.Adapter<CalendarAda.CalendarViewHolder>{

private Context context;
    private ArrayList<Day_item> list;
    public CalendarAda(Context context,ArrayList<Day_item>list)
    {
        this.context=context;
        this.list=list;
    }
    @Override
    public CalendarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.day_plan_item,parent,false);
        CalendarViewHolder holder=new CalendarViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(CalendarViewHolder holder, int position) {
final Day_item item=list.get(holder.getAdapterPosition());
        ImageView imageView=holder.imageView;
        if(item.getCategory().equals("工作")||item.getCategory().equals("") ){
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.work));

        }
        if(item.getCategory().equals("娱乐") ){
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.yule));

        }
        if(item.getCategory().equals("学习") ){
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.study));

        }
        if(item.getCategory().equals("运动") ){
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.sport));

        }
        if(item.getCategory().equals("旅行") ){
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.train));

        }
        if(item.getCategory().equals("活动") ){
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.activity));

        }
        if(item.isFinish())
            holder.textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        else holder.textView.getPaint().setFlags(0);
        Calendar calendar1=Calendar.getInstance();
        Calendar calendar11=Calendar.getInstance();
        Calendar calendar12=Calendar.getInstance();
        calendar11.set(item.getYear(),item.getMonth()-1,item.getDay(),item.getFromHour(),item.getFromMinute(),0);
        if(calendar11.getTimeInMillis()<calendar1.getTimeInMillis())
            holder.timeUp.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        else holder.timeUp.getPaint().setFlags(0);
        calendar12.set(item.getYear(),item.getMonth()-1,item.getDay(),item.getToHour(),item.getToMinute(),0);
        if(calendar12.getTimeInMillis()<calendar1.getTimeInMillis())
        holder.timeDown.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        else holder.timeDown.getPaint().setFlags(0);
        holder.timeUp.setText((item.getFromHour()>9?item.getFromHour():("0"+item.getFromHour()))+":"+(item.getFromMinute()>9?item.getFromMinute():"0"+item.getFromMinute()));
        holder.timeDown.setText((item.getToHour()>9?item.getToHour():("0"+item.getToHour()))+":"+(item.getToMinute()>9?item.getToMinute():"0"+item.getToMinute()));
        holder.textView.setText(item.getTheme());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, EditDayActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("day",item);
                bundle.putInt("change",1);
                intent.putExtras(bundle);
                ((AppCompatActivity)context).startActivityForResult(intent,1239);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CalendarViewHolder extends RecyclerView.ViewHolder{
ImageView imageView;
        TextView timeUp;
        TextView timeDown;
        TextView textView;
        View view;
        public CalendarViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            imageView= (ImageView) itemView.findViewById(R.id.day_plan_item_image);
            timeDown= (TextView) itemView.findViewById(R.id.day_plan_item_time_dowm);
            timeUp= (TextView) itemView.findViewById(R.id.day_plan_item_time_up);
            textView= (TextView) itemView.findViewById(R.id.day_plan_item_text);
        }
    }
}
