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
import com.firstapp.steven.mishu.data.BirthdayAda;
import com.firstapp.steven.mishu.data.ImportantAda;
import com.firstapp.steven.mishu.data.ImportantDay;
import com.soundcloud.android.crop.Crop;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by steven on 2016/8/21.
 */

public class ImportantActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button button;
    RecyclerView recyclerView;
    ImageView add,del,change;
   static ArrayList<ImportantDay> list;
    public static ArrayList<ImportantDay> removeList;
    ImportantAda ada;
    boolean cancel=false;
    ImportantAda.OnRecycleViewClick recycleViewClick;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.importantdat_content);
        toolbar= (Toolbar) findViewById(R.id.important_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("纪念日列表");
        recyclerView= (RecyclerView) findViewById(R.id.important_list);
        removeList=new ArrayList<>();
        add= (ImageView) findViewById(R.id.impor_add_Im);
        del= (ImageView) findViewById(R.id.impor_del_Im);
        change= (ImageView) findViewById(R.id.impor_change);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        list=new ArrayList<>();
        ada=new ImportantAda(this,list);
        recyclerView.setAdapter(ada);
        recyclerView.setLayoutManager(manager);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ImportantActivity.this,EditImportantActivity.class);
                startActivityForResult(intent,999);
            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<removeList.size();i++)
                {
                    ImportantDay day=removeList.get(i);
                    if(list.contains(day)) list.remove(day);
                }
                cancel=false;
                removeList.clear();
                ada.setGone(true);
                ada.notifyDataSetChanged();
                button.setText("添加纪念日");
                del.setVisibility(View.GONE);
                add.setVisibility(View.VISIBLE);
                change.setVisibility(View.VISIBLE);
                onSave();
            }
        });
        button= (Button) findViewById(R.id.add_important);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!cancel){
                Intent intent=new Intent(ImportantActivity.this,EditImportantActivity.class);
                startActivityForResult(intent,999);}
                else {
                    for(int i=0;i<removeList.size();i++)
                    {
                        ImportantDay day=removeList.get(i);
                        if(list.contains(day)) list.remove(day);
                    }
                    cancel=false;
                    removeList.clear();
                    ada.setGone(true);

                    ada.notifyDataSetChanged();
                    button.setText("添加纪念日");
                    del.setVisibility(View.GONE);
                    add.setVisibility(View.VISIBLE);
                    change.setVisibility(View.VISIBLE);
                    onSave();
                }
            }
        });
        recycleViewClick=new ImportantAda.OnRecycleViewClick() {
            @Override
            public void onClick(int item) {
                Intent intent=new Intent(ImportantActivity.this,EditImportantActivity.class);
ImportantDay day=list.get(item);
                intent.putExtra("changeIm",day);
                intent.putExtra("position",item);
                startActivityForResult(intent,1);
            }
        };
        ada.setOnRecycleViewClick(recycleViewClick);
        ada.setLongClick(new BirthdayAda.OnRecyclevireLongClick() {
            @Override
            public void onClick(int item) {
                button.setText("确定");
                ada.setGone(false);
                add.setVisibility(View.GONE);
                change.setVisibility(View.GONE);
                del.setVisibility(View.VISIBLE);
                cancel=true;
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setText("确定");
                ada.setGone(false);
                add.setVisibility(View.GONE);
                del.setVisibility(View.VISIBLE);
                change.setVisibility(View.GONE);
                cancel=true;
            }
        });
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
                    ImportantDay day= (ImportantDay) data.getSerializableExtra("important");
                    list.add(day);
                    Collections.sort(list);
                    ada.notifyDataSetChanged();
                }
                break;
            case 1:if(resultCode==RESULT_OK)
            {
                int po=data.getIntExtra("position",0);
                list.remove(po);
                list.add((ImportantDay) data.getSerializableExtra("important"));
                Collections.sort(list);
                onSave();
                ada.notifyDataSetChanged();
            }
        }
    }
    public void load()
    {
        list.clear();
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
            list.add(day);
        }
        Collections.sort(list);
        ada.notifyDataSetChanged();
    }
    public void onSave()
    {
        SharedPreferences.Editor editor=getSharedPreferences("importantday",MODE_PRIVATE).edit();
        editor.clear();
       if(list!=null)
        for(int i=0;i<list.size();i++)
        {ImportantDay day=list.get(i);
            editor.putString("important"+i+"name",day.getName());
            editor.putInt("important"+i+"year",day.getDate().get(Calendar.YEAR));
            editor.putInt("important"+i+"month",day.getDate().get(Calendar.MONTH));
            editor.putInt("important"+i+"day",day.getDate().get(Calendar.DAY_OF_MONTH));
            editor.putString("important"+i+"people",day.getPeople());
            editor.putString("important"+i+"story",day.getStory());
            editor.putString("important"+i+"beizhu",day.getBeizhu());
             editor.putString("important"+i+"add",day.getIcon_add());
            editor.putInt("important"+i+"id",day.getPhoto_id());
            editor.putStringSet("important" + i + "set",day.getHashSet());

        }
        editor.putInt("size",list.size());
        editor.apply();
    }
}
