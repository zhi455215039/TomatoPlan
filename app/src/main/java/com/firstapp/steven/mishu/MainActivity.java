package com.firstapp.steven.mishu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.firstapp.steven.mishu.Calendar.LunarCalendar;
import com.firstapp.steven.mishu.Calendar.MyCalendar;
import com.firstapp.steven.mishu.Calendar.MyCalendarView;
import com.firstapp.steven.mishu.EditDay.BirthdayActivity;
import com.firstapp.steven.mishu.EditDay.EditDayActivity;
import com.firstapp.steven.mishu.EditDay.ImportantActivity;
import com.firstapp.steven.mishu.data.Birthday;
import com.firstapp.steven.mishu.data.ImportantDay;
import com.firstapp.steven.mishu.note.NoteActivity;
import com.firstapp.steven.mishu.service.BirthdayNotificationService;
import com.firstapp.steven.mishu.service.ImportantNotificationService;
import com.firstapp.steven.tomato.TomatoStatisticsActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;

import cn.aigestudio.datepicker.bizs.calendars.DPCNCalendar;

public class MainActivity extends AppCompatActivity {
private  DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private LinearLayout mainContent;
    private NavigationView leftContent;
    private Toolbar toolbar;
    private long current=0;
 private MyCalendarView myCalendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
findRecentBirthday();
        findImportantDay();
        toolbar= (Toolbar) findViewById(R.id.toolbar);
setSupportActionBar(toolbar);

myCalendarView= (MyCalendarView) findViewById(R.id.my_calendar);
        myCalendarView.setParent(this);
        mainContent= (LinearLayout) findViewById(R.id.main_content);
        leftContent= (NavigationView) findViewById(R.id.drawer_main);
        drawerLayout= (DrawerLayout) findViewById(R.id.activity_main);

