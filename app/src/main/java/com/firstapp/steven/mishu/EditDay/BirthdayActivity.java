package com.firstapp.steven.mishu.EditDay;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.firstapp.steven.mishu.R;
import com.firstapp.steven.mishu.data.Birthday;
import com.firstapp.steven.mishu.data.BirthdayAda;
import com.firstapp.steven.mishu.data.ImportantAda;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by steven on 2016/8/21.
 */

public class BirthdayActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button button;
    RecyclerView recyclerView;
    Menu menu;
    BirthdayAda ada;
    ImageView add;
    ImageView del,change;
   public static ArrayList<Birthday> removeList;
   static boolean cancel=false;
  static   ArrayList<Birthday> list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.birthday_content);
        toolbar= (Toolbar) findViewById(R.id.birthday_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("生日列表");
        button= (Button) findViewById(R.id.add_birthday);
        removeList=new ArrayList<>();
        add= (ImageView) findViewById(R.id.birthday_add_Im);
        del= (ImageView) findViewById(R.id.birthday_del_Im);
        change= (ImageView) findViewById(R.id.birthday_change);
        recyclerView= (RecyclerView) findViewById(R.id.birthday_list);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        list=new ArrayList<>();

        ada=new BirthdayAda(this,list);
        recyclerView.setAdapter(ada);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BirthdayActivity.this,EditBirthdayActivity.class);
                startActivityForResult(intent,999);
            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i=0;i<removeList.size();i++)
                {
                    Birthday birthday=removeList.get(i);
                    if(list.contains(birthday))
                        list.remove(birthday);
                }
                ada.setGone(true);
                button.setText("添加生日");
                cancel=false;
                removeList.clear();
                add.setVisibility(View.VISIBLE);
                del.setVisibility(View.GONE);
                change.setVisibility(View.VISIBLE);
                onSave();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!cancel){
                Intent intent=new Intent(BirthdayActivity.this,EditBirthdayActivity.class);
                startActivityForResult(intent,999);}
                else {
for(int i=0;i<removeList.size();i++)
{
    Birthday birthday=removeList.get(i);
    if(list.contains(birthday))
        list.remove(birthday);
}
                    ada.setGone(true);
                    button.setText("添加生日");
                    cancel=false;
                    removeList.clear();
                    add.setVisibility(View.VISIBLE);
                    del.setVisibility(View.GONE);
                    change.setVisibility(View.VISIBLE);
                    onSave();
                }
            }
        });
        ImportantAda.OnRecycleViewClick onRecycleViewClick=new ImportantAda.OnRecycleViewClick() {
            @Override
            public void onClick(int item) {
                Intent intent=new Intent(BirthdayActivity.this,EditBirthdayActivity.class);
                Birthday day=list.get(item);
                intent.putExtra("changeBi",day);
                intent.putExtra("position",item);
                startActivityForResult(intent,1);
            }
        };
        ada.setOnRecycleViewClick(onRecycleViewClick);
        BirthdayAda.OnRecyclevireLongClick onRecyclevireLongClick=new BirthdayAda.OnRecyclevireLongClick() {
            @Override
            public void onClick(int item) {
                ada.setGone(false);
               // menu.findItem(0).setIcon(getResources().getDrawable(R.drawable.ic_action_delete));
                add.setVisibility(View.GONE);
                del.setVisibility(View.VISIBLE);
                change.setVisibility(View.GONE);
                cancel=true;

                button.setText("确定");
            }
        };
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ada.setGone(false);
                // menu.findItem(0).setIcon(getResources().getDrawable(R.drawable.ic_action_delete));
                add.setVisibility(View.GONE);
                del.setVisibility(View.VISIBLE);
                change.setVisibility(View.GONE);
                cancel=true;

                button.setText("确定");
            }
        });
        ada.setOnRecyclevireLongClick(onRecyclevireLongClick);
load();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {

            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case 999:
                if(resultCode==RESULT_OK)
                {
                   // Toast.makeText(this,"收到",Toast.LENGTH_SHORT).show();
                    Birthday birthday= (Birthday) data.getSerializableExtra("birthday");
                    list.add(birthday);
                    Collections.sort(list);
                    ada.notifyDataSetChanged();
                }break;
            case 1:
            {
                if(resultCode==RESULT_OK)
                {
                    int po=data.getIntExtra("position",0);
                    Birthday birthday= (Birthday) data.getSerializableExtra("birthday");
                    list.remove(po);
                    list.add(birthday);
                    Collections.sort(list);
                    onSave();
                    ada.notifyDataSetChanged();

                }
            }
        }
    }
public void load()
{
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
        list.add(birthday);
    }
    Collections.sort(list);
    ada.notifyDataSetChanged();
}
public void onSave()
{
    SharedPreferences.Editor editor=getSharedPreferences("birthday",MODE_PRIVATE).edit();
    editor.clear();

    if(list!=null)
    {
        for(int i=0;i<list.size();i++)
        {
            Birthday birthday=list.get(i);
            String name=birthday.getName();
            String sex=birthday.getSex();
            int year=birthday.getDate().get(Calendar.YEAR);
            int month=birthday.getDate().get(Calendar.MONTH);
            int day=birthday.getDate().get(Calendar.DAY_OF_MONTH);
            String phone=birthday.getPhone();
            String relationship=birthday.getRelationship();
            editor.putString("birthday"+i+"name",name);
            editor.putString("birthday"+i+"sex",sex);
            editor.putInt("birthday"+i+"year",year);
            editor.putInt("birthday"+i+"month",month);
            editor.putInt("birthday"+i+"day",day);
            editor.putString("birthday"+i+"phone",phone);
            editor.putString("birthday"+i+"relationship",relationship);
            editor.putString("birthday"+i+"iconadd",birthday.getIcon_add());
            editor.putInt("birthday"+i+"id",birthday.getPhoto_id());
            editor.putStringSet("birthday"+i+"set",birthday.getHashSet());
            editor.putInt("birthday"+i+"thisyear",birthday.getThisyear().get(Calendar.YEAR));
            editor.putInt("birthday"+i+"thismonth",birthday.getThisyear().get(Calendar.MONTH));
            editor.putInt("birthday"+i+"thisday",birthday.getThisyear().get(Calendar.DAY_OF_MONTH));
        }
        editor.putInt("size",list.size());
        editor.apply();
    }
}
}
