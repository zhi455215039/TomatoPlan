package com.firstapp.steven.mishu.EditDay;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firstapp.steven.mishu.Calendar.DayIntem;
import com.firstapp.steven.mishu.CategoryDialog;
import com.firstapp.steven.mishu.DialogRemark;
import com.firstapp.steven.mishu.DialogRepeat;
import com.firstapp.steven.mishu.R;
import com.firstapp.steven.mishu.data.Day_item;
import com.firstapp.steven.mishu.service.ImportantNotificationService;

import java.util.Calendar;
import java.util.HashSet;

/**
 * Created by steven on 2016/8/20.
 */


public class EditDayActivity extends AppCompatActivity {
    private Toolbar toolbar;
private EditText edit_title;
    private TextView edit_day;
    private TextView fromTime;
    private TextView toTime;

    private Day_item item;
    private LinearLayout gridLayout;
    private Calendar calendar;
    private int year;
    private int month;
    private int day;
    private int fromHour;
    private int fromMinute;
    private float density;
    private Item complete;
    private HashSet<String> notify;
    private LinearLayout add_imformation;
    private String oldFileName="";
private View category_,repeat_,remark_;
    private int oldItemRepeatDays=-1;
    private Day_item oldItem;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_day);
        toolbar= (Toolbar) findViewById(R.id.edit_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setTitle("");
        notify=new HashSet<>();
        item=new Day_item();
        item.setCreatTime(System.currentTimeMillis()+"哈");
        calendar=Calendar.getInstance();
        density=getResources().getDisplayMetrics().density;
        edit_title= (EditText) findViewById(R.id.edit_title);
        add_imformation= (LinearLayout) findViewById(R.id.add_imformation);
        edit_day= (TextView) findViewById(R.id.edit_date);
        complete= (Item) findViewById(R.id.complete);
        //edit_day.setText(calendar.get(Calendar.YEAR)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.DAY_OF_MONTH));
        fromTime= (TextView) findViewById(R.id.edit_time_from);
        toTime= (TextView) findViewById(R.id.edit_time_to);
gridLayout= (LinearLayout) findViewById(R.id.item_click);
  Intent intent=getIntent();
        if(intent!=null)
        {
            Day_item day_item= (Day_item) intent.getSerializableExtra("day");
            if(day_item!=null){
            edit_title.setText(day_item.getTheme());
                if(day_item.getYear()!=-1)
                {
                    edit_day.setTextColor(Color.BLACK);
                    edit_day.setTextSize(density*8);
                }
            edit_day.setText(day_item.getYear()+"年"+day_item.getMonth()+"月"+day_item.getDay()+"日");
                if(day_item.getFromMinute()!=-1&&day_item.getFromHour()!=-1)
                {
                    fromTime.setTextSize(8*density);
                    fromTime.setTextColor(Color.BLACK);
                }
            fromTime.setText((day_item.getFromHour()>9?day_item.getFromHour():("0"+day_item.getFromHour()))+":"+(day_item.getFromMinute()>9?day_item.getFromMinute():"0"+day_item.getFromMinute()));
                if(day_item.getToMinute()!=-1&&day_item.getToHour()!=-1)
                {
                    toTime.setTextSize(8*density);
                    toTime.setTextColor(Color.BLACK);
                }
            toTime.setText((day_item.getToHour()>9?day_item.getToHour():("0"+day_item.getToHour()))+":"+(day_item.getToMinute()>9?day_item.getFromMinute():"0"+day_item.getToMinute()));
                if(day_item.isFinish())
                {   complete.getTextView().setText("已完成");
                    complete.getImageView().setImageDrawable(getResources().getDrawable(R.drawable.ic_action_wancheng));
                }
                else {
                    complete.getTextView().setText("未完成");
                    complete.getImageView().setImageDrawable(getResources().getDrawable(R.drawable.weiwancheng));

                }
            updateData(0);
            item=day_item.cloneItem();

                oldItem=day_item.cloneItem();
                oldItemRepeatDays=item.getRepeatDays();
               oldFileName=item.getYear()+""+item.getMonth()+""+item.getDay()+""+item.getTheme()+""+item.getFromHour()+""+item.getFromMinute()+""+item.getFromHour()+""+item.getToMinute()+"";
            }
        }


        edit_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          DatePickerDialog dialog=     new DatePickerDialog(EditDayActivity.this, new DatePickerDialog.OnDateSetListener() {
                   @Override
                   public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
edit_day.setTextColor(Color.BLACK);
                       edit_day.setTextSize(density*7);
                       edit_day.setText(i+"年"+(i1+1)+"月"+i2+"日");
                      item.setYear(i);
                       item.setMonth(i1+1);
                       item.setDay(i2);
                      // Toast.makeText(EditDayActivity.this,calendar.get(Calendar.MONTH)+"",Toast.LENGTH_SHORT).show();

                   }
               },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

dialog.show();
            }
        });
        fromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(EditDayActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        fromTime.setTextSize(10*density);
                        fromTime.setTextColor(Color.BLACK);
                        fromTime.setText((i>9?i:("0"+i))+":"+(i1>9?i1:"0"+i1));
                        fromHour=i;
                        fromMinute=i1;
                       item.setFromHour(i);
                        item.setFromMinute(i1);
                    }
                },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();
            }
        });
        toTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(EditDayActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        if(i<=fromHour||(i==fromHour&&fromMinute<=i1))
                            Toast.makeText(EditDayActivity.this,"结束时间应该晚于开始时间",Toast.LENGTH_SHORT).show();
                      else  {
                            toTime.setTextSize(10*density);
                            toTime.setTextColor(Color.BLACK);
                            toTime.setText((i>9?i:("0"+i))+":"+(i1>9?i1:"0"+i1));
                           item.setToHour(i);
                            item.setToMinute(i1);
                        }
                    }
                },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();
            }
        });
        for(int i=0;i<gridLayout.getChildCount();i++)
        {
            Item item= (Item) gridLayout.getChildAt(i);
            item.setListener(new Item.OnItemClickedListener() {
                @Override
                public void onClick(Item item) {
                    switch (item.getId())
                    {
                        case 1:alert();break;
                        case 2:repeat();break;
                        case 3:upLoad();break;
                        case 4:biaoji(item);break;
                        case 5:delete();break;
                    }
                }
            });
        }

