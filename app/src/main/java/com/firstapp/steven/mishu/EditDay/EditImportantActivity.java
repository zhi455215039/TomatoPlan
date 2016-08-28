package com.firstapp.steven.mishu.EditDay;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firstapp.steven.mishu.MainActivityView.SelectDialog;
import com.firstapp.steven.mishu.PictureDialog;
import com.firstapp.steven.mishu.R;
import com.firstapp.steven.mishu.UserIcon;
import com.firstapp.steven.mishu.data.ImportantDay;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

/**
 * Created by steven on 2016/8/23.
 */

public class EditImportantActivity extends AppCompatActivity {
    Toolbar toolbar;
ImportantDay day;
    EditText day_name;
    TextView day_date;
    EditText people;
    EditText story;
    EditText beizhu;
    TextView notify;
    Calendar calendar;
    UserIcon icon;
    File f;
    ImageLoader loader;
HashSet<String> notift_days;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_importantday);
        toolbar= (Toolbar) findViewById(R.id.edit_important_toolbar);
calendar=Calendar.getInstance();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("纪念日");
        notift_days=new HashSet<>();
        day=new ImportantDay();
        icon= (UserIcon) findViewById(R.id.edit_impor_icon);
day.setHashSet(notift_days);

        day_name= (EditText) findViewById(R.id.impor_day_name);
        day_date= (TextView) findViewById(R.id.impor_day_date);
        people= (EditText) findViewById(R.id.impor_day_people);
        story= (EditText) findViewById(R.id.edit_impor_story);
        beizhu= (EditText) findViewById(R.id.impor_day_beizhu);
        notify= (TextView) findViewById(R.id.impor_day_notify);
        f=new File(Environment.getExternalStorageDirectory(),"new_take"+".jpg");
        loader= ImageLoader.getInstance();
        loader.init(ImageLoaderConfiguration.createDefault(this));

notify.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        new SelectDialog(EditImportantActivity.this,notify,notift_days).show();
    }
});
        day_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditImportantActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i,i1,i2);
                        Calendar now=Calendar.getInstance();
                        now.set(Calendar.HOUR,1);
                        now.set(Calendar.SECOND,1);
                        now.add(Calendar.DAY_OF_YEAR,1);

                        if(calendar.getTimeInMillis()>now.getTimeInMillis())
                            Toast.makeText(EditImportantActivity.this,"纪念日应该在明天之前发生",Toast.LENGTH_SHORT).show();
                        else
                        day_date.setText(i+"年"+(i1+1)+"月"+i2+"日");
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        Intent intent=getIntent();
        if(intent!=null)
        {ImportantDay d= (ImportantDay) intent.getSerializableExtra("changeIm");
            if(d!=null){
                calendar=d.getDate();
            day_name.setText(d.getName());
            day_date.setText(d.getDate().get(Calendar.YEAR)+"年"+(d.getDate().get(Calendar.MONTH)+1)+"月"+d.getDate().get(Calendar.DAY_OF_MONTH)+"日");
            people.setText(d.getPeople());
            story.setText(d.getStory());

            beizhu.setText(d.getBeizhu());
            day=d;
                if(day.getHashSet().size()!=0)
                {
                    String s="";
                    if(day.getHashSet().contains("0"))
                        s+="当天/ ";
                    if(day.getHashSet().contains("1"))
                        s+="提前1天,";
                    if(day.getHashSet().contains("3"))
                        s+="提前3天,";
                    if(day.getHashSet().contains("7"))
                        s+="提前7天,";
                    if(day.getHashSet().contains("15"))
                        s+="提前15天,";
                    if(day.getHashSet().contains("30"))
                        s+="提前30天";
                    notify.setText(s);
                }}
        }
        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SelectDialog(EditImportantActivity.this,notify,day.getHashSet()).show();
            }
        });
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PictureDialog(EditImportantActivity.this,f).show();
            }
        });
if(day.getPhoto_id()==-1)
    day.setPhoto_id((int) (System.currentTimeMillis()%7));
  if(day.getIcon_add().equals(""))
  {
      icon.setImageDrawable(getResources().getDrawable(UserIcon.photos[day.getPhoto_id()]));
  }
        else  loader.displayImage(day.getIcon_add(),icon);

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
if(day_name.getText().toString().equals(""))
    Toast.makeText(this,"请输入纪念日名字",Toast.LENGTH_SHORT).show();
               else if(day_date.getText().equals(""))
    Toast.makeText(this,"请选择日期",Toast.LENGTH_SHORT).show();
              else {
    Intent intent=getIntent();

    day.setName(day_name.getText().toString());
    day.setDate(calendar);
    day.setBeizhu(beizhu.getText().toString());
    day.setPeople(people.getText().toString());
    day.setStory(story.getText().toString());

    intent.putExtra("important",day);
    save(day);
    setResult(RESULT_OK,intent);
    finish();
              }


               break;
           case android.R.id.home:
               finish();
       }
        return true;
    }
    public void save(ImportantDay dayy) {
        SharedPreferences.Editor editor = getSharedPreferences("importantday", MODE_PRIVATE).edit();
        editor.clear();
        ArrayList<ImportantDay> list = (ArrayList<ImportantDay>) ImportantActivity.list.clone();
        list.add(dayy);
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                ImportantDay day=list.get(i);
                editor.putString("important" + i + "name", day.getName());
                editor.putInt("important" + i + "year", day.getDate().get(Calendar.YEAR));
                editor.putInt("important" + i + "month", day.getDate().get(Calendar.MONTH));
                editor.putInt("important" + i + "day", day.getDate().get(Calendar.DAY_OF_MONTH));
                editor.putString("important" + i + "people", day.getPeople());
                editor.putString("important" + i + "story", day.getStory());
                editor.putString("important" + i + "beizhu", day.getBeizhu());
                editor.putString("important" + i + "add",day.getIcon_add());
                editor.putInt("important" + i + "id",day.getPhoto_id());
                editor.putStringSet("important" + i + "set",day.getHashSet());
            }
            editor.putInt("size", list.size());
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
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped_important" + System.currentTimeMillis() + ".jpg"));
        Crop.of(source, destination).asSquare().start(this);
    }
    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            Uri uri = Crop.getOutput(result);
            String imagePath = uri.toString();
            day.setIcon_add(imagePath);
            ImageView imageView=icon;
            loader.displayImage(imagePath,imageView);
            if(f.exists()) f.delete();
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