        final Calendar calendar=Calendar.getInstance();
        getSupportActionBar().setTitle(calendar.get(Calendar.YEAR)+"年"+(calendar.get(Calendar.MONTH)+1)+"月");
        mDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.app_name,R.string.app_name){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
                supportInvalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        leftContent.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.birthday:
                        Intent intent=new Intent(MainActivity.this, BirthdayActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.importantday:
                        Intent intent1=new Intent(MainActivity.this, ImportantActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.menu_feed:
                        Intent intent2=new Intent(MainActivity.this, EditDayActivity.class);
                        startActivityForResult(intent2,2234);
                        break;

                    case R.id.tomato:
                        Intent intent4=new Intent(MainActivity.this,TomatoActivity.class);
                        startActivity(intent4);
                        break;
                    case R.id.tomato_statistics:
                        Intent intent3=new Intent(MainActivity.this, TomatoStatisticsActivity.class);
                        startActivity(intent3);
                        break;
                }
                drawerLayout.closeDrawers();
                    return false;
                }

        });
        DPCNCalendar dpcnCalendar=new DPCNCalendar();
        String [][]mon_canlendar=dpcnCalendar.buildMonthFestival(2015,7);
        String s="";
        for(int i=0;i<mon_canlendar.length;i++) {
            for (int j = 0; j < mon_canlendar[i].length; j++) {
                if(mon_canlendar[i][j].equals(""))
                    s=s+i+j+"   ";
              else   if(mon_canlendar[i][j].length()==1)
                s = s + mon_canlendar[i][j] + "    ";
                else s = s +mon_canlendar[i][j] + " ";
            }
            s=s+"\n";
        }

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean isOpen=drawerLayout.isDrawerVisible(leftContent);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_delete)
        {
            Intent intent=new Intent(MainActivity.this,EditDayActivity.class);
            startActivityForResult(intent,11223);
        }
        if(mDrawerToggle.onOptionsItemSelected(item))
        {
            drawerLayout.post(new Runnable() {
                @Override
                public void run() {
                    supportInvalidateOptionsMenu();
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        myCalendarView.getMiddle().closeCalendar();
        myCalendarView.getMiddle().setTime(myCalendarView.getMiddle().getYear(),myCalendarView.getMiddle().getMonth());
        myCalendarView.getLeftCalendar().setTime( myCalendarView.getLeftCalendar().getYear(), myCalendarView.getLeftCalendar().getMonth());
        myCalendarView.getRightCalendar().setTime(myCalendarView.getRightCalendar().getYear(),myCalendarView.getRightCalendar().getMonth());
        myCalendarView.getAdd_new_day().setVisibility(View.GONE);
    }
    public void findRecentBirthday()
    {
       // ArrayList<Integer> list=new ArrayList<>();
        ArrayList<Birthday> bList=new ArrayList<>();
        SharedPreferences editor=getSharedPreferences("birthday",MODE_PRIVATE);
        int size=editor.getInt("size",0);
        for(int i=0;i<size;i++)
        {
            Birthday birthday=new Birthday();
            Calendar calendar=Calendar.getInstance();
            birthday.setName( editor.getString("birthday"+i+"name",""));
            birthday.setSex(editor.getString("birthday"+i+"sex",""));
            int y=editor.getInt("birthday"+i+"year",calendar.get(Calendar.YEAR));
            int m=editor.getInt("birthday"+i+"month",calendar.get(Calendar.MONTH));
            int d=editor.getInt("birthday"+i+"day",calendar.get(Calendar.DAY_OF_MONTH));
            calendar.set(y,m,d);
            birthday.setDate(calendar);
            Calendar calendar1=Calendar.getInstance();
            calendar1.set(editor.getInt("birthday"+i+"thisyear",calendar1.get(Calendar.YEAR)),editor.getInt("birthday"+i+"thismonth",calendar1.get(Calendar.MONTH)),editor.getInt("birthday"+i+"thisday",calendar1.get(Calendar.DAY_OF_MONTH)));
            birthday.setThisyear(calendar1);

            birthday.setPhone(editor.getString("birthday"+i+"phone",""));
            birthday.setRelationship(
                    editor.getString("birthday"+i+"relationship",""));
            birthday.setIcon_add(editor.getString("birthday"+i+"iconadd",""));
            birthday.setPhoto_id(editor.getInt("birthday"+i+"id",-1));
            birthday.setHashSet((HashSet<String>) editor.getStringSet("birthday"+i+"set",null));
            //HashSet<String> hashSet=birthday.getHashSet();

            bList.add(birthday);
        }
        Collections.sort(bList);
        int day_count[]={0,1,3,7,15,30};
        for(int i=0;i<day_count.length;i++)
        {
            for(int j=0;j<bList.size();j++)
            {
                Birthday b=bList.get(j);
                if(!b.getHashSet().contains(day_count[i]+""))
                    continue;
                Calendar now=Calendar.getInstance();
                now.add(Calendar.DAY_OF_YEAR,day_count[i]);
                if((b.getThisyear().get(Calendar.MONTH)==now.get(Calendar.MONTH)&&b.getThisyear().get(Calendar.DAY_OF_MONTH)==now.get(Calendar.DAY_OF_MONTH)))
                {
                    Intent intent=new Intent(this,BirthdayNotificationService.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("birthday",b);
                    intent.putExtras(bundle);
                    startService(intent);
                    return;
                }
            }
        }


    }
    public void findImportantDay()
    {
        ArrayList<ImportantDay> bList=new ArrayList<>();
        SharedPreferences editor=getSharedPreferences("importantday",MODE_PRIVATE);
        int size=editor.getInt("size",0);
        for(int i=0;i<size;i++)
        {
            ImportantDay day=new ImportantDay();
            Calendar calendar=Calendar.getInstance();
            day.setName(editor.getString("important"+i+"name",""));
            int y=  editor.getInt("important"+i+"year",calendar.get(Calendar.YEAR));
            int m=  editor.getInt("important"+i+"month",calendar.get(Calendar.MONTH));
            int d= editor.getInt("important"+i+"day",calendar.get(Calendar.DAY_OF_MONTH));
            calendar.set(y,m,d);
            day.setDate(calendar);

            day.setPeople(editor.getString("important"+i+"people",""));
            day.setStory(editor.getString("important"+i+"story",""));
            day.setBeizhu(editor.getString("important"+i+"beizhu",""));
            day.setIcon_add(editor.getString("important"+i+"add",""));
            day.setPhoto_id(editor.getInt("important"+i+"id",-1));
            day.setHashSet((HashSet<String>) editor.getStringSet("important"+i+"set",null));
            bList.add(day);
        }
        Collections.sort(bList);
        int day_count[]={0,1,3,7,15,30};
        for(int i=0;i<day_count.length;i++) {
            for (int j = 0; j < bList.size(); j++) {
                ImportantDay b = bList.get(j);
                if(!b.getHashSet().contains(day_count[i]+"") ) continue;
                Calendar now = Calendar.getInstance();
                now.add(Calendar.DAY_OF_YEAR, day_count[i]);
                if ((b.getDate().get(Calendar.MONTH)==now.get(Calendar.MONTH)&&b.getDate().get(Calendar.DAY_OF_MONTH)==now.get(Calendar.DAY_OF_MONTH))) {
                    Intent intent = new Intent(this, ImportantNotificationService.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("important", b);
                    intent.putExtras(bundle);
                    startService(intent);
                    return;
                }
            }

        }
    }
    public static Calendar fromLunarToGregorian(int year,int month,int day)
    {
        Calendar calendar=Calendar.getInstance();
        LunarCalendar lunar=new LunarCalendar();
        lunar.getLunarDate(year,month,day,true);
        int count=0;

        int lunarM=lunar.getMon();
        int lunarD=lunar.getDay();
        count=LunarCalendar.monthDays(year,lunarM)-lunarD;

        for(int i=lunarM+1;lunarM<=12;i++)
        {
            count+=LunarCalendar.monthDays(year,i);
        }
        for(int i=year+1;i<calendar.get(Calendar.YEAR);i++)
       for(int j=1;j<=12;j++)
       {
           count+=LunarCalendar.monthDays(i,j);
       }
        for(int i=1;i<=calendar.get(Calendar.MONTH);i++)
            count+=LunarCalendar.monthDays(calendar.get(Calendar.YEAR),i);
        count+=lunarD;
        calendar.set(year,month-1,day);
        calendar.add(Calendar.DAY_OF_YEAR,count);

        return calendar;
    }

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis()-current<2000)
            finish();
        else {
            Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
            current=System.currentTimeMillis();
        }
    }
}