updateData(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.edit_day,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.yes:

                save();
                setResult(RESULT_OK,null);
                finish();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }


    public void save()
    {
        if(edit_title.getText().toString().equals(""))
            Toast.makeText(this,"主题不可为空",Toast.LENGTH_SHORT).show();
        else if(edit_day.getText().toString().equals(""))
            Toast.makeText(this,"日期不可为空",Toast.LENGTH_SHORT).show();
else if(fromTime.getText().toString().equals(""))
            Toast.makeText(this,"开始时间不可为空",Toast.LENGTH_SHORT).show();
else if(toTime.getText().toString().equals(""))
            Toast.makeText(this,"日期不可为空",Toast.LENGTH_SHORT).show();
else
        {
        item.setTheme(edit_title.getText().toString());
       if(oldItem!=null&&oldItem.equals(item)&&oldItem.getRepeatDays()==item.getRepeatDays()&&oldItem.getCategory().equals(item.getCategory())&&oldItem.isFinish()==item.isFinish()&&oldItem.getBeiwang().equals(item.getBeiwang()))
       {

       }
       else
           {

                   item.setCreatTime(System.currentTimeMillis()+"哈");

               //String crea = System.currentTimeMillis() + "";
               String fileName = item.getYear() + "" + item.getMonth() + "" + item.getDay() + "" + item.getTheme() + "" + item.getFromHour() + "" + item.getFromMinute() + "" + item.getFromHour() + "" + item.getToMinute() + "";
               SharedPreferences.Editor editor = getSharedPreferences(item.getYear() + "" + item.getMonth() + "" + item.getDay() + "edit_day", MODE_PRIVATE).edit();
               SharedPreferences reader = getSharedPreferences(item.getYear() + "" + item.getMonth() + "" + item.getDay() + "edit_day", MODE_PRIVATE);


               SharedPreferences.Editor editorRepeat = getSharedPreferences("repeatdays", MODE_PRIVATE).edit();
               SharedPreferences readerRepeat = getSharedPreferences("repeatdays", MODE_PRIVATE);
               HashSet<String> repeatdays = (HashSet<String>) readerRepeat.getStringSet("dayitem", new HashSet<String>());


               HashSet<String> hashSet = (HashSet<String>) reader.getStringSet("dayitem", new HashSet<String>());
               if (!oldFileName.equals("")) {
                   hashSet.remove(oldFileName);
                   editor.remove(oldFileName + "day");
                   editor.remove(oldFileName + "month");
                   editor.remove(oldFileName + "year");
                   editor.remove(oldFileName + "fromHour");
                   editor.remove(oldFileName + "fromMinute");
                   editor.remove(oldFileName + "toHour");
                   editor.remove(oldFileName + "toMinute");
                   editor.remove(oldFileName + "theme");
                   editor.remove(oldFileName + "category");
                   editor.remove(oldFileName + "repeat");
                   editor.remove(oldFileName + "beiwang");
                   editor.remove(oldFileName + "finish");
                   editor.remove(oldFileName + "create");
                   repeatdays.remove(oldFileName);
                   editorRepeat.remove(oldFileName + "day");
                   editorRepeat.remove(oldFileName + "month");
                   editorRepeat.remove(oldFileName + "year");
                   editorRepeat.remove(oldFileName + "fromHour");
                   editorRepeat.remove(oldFileName + "fromMinute");
                   editorRepeat.remove(oldFileName + "toHour");
                   editorRepeat.remove(oldFileName + "toMinute");
                   editorRepeat.remove(oldFileName + "theme");
                   editorRepeat.remove(oldFileName + "category");
                   editorRepeat.remove(oldFileName + "repeat");
                   editorRepeat.remove(oldFileName + "beiwang");
                   editorRepeat.remove(oldFileName + "finish");
                   editorRepeat.remove(oldFileName + "create");
               }

               editor.putInt(fileName + "day", item.getDay());
               editor.putInt(fileName + "month", item.getMonth());
               editor.putInt(fileName + "year", item.getYear());
               editor.putInt(fileName + "fromHour", item.getFromHour());
               editor.putInt(fileName + "fromMinute", item.getFromMinute());
               editor.putInt(fileName + "toHour", item.getToHour());
               editor.putInt(fileName + "toMinute", item.getToMinute());
               editor.putString(fileName + "theme", item.getTheme());
               editor.putString(fileName + "category", item.getCategory());
               editor.putInt(fileName + "repeat", item.getRepeatDays());
               editor.putString(fileName + "beiwang", item.getBeiwang());
               editor.putBoolean(fileName + "finish", item.isFinish());
               editor.putString(fileName + "create", item.getCreatTime());
               hashSet.add(fileName);
               editor.putStringSet("dayitem", hashSet);

               if (item.getRepeatDays() != -1) {
                   editorRepeat.putInt(fileName + "day", item.getDay());
                   editorRepeat.putInt(fileName + "month", item.getMonth());
                   editorRepeat.putInt(fileName + "year", item.getYear());
                   editorRepeat.putInt(fileName + "fromHour", item.getFromHour());
                   editorRepeat.putInt(fileName + "fromMinute", item.getFromMinute());
                   editorRepeat.putInt(fileName + "toHour", item.getToHour());
                   editorRepeat.putInt(fileName + "toMinute", item.getToMinute());
                   editorRepeat.putString(fileName + "theme", item.getTheme());
                   editorRepeat.putString(fileName + "category", item.getCategory());
                   editorRepeat.putInt(fileName + "repeat", item.getRepeatDays());
                   editorRepeat.putString(fileName + "beiwang", item.getBeiwang());
                   editorRepeat.putBoolean(fileName + "finish", item.isFinish());
                   editorRepeat.putString(fileName + "create", item.getCreatTime());

                   repeatdays.add(fileName);

               }
               editorRepeat.putStringSet("dayitem", repeatdays);

               editor.apply();


               editorRepeat.apply();




           }
       } }

    public void alert()
    {
new CategoryDialog(this,item).setOnDialogDismiss(new OnDialogDismiss() {
    @Override
    public void OnDismiss() {
        updateData(1);
    }
}).show();

    }
    public void repeat()
    {
new DialogRepeat(this,item).setOnDialogDismiss(new OnDialogDismiss() {
    @Override
    public void OnDismiss() {
        updateData(2);
    }
}).show();
    }
    public void upLoad()
    {
new DialogRemark(this,item).setOnDialogDismiss(new OnDialogDismiss() {
    @Override
    public void OnDismiss() {
        updateData(3);
    }
}).show();
    }
    public void delete()
    {
        new AlertDialog.Builder(this).setTitle("确定要删除这条计划吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(oldFileName.equals(""))
                            Toast.makeText(EditDayActivity.this,"该计划还没添加",Toast.LENGTH_SHORT).show();
                        else {

                            deleteItem();
                            setResult(RESULT_OK,null);
                            finish();
                    }
                }

  }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }

    public void updateData(int num)
    {

        if(!item.getCategory().equals("")&&(num==0||num==1))
        {
            if(category_!=null) add_imformation.removeView(category_);
            View view=getLayoutInflater().inflate(R.layout.editday_add_item,null);
            category_=view;
            ImageView imageView= (ImageView) view.findViewById(R.id.editday_add_item_image);
            TextView textView= (TextView) view.findViewById(R.id.editday_add_item_text);
            ImageView del= (ImageView) view.findViewById(R.id.editday_add_item_del);
            add_imformation.addView(view,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (50*density)));
            if(item.getCategory().equals("工作") ){
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.work));
                textView.setText("工作");
            }
            if(item.getCategory().equals("娱乐") ){
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.yule));
                textView.setText("娱乐");
            }
            if(item.getCategory().equals("学习") ){
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.study));
                textView.setText("学习");
            }
            if(item.getCategory().equals("运动") ){
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.sport));
                textView.setText("运动");
            }
            if(item.getCategory().equals("旅行") ){
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.train));
                textView.setText("旅行");
            }
            if(item.getCategory().equals("活动") ){
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.activity));
                textView.setText("活动");
            }
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    add_imformation.removeView(category_);
                   add_imformation.requestLayout();
                    item.setCategory("");
                }
            });
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new CategoryDialog(EditDayActivity.this,item).setOnDialogDismiss(new OnDialogDismiss() {
                        @Override
                        public void OnDismiss() {
                            updateData(1);
                        }
                    }).show();
                }
            });

        }
        if(item.getRepeatDays()!=-1&&(num==0||num==2))
        {
            if(repeat_!=null) add_imformation.removeView(repeat_);
            View view=getLayoutInflater().inflate(R.layout.editday_add_item,null);
            repeat_=view;
            ImageView imageView= (ImageView) view.findViewById(R.id.editday_add_item_image);
            TextView textView= (TextView) view.findViewById(R.id.editday_add_item_text);
            ImageView del= (ImageView) view.findViewById(R.id.editday_add_item_del);
            add_imformation.addView(view,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (50*density)));
            if(item.getRepeatDays()==0)
            {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.repeat_icon));
                textView.setText("不重复");
            }
            if(item.getRepeatDays()==1)
            {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.repeat_icon));
                textView.setText("每天重复");
            }
            if(item.getRepeatDays()==7)
            {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.repeat_icon));
                textView.setText("每周重复");
            }
            if(item.getRepeatDays()==14)
            {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.repeat_icon));
                textView.setText("每两周重复");
            }
            if(item.getRepeatDays()==30)
            {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.repeat_icon));
                textView.setText("每月重复");
            }
            if(item.getRepeatDays()==365)
            {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.repeat_icon));
                textView.setText("每年重复");
            }
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    add_imformation.removeView(repeat_);
                    add_imformation.requestLayout();
                    item.setRepeatDays(-1);
                }
            });
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DialogRepeat(EditDayActivity.this,item).setOnDialogDismiss(new OnDialogDismiss() {
                        @Override
                        public void OnDismiss() {
                            updateData(2);
                        }
                    }).show();

                }
            });
        }
        if(!item.getBeiwang().equals("")&&(num==0||num==3))
        {
            if(remark_!=null) add_imformation.removeView(remark_);
            add_imformation.requestLayout();
            View view=getLayoutInflater().inflate(R.layout.editday_add_item,null);
            remark_=view;
            ImageView imageView= (ImageView) view.findViewById(R.id.editday_add_item_image);
            TextView textView= (TextView) view.findViewById(R.id.editday_add_item_text);
            ImageView del= (ImageView) view.findViewById(R.id.editday_add_item_del);
            add_imformation.addView(view,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (50*density)));

            imageView.setImageDrawable(getResources().getDrawable(R.drawable.beizhu));
            textView.setText(item.getBeiwang());
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    add_imformation.removeView(remark_);
                    add_imformation.requestLayout();

                }
            });
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DialogRemark(EditDayActivity.this,item).setOnDialogDismiss(new OnDialogDismiss() {
                        @Override
                        public void OnDismiss() {
                            updateData(3);
                        }
                    }).show();
                }
            });
        }

    }
    public interface OnDialogDismiss{
        public void OnDismiss();
    }
    public void biaoji(final Item itemM)
    {
        new AlertDialog.Builder(this).setTitle("计划是否完成")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        itemM.getTextView().setText("已完成");
                        itemM.getImageView().setImageDrawable(getResources().getDrawable(R.drawable.ic_action_wancheng));
                        item.setFinish(true);
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        itemM.getTextView().setText("未完成");
                        itemM.getImageView().setImageDrawable(getResources().getDrawable(R.drawable.weiwancheng));
                        item.setFinish(false);
                    }
                }).show();
    }
    public void deleteItem()
    {
        SharedPreferences.Editor editor=  getSharedPreferences(item.getYear()+""+item.getMonth()+""+item.getDay()+"edit_day",MODE_PRIVATE).edit();
        SharedPreferences reader=getSharedPreferences(item.getYear()+""+item.getMonth()+""+item.getDay()+"edit_day",MODE_PRIVATE);

        SharedPreferences.Editor editorRepeat=  getSharedPreferences("repeatdays",MODE_PRIVATE).edit();
        SharedPreferences readerRepeat=getSharedPreferences("repeatdays",MODE_PRIVATE);
        HashSet<String> set= (HashSet<String>) readerRepeat.getStringSet("dayitem",new HashSet<String>());



        HashSet<String> hashSet= (HashSet<String>) reader.getStringSet("dayitem",new HashSet<String>());
        HashSet<String> hashS= (HashSet<String>) reader.getStringSet("delName",new HashSet<String>());
        hashSet.remove(oldFileName);
        editor.remove(oldFileName + "day");
        editor.remove(oldFileName + "month");
        editor.remove(oldFileName + "year");
        editor.remove(oldFileName + "fromHour");
        editor.remove(oldFileName + "fromMinute");
        editor.remove(oldFileName + "toHour");
        editor.remove(oldFileName + "toMinute");
        editor.remove(oldFileName + "theme");
        editor.remove(oldFileName + "category");
        editor.remove(oldFileName + "repeat");
        editor.remove(oldFileName + "beiwang");
        editor.remove(oldFileName+"finish");
        editor.remove(oldFileName+"create");


        editor.putStringSet("dayitem",hashSet);
        hashS.add(item.getCreatTime());

        editor.putStringSet("delName",hashS);
        editor.apply();
        if(oldItemRepeatDays!=-1)
        {
            set.remove(oldFileName);
            editorRepeat.remove(oldFileName + "day");
            editorRepeat.remove(oldFileName + "month");
            editorRepeat.remove(oldFileName + "year");
            editorRepeat.remove(oldFileName + "fromHour");
            editorRepeat.remove(oldFileName + "fromMinute");
            editorRepeat.remove(oldFileName + "toHour");
            editorRepeat.remove(oldFileName + "toMinute");
            editorRepeat.remove(oldFileName + "theme");
            editorRepeat.remove(oldFileName + "category");
            editorRepeat.remove(oldFileName + "repeat");
            editorRepeat.remove(oldFileName + "beiwang");
            editorRepeat.remove(oldFileName+"finish");
            editorRepeat.remove(oldFileName+"create");
            editorRepeat.putStringSet("dayitem",set);
        }

        editorRepeat.apply();



        //String crea = System.currentTimeMillis() + "";
        String fileName = item.getYear() + "" + item.getMonth() + "" + item.getDay() + "" + item.getTheme() + "" + item.getFromHour() + "" + item.getFromMinute() + "" + item.getFromHour() + "" + item.getToMinute() + "";
         editor = getSharedPreferences(item.getYear() + "" + item.getMonth() + "" + item.getDay() + "edit_day", MODE_PRIVATE).edit();
         reader = getSharedPreferences(item.getYear() + "" + item.getMonth() + "" + item.getDay() + "edit_day", MODE_PRIVATE);


      editorRepeat = getSharedPreferences("repeatdays", MODE_PRIVATE).edit();
         readerRepeat = getSharedPreferences("repeatdays", MODE_PRIVATE);
        HashSet<String> repeatdays = (HashSet<String>) readerRepeat.getStringSet("dayitem", new HashSet<String>());


         hashSet = (HashSet<String>) reader.getStringSet("dayitem", new HashSet<String>());
        if (!oldFileName.equals("")) {
            hashSet.remove(oldFileName);
            editor.remove(oldFileName + "day");
            editor.remove(oldFileName + "month");
            editor.remove(oldFileName + "year");
            editor.remove(oldFileName + "fromHour");
            editor.remove(oldFileName + "fromMinute");
            editor.remove(oldFileName + "toHour");
            editor.remove(oldFileName + "toMinute");
            editor.remove(oldFileName + "theme");
            editor.remove(oldFileName + "category");
            editor.remove(oldFileName + "repeat");
            editor.remove(oldFileName + "beiwang");
            editor.remove(oldFileName + "finish");
            editor.remove(oldFileName + "create");
            repeatdays.remove(oldFileName);
            editorRepeat.remove(oldFileName + "day");
            editorRepeat.remove(oldFileName + "month");
            editorRepeat.remove(oldFileName + "year");
            editorRepeat.remove(oldFileName + "fromHour");
            editorRepeat.remove(oldFileName + "fromMinute");
            editorRepeat.remove(oldFileName + "toHour");
            editorRepeat.remove(oldFileName + "toMinute");
            editorRepeat.remove(oldFileName + "theme");
            editorRepeat.remove(oldFileName + "category");
            editorRepeat.remove(oldFileName + "repeat");
            editorRepeat.remove(oldFileName + "beiwang");
            editorRepeat.remove(oldFileName + "finish");
            editorRepeat.remove(oldFileName + "create");
        }

        editor.putInt(fileName + "day", item.getDay());
        editor.putInt(fileName + "month", item.getMonth());
        editor.putInt(fileName + "year", item.getYear());
        editor.putInt(fileName + "fromHour", item.getFromHour());
        editor.putInt(fileName + "fromMinute", item.getFromMinute());
        editor.putInt(fileName + "toHour", item.getToHour());
        editor.putInt(fileName + "toMinute", item.getToMinute());
        editor.putString(fileName + "theme", item.getTheme());
        editor.putString(fileName + "category", item.getCategory());
        editor.putInt(fileName + "repeat", item.getRepeatDays());
        editor.putString(fileName + "beiwang", item.getBeiwang());
        editor.putBoolean(fileName + "finish", item.isFinish());
        editor.putString(fileName + "create", item.getCreatTime());
        hashSet.add(fileName);
        editor.putStringSet("dayitem", hashSet);

        if (item.getRepeatDays() != -1) {
            editorRepeat.putInt(fileName + "day", item.getDay());
            editorRepeat.putInt(fileName + "month", item.getMonth());
            editorRepeat.putInt(fileName + "year", item.getYear());
            editorRepeat.putInt(fileName + "fromHour", item.getFromHour());
            editorRepeat.putInt(fileName + "fromMinute", item.getFromMinute());
            editorRepeat.putInt(fileName + "toHour", item.getToHour());
            editorRepeat.putInt(fileName + "toMinute", item.getToMinute());
            editorRepeat.putString(fileName + "theme", item.getTheme());
            editorRepeat.putString(fileName + "category", item.getCategory());
            editorRepeat.putInt(fileName + "repeat", item.getRepeatDays());
            editorRepeat.putString(fileName + "beiwang", item.getBeiwang());
            editorRepeat.putBoolean(fileName + "finish", item.isFinish());
            editorRepeat.putString(fileName + "create", item.getCreatTime());

            repeatdays.add(fileName);

        }
        editorRepeat.putStringSet("dayitem", repeatdays);

        editor.apply();


        editorRepeat.apply();

         editor=  getSharedPreferences(item.getYear()+""+item.getMonth()+""+item.getDay()+"edit_day",MODE_PRIVATE).edit();
        reader=getSharedPreferences(item.getYear()+""+item.getMonth()+""+item.getDay()+"edit_day",MODE_PRIVATE);

         editorRepeat=  getSharedPreferences("repeatdays",MODE_PRIVATE).edit();
         readerRepeat=getSharedPreferences("repeatdays",MODE_PRIVATE);
         set= (HashSet<String>) readerRepeat.getStringSet("dayitem",new HashSet<String>());



        hashSet= (HashSet<String>) reader.getStringSet("dayitem",new HashSet<String>());
         hashS= (HashSet<String>) reader.getStringSet("delName",new HashSet<String>());
        hashSet.remove(oldFileName);
        editor.remove(oldFileName + "day");
        editor.remove(oldFileName + "month");
        editor.remove(oldFileName + "year");
        editor.remove(oldFileName + "fromHour");
        editor.remove(oldFileName + "fromMinute");
        editor.remove(oldFileName + "toHour");
        editor.remove(oldFileName + "toMinute");
        editor.remove(oldFileName + "theme");
        editor.remove(oldFileName + "category");
        editor.remove(oldFileName + "repeat");
        editor.remove(oldFileName + "beiwang");
        editor.remove(oldFileName+"finish");
        editor.remove(oldFileName+"create");


        editor.putStringSet("dayitem",hashSet);
        hashS.add(item.getCreatTime());

        editor.putStringSet("delName",hashS);
        editor.apply();
        if(oldItemRepeatDays!=-1)
        {
            set.remove(oldFileName);
            editorRepeat.remove(oldFileName + "day");
            editorRepeat.remove(oldFileName + "month");
            editorRepeat.remove(oldFileName + "year");
            editorRepeat.remove(oldFileName + "fromHour");
            editorRepeat.remove(oldFileName + "fromMinute");
            editorRepeat.remove(oldFileName + "toHour");
            editorRepeat.remove(oldFileName + "toMinute");
            editorRepeat.remove(oldFileName + "theme");
            editorRepeat.remove(oldFileName + "category");
            editorRepeat.remove(oldFileName + "repeat");
            editorRepeat.remove(oldFileName + "beiwang");
            editorRepeat.remove(oldFileName+"finish");
            editorRepeat.remove(oldFileName+"create");
            editorRepeat.putStringSet("dayitem",set);
        }

        editorRepeat.apply();



    }
    }

