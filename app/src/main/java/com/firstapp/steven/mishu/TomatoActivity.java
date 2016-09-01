package com.firstapp.steven.mishu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.firstapp.steven.mishu.service.TomatoService;
import com.firstapp.steven.tomato.ShowTomato;
import com.firstapp.steven.tomato.Tomato;
import com.firstapp.steven.tomato.TomatoCalcel;
import com.firstapp.steven.tomato.TomatoSetting;

import java.util.Calendar;

/**
 * Created by steven on 2016/8/29.
 */

public class TomatoActivity extends AppCompatActivity {
    TomatoButton tomatoButton;
    Tomato tomato;
    ShowTomato showTomato;
    FrameLayout.LayoutParams params;
    FrameLayout frameLayout;
    ImageView imageView;
    TomatoService service;
    boolean mBound=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tomato);
        frameLayout= (FrameLayout) findViewById(R.id.tomato_frame);
        tomatoButton= (TomatoButton) findViewById(R.id.tomato_button);
        tomatoButton.setParent(frameLayout);
        tomatoButton.setActivity(this);
        tomato= new Tomato(TomatoActivity.this);
        showTomato= (ShowTomato) findViewById(R.id.showTomato);
        params= (FrameLayout.LayoutParams) showTomato.getLayoutParams();
        imageView= (ImageView) findViewById(R.id.tomato_set);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
new TomatoSetting(TomatoActivity.this).show();
            }
        });
        showTomato.setVisibility(View.VISIBLE);
        showTomato.setOnAnimEnd(new ShowTomato.OnAnimEnd() {
            @Override
            public void onEnd() {
                frameLayout.removeView(tomato);
                showTomato.setVisibility(View.GONE);
                 tomato=new Tomato(TomatoActivity.this);
                frameLayout.addView(tomato,params);

                tomato.setVisibility(View.VISIBLE);
               // tomato.startAnim();
               // tomato.startRotate();
                Intent intent = new Intent(TomatoActivity.this, TomatoService.class);
                bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
               tomatoButton.startTimer();
            }
        });
        tomatoButton.setOnTomotoButtonOnclick(new TomatoButton.OnTomotoButtonOnclick() {
            @Override
            public void onClick() {

                if(!tomatoButton.isClick())
                { if(frameLayout.indexOfChild(tomato)==-1){
                    imageView.setVisibility(View.GONE);
                    tomatoButton.setClick(!tomatoButton.isClick());
                    showTomato.startAnim();
                    showTomato.setVisibility(View.VISIBLE);}
                }
                else if(frameLayout.indexOfChild(tomato)!=-1) {
   new TomatoCalcel(TomatoActivity.this,tomatoButton).setOnDialogClick(new TomatoCalcel.OnDialogClick() {
       @Override
       public void onClick() {
           tomatoButton.setClick(!tomatoButton.isClick());
           if(!tomatoButton.isOk())
           {
               Calendar calendar=Calendar.getInstance();
               SharedPreferences reader=TomatoActivity.this.getSharedPreferences("tomato", Context.MODE_PRIVATE);
               int all=reader.getInt("allBad",0);
               int todayTomatoNum= reader.getInt(calendar.get(Calendar.YEAR)+""+(calendar.get(Calendar.MONTH)+1)+""+calendar.get(Calendar.DAY_OF_MONTH)+"bad",0);
               SharedPreferences.Editor    editor=TomatoActivity.this.getSharedPreferences("tomato",Context.MODE_PRIVATE).edit();
               editor.putInt("allBad",all+1);
               editor.putInt(calendar.get(Calendar.YEAR)+""+(calendar.get(Calendar.MONTH)+1)+""+calendar.get(Calendar.DAY_OF_MONTH)+"bad",todayTomatoNum+1);
               editor.apply();
           }
           try {
               unbindService(mConnection);
           }
           catch (IllegalArgumentException ex)
           {

           }

           showTomato.cancel();
          frameLayout.removeView(tomato);
         //  frameLayout.requestLayout();
           tomatoButton.end();
           imageView.setVisibility(View.VISIBLE);
       }
   }).show();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        frameLayout.removeView(tomato);
       // Toast.makeText(this,"stop",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        tomato=new Tomato(TomatoActivity.this);
        frameLayout.addView(tomato,params);

        tomato.setVisibility(View.VISIBLE);
        //Toast.makeText(this,"restart",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        frameLayout.removeView(tomato);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        tomato=new Tomato(TomatoActivity.this);
        frameLayout.addView(tomato,params);

        tomato.setVisibility(View.VISIBLE);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        if(frameLayout.indexOfChild(tomato)!=-1&&!tomatoButton.isOk())
        {
            new TomatoCalcel(TomatoActivity.this,tomatoButton).setOnDialogClick(new TomatoCalcel.OnDialogClick() {
                @Override
                public void onClick() {
                    tomatoButton.setClick(!tomatoButton.isClick());
                    if(!tomatoButton.isOk())
                    {
                        Calendar calendar=Calendar.getInstance();
                        SharedPreferences reader=TomatoActivity.this.getSharedPreferences("tomato", Context.MODE_PRIVATE);
                        int all=reader.getInt("allBad",0);
                        int todayTomatoNum= reader.getInt(calendar.get(Calendar.YEAR)+""+(calendar.get(Calendar.MONTH)+1)+""+calendar.get(Calendar.DAY_OF_MONTH)+"bad",0);
                        SharedPreferences.Editor    editor=TomatoActivity.this.getSharedPreferences("tomato",Context.MODE_PRIVATE).edit();
                        editor.putInt("allBad",all+1);
                        editor.putInt(calendar.get(Calendar.YEAR)+""+(calendar.get(Calendar.MONTH)+1)+""+calendar.get(Calendar.DAY_OF_MONTH)+"bad",todayTomatoNum+1);
                        editor.apply();
                    }

                    showTomato.cancel();
                    frameLayout.removeView(tomato);
                    //  frameLayout.requestLayout();
                    tomatoButton.end();
                    if(mBound) {
                        unbindService(mConnection);
                        mBound=false;
                    }
                    imageView.setVisibility(View.VISIBLE);
                    finish();
                }
            }).show();
        }
        else
        super.onBackPressed();
    }
    private ServiceConnection mConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            TomatoService.TomatoBinder binder= (TomatoService.TomatoBinder) iBinder;
            service=binder.getLocalService();
            mBound=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    public void setNotificationText(String s)
    {
        if(service!=null)
        service.setTextView(s);
    }

    public ServiceConnection getmConnection() {
        return mConnection;
    }
}
