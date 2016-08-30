package com.firstapp.steven.mishu.EditDay;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firstapp.steven.mishu.Calendar.LunarCalendar;
import com.firstapp.steven.mishu.MainActivityView.SelectDialog;
import com.firstapp.steven.mishu.PictureDialog;
import com.firstapp.steven.mishu.R;
import com.firstapp.steven.mishu.UserIcon;
import com.firstapp.steven.mishu.data.Birthday;
import com.firstapp.steven.tomato.Tomato;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.L;
import com.soundcloud.android.crop.Crop;
import com.soundcloud.android.crop.CropImageActivity;
import com.soundcloud.android.crop.CropImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

/**
 * Created by steven on 2016/8/23.
 */

public class EditBirthdayActivity extends AppCompatActivity {
    Toolbar toolbar;
    Birthday birthday;
    EditText name;
    MyButton boy,girl;
    TextView date;
    EditText phoneNum;
    TextView notify;
    EditText relationship;
    Calendar calendar;
    TextView lunchMon;
    TextView lunchDay;
    UserIcon icon;
    ImageLoader loader;
    String icon_address=new String();
    HashSet<String> notify_days;
    Calendar lunar;
    TextView birthday_of_this_year;
    ImageView imageView;
    File f;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_birthday);
        toolbar= (Toolbar) findViewById(R.id.birthday_edit_toolbar);
        setSupportActionBar(toolbar);
        notify_days= new HashSet<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("");
        birthday=new Birthday();
        birthday.setHashSet(notify_days);
        lunar=Calendar.getInstance();
        icon= (UserIcon) findViewById(R.id.birthday_select_icon);
        lunchMon= (TextView) findViewById(R.id.luanchMon);
        lunchDay= (TextView) findViewById(R.id.luanchDay);
        name= (EditText) findViewById(R.id.edit_birthday_name);
        boy= (MyButton) findViewById(R.id.edit_sex_boy);
        girl= (MyButton) findViewById(R.id.ediit_sex_girl);
        date= (TextView) findViewById(R.id.edit_bitrhday_date);
        phoneNum= (EditText) findViewById(R.id.edit_phone);
        notify= (TextView) findViewById(R.id.edit_notify);
        relationship= (EditText) findViewById(R.id.edit_guanxi);
        birthday_of_this_year= (TextView) findViewById(R.id.birthday_this_year);
        imageView= (ImageView) findViewById(R.id.edit_birthday_send);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phoneNum.getText().toString().equals(""))
                    Toast.makeText(EditBirthdayActivity.this,"请输入手机",Toast.LENGTH_SHORT).show();
                else {
                    String s=phoneNum.getText().toString();
                   Intent intent=new Intent();
                    intent.setAction(Intent.ACTION_SENDTO);
                    //需要发短息的号码,电话号码之间用“;”隔开
                    intent.setData(Uri.parse("smsto:"+s+";"));
                    intent.putExtra("sms_body", "");
                    startActivity(intent);
                }
            }
        });
        boy.single.clear();
        boy.single.add(boy);
        boy.single.add(girl);
        f=new File(Environment.getExternalStorageDirectory(),"new_take"+".jpg");
        loader=ImageLoader.getInstance();
        loader.init(ImageLoaderConfiguration.createDefault(this));
         calendar=Calendar.getInstance();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditBirthdayActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        date.setText(i+"年"+(i1+1)+"月"+i2+"日");
                        calendar.set(i,i1,i2);
                        LunarCalendar lun=new LunarCalendar();
