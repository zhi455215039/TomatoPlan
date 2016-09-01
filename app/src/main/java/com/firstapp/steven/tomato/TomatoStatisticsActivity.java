package com.firstapp.steven.tomato;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firstapp.steven.mishu.EditDay.EditDayActivity;
import com.firstapp.steven.mishu.MainActivity;
import com.firstapp.steven.mishu.R;

import java.util.Calendar;
import java.util.Random;

/**
 * Created by steven on 2016/8/30.
 */

public class TomatoStatisticsActivity extends AppCompatActivity {
private Toolbar toolbar;
 private  TextView all_today;
  private TextView worktime_today;
    private TextView bad_today;
    private LinearLayout addTomato;
    private TextView all;
    private TextView worktime;
    private TextView bad;
    private int all_t,bad_t,all_,bad_;
    private Random random;
    private SharedPreferences editor;
    private int days[][];

    private Chart chart;
    private String []xDay;
    private int []yDay;
    private String []xWeek;
    private int []yWeek;
    private String []xMon;
    private int []yMon;
    private TextView ri;
    private TextView yue;
    private TextView zhou;
    private Handler handler;
     @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tomato_statistics);
        days=new int[49][2];
handler=new Handler();
        toolbar= (Toolbar) findViewById(R.id.statistics_toolbar);
        setSupportActionBar(toolbar);
        random=new Random(System.currentTimeMillis());
         xDay=new String[7];
         yDay=new int[7];
         xWeek=new String[7];
         yWeek=new int[7];
         xMon=new String[7];
         yMon=new int[7];
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("番茄统计");
    ri= (TextView) findViewById(R.id.ri);
         yue= (TextView) findViewById(R.id.yue);
         zhou= (TextView) findViewById(R.id.zhou);
         ri.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 ri.setTextColor(Color.RED);
                 yue.setTextColor(Color.BLACK);
                 zhou.setTextColor(Color.BLACK);
                 chart.setxAxis(xDay);
                 chart.setyAxis(yDay);
                 chart.startAnim();
             }
         });
       yue.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ri.setTextColor(Color.BLACK);
               yue.setTextColor(Color.RED);
               zhou.setTextColor(Color.BLACK);
               chart.setxAxis(xMon);
               chart.setyAxis(yMon);
               chart.startAnim();
           }
       });
         zhou.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 ri.setTextColor(Color.BLACK);
                 yue.setTextColor(Color.BLACK);
                 zhou.setTextColor(Color.RED);
                 chart.setxAxis(xWeek);
                 chart.setyAxis(yWeek);
                 chart.startAnim();
             }
         });
         all_today= (TextView) findViewById(R.id.statistics_num_today);
        worktime_today= (TextView) findViewById(R.id.statistics_worktime_today);
        bad_today= (TextView) findViewById(R.id.statistics_bad_num_today);
        addTomato= (LinearLayout) findViewById(R.id.statistics_add_today);
        all= (TextView) findViewById(R.id.statistics_all_num);
        worktime= (TextView) findViewById(R.id.statistics_all_worktime);
        bad= (TextView) findViewById(R.id.statistics_all_bad);
        Calendar calendar=Calendar.getInstance();
        editor=getSharedPreferences("tomato",MODE_PRIVATE);
        all_t=editor.getInt(calendar.get(Calendar.YEAR)+""+(calendar.get(Calendar.MONTH)+1)+""+calendar.get(Calendar.DAY_OF_MONTH)+"good",0);
        bad_t=editor.getInt(calendar.get(Calendar.YEAR)+""+(calendar.get(Calendar.MONTH)+1)+""+calendar.get(Calendar.DAY_OF_MONTH)+"bad",0);
        all_=editor.getInt("allGood",0);
        bad_=editor.getInt("allBad",0);
        all_today.setText(""+all_t);
      //  worktime_today.setText(""+((float)editor.getInt(calendar.get(Calendar.YEAR)+""+(calendar.get(Calendar.MONTH)+1)+""+calendar.get(Calendar.DAY_OF_MONTH)+"min",0)/60));
        bad_today.setText(""+bad_t);
        all.setText(""+all_);
      //  worktime.setText(""+((float)editor.getInt("allMin",0)/60));
        bad.setText(""+bad_);
          loadData();
        String workToday=String.valueOf((float)editor.getInt(calendar.get(Calendar.YEAR)+""+(calendar.get(Calendar.MONTH)+1)+""+calendar.get(Calendar.DAY_OF_MONTH)+"min",0)/60);
        String workAll=String.valueOf((float)editor.getInt("allMin",0)/60);
        int num=workToday.indexOf(".");
        if(num!=-1&&num+3<=workToday.length())
        workToday=workToday.substring(0,workToday.indexOf(".")+3);
        num=workAll.indexOf(".");
        if(num!=-1&&num+3<=workAll.length())
        workAll=workAll.substring(0,workAll.indexOf(".")+3);
        worktime_today.setText(workToday);
        worktime.setText(workAll);
        for(int i=0;i<all_t;i++)
        {
            ImageView imageView=new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setImageDrawable(getResources().getDrawable(Tomato.draw[random.nextInt(14)]));
            addTomato.addView(imageView,new FrameLayout.LayoutParams((int)(40*getResources().getDisplayMetrics().density), ViewGroup.LayoutParams.MATCH_PARENT));
        }
         chart= (Chart) findViewById(R.id.chart);

 handler.postDelayed(new Runnable() {
     @Override
     public void run() {
         ri.setTextColor(Color.RED);
         chart.setxAxis(xDay);
         chart.setyAxis(yDay);
         chart.startAnim();
     }
 },500);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       if(item.getItemId()==android.R.id.home)
           finish();
        return super.onOptionsItemSelected(item);
    }
    public void loadData()
    {
        int k=0;

        for(int i=0;i<49;i++)
        {Calendar calendar=Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR,-i);
            if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY)
            {
                xWeek[6-k]=calendar.get(Calendar.DAY_OF_MONTH)+"";
                yWeek[6-k]=editor.getInt(calendar.get(Calendar.YEAR)+""+calendar.get(Calendar.WEEK_OF_YEAR),0);
            k++;
            }
            days[i][0]=calendar.get(Calendar.DAY_OF_MONTH);
            days[i][1]=editor.getInt(calendar.get(Calendar.YEAR)+""+(calendar.get(Calendar.MONTH)+1)+""+calendar.get(Calendar.DAY_OF_MONTH)+"good",0);
        }
        for (int i=0;i<7;i++)
        {
            Calendar calendar=Calendar.getInstance();
            calendar.add(Calendar.MONTH,-i);
            xMon[6-i]=(calendar.get(Calendar.MONTH)+1)+"";
            yMon[6-i]=editor.getInt(calendar.get(Calendar.YEAR)+""+(calendar.get(Calendar.MONTH)+1),0);
        }

        for(int i=0;i<7;i++)
        {
            xDay[i]=days[6-i][0]+"";
            yDay[i]=days[6-i][1];
        }
    }


}