lun.getLunarDate(i,i1+1,i2,true);
                        lunchMon.setText(lun.getMonth());
                        lunchDay.setText(lun.getLunchDay());
                        lunar=fromLunarToGregorian(lun.getMon(),lun.getDay());
                        birthday_of_this_year.setText(lunar.get(Calendar.YEAR)+"年"+(lunar.get(Calendar.MONTH)+1)+"月"+lunar.get(Calendar.DAY_OF_MONTH)+"日");
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        birthday.setThisyear(lunar);
        Intent intent=getIntent();
        if(intent!=null)
        {
            Birthday d= (Birthday) intent.getSerializableExtra("changeBi");
            if(d!=null)
            {
                name.setText(d.getName());
                if(d.getSex().equals("男"))
                boy.start();
                else girl.start();
                calendar=d.getDate();
                lunar=d.getThisyear();
                birthday_of_this_year.setText(lunar.get(Calendar.YEAR)+"年"+(lunar.get(Calendar.MONTH)+1)+"月"+lunar.get(Calendar.DAY_OF_MONTH)+"日");

                date.setText(d.getDate().get(Calendar.YEAR)+"年"+(d.getDate().get(Calendar.MONTH)+1)+"月"+d.getDate().get(Calendar.DAY_OF_MONTH)+"日");
                phoneNum.setText(d.getPhone());
                LunarCalendar lunarCalendar=new LunarCalendar();
                lunarCalendar.getLunarDate(d.getDate().get(Calendar.YEAR),(d.getDate().get(Calendar.MONTH)+1),d.getDate().get(Calendar.DAY_OF_MONTH),true);
                lunchMon.setText(lunarCalendar.getMonth());
                lunchDay.setText(lunarCalendar.getLunchDay());
                relationship.setText(d.getRelationship());

birthday=d;
                if(birthday.getHashSet().size()!=0)
                {
                String s="";
                    if(birthday.getHashSet().contains("0"))
                        s+="当天/ ";
                    if(birthday.getHashSet().contains("1"))
                        s+="提前1天,";
                    if(birthday.getHashSet().contains("3"))
                        s+="提前3天,";
                    if(birthday.getHashSet().contains("7"))
                        s+="提前7天,";
                    if(birthday.getHashSet().contains("15"))
                        s+="提前15天,";
                    if(birthday.getHashSet().contains("30"))
                        s+="提前30天";
                    notify.setText(s);
                }
            }
        }
        birthday.setThisyear(lunar);
        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SelectDialog(EditBirthdayActivity.this,notify,birthday.getHashSet()).show();
            }
        });

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
new PictureDialog(EditBirthdayActivity.this,f).show();
            }
        });
        if(birthday.getPhoto_id()==-1)
            birthday.setPhoto_id((int) (System.currentTimeMillis()%7));
         if(!birthday.getIcon_add().equals(""))
            loader.displayImage(birthday.getIcon_add(),icon);
        else icon.setImageDrawable(getResources().getDrawable(UserIcon.photos[birthday.getPhoto_id()]));

    }
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.edit_day,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.yes:
                if(name.getText().toString().equals(""))
                    Toast.makeText(this,"请输入姓名",Toast.LENGTH_SHORT).show();
                else if(!boy.isChecked()&&!girl.isChecked())
                    Toast.makeText(this,"请选择性别",Toast.LENGTH_SHORT).show();
                else if(date.getText().equals(""))
                    Toast.makeText(this,"请选择生日",Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = getIntent();
                    birthday.setName(name.getText().toString());
                    birthday.setDate(calendar);
                    birthday.setPhone(phoneNum.getText().toString());
                    birthday.setRelationship(relationship.getText().toString());
                    birthday.setSex(boy.isChecked() ? "男" : "女");
                    birthday.setThisyear(lunar);

save();
                    intent.putExtra("birthday", birthday);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
    public void save()
    {

        SharedPreferences.Editor editor=getSharedPreferences("birthday",MODE_PRIVATE).edit();
        editor.clear();
        ArrayList<Birthday> list= (ArrayList<Birthday>) BirthdayActivity.list.clone();
        list.add(birthday);
        if(list!=null)
        {
            for(int i=0;i<list.size();i++)
            {
                Birthday birthday=list.get(i);
                String name=birthday.getName();
                String sex=birthday.getSex();
                String add=birthday.getIcon_add();
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
                editor.putString("birthday"+i+"iconadd",add);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(data.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);

        }
        else if(requestCode==12345&&resultCode == RESULT_OK)
        {

            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inSampleSize=10;
            Uri uri=Uri.fromFile(f);
            beginCrop(uri);

        }
    }
    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped_" + System.currentTimeMillis() + ".jpg"));
        Crop.of(source, destination).asSquare().start(this);
    }
    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            Uri uri = Crop.getOutput(result);
            String imagePath = uri.toString();
            birthday.setIcon_add(imagePath);
            ImageView imageView=icon;
            loader.displayImage(imagePath,imageView);
            if(f.exists()) f.delete();
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public static Calendar fromLunarToGregorian( int month, int day)
    {
        Calendar calendar=Calendar.getInstance();
        LunarCalendar lunar=new LunarCalendar();

        int lunarM=lunar.getMon();
        int lunarD=lunar.getDay();
        Calendar calendar1=Calendar.getInstance();
        Calendar calendar2=Calendar.getInstance();

        calendar2.add(Calendar.YEAR,2);
        while(calendar1.getTimeInMillis()<calendar2.getTimeInMillis())
        {
            lunar.getLunarDate(calendar1.get(Calendar.YEAR),calendar1.get(Calendar.MONTH)+1,calendar1.get(Calendar.DAY_OF_MONTH),true);
            lunarM=lunar.getMon();
            lunarD=lunar.getDay();
            if(lunarM==month&&lunarD==day)
                break;
            else
            calendar1.add(Calendar.DAY_OF_YEAR,1);
        }
        return calendar1;
    }


}
