package com.firstapp.steven.mishu.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.firstapp.steven.mishu.R;
import com.firstapp.steven.mishu.TomatoActivity;
import com.firstapp.steven.tomato.Tomato;

/**
 * Created by steven on 2016/8/31.
 */

public class TomatoService extends Service {
    private TomatoBinder binder=new TomatoBinder();
  private   NotificationManager manager;
  private   NotificationCompat.Builder builder;
  private   RemoteViews remoteViews;
   private Notification notification;
    private TextView textView;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        builder.setContent(remoteViews);
        notification = builder.build();
        Notification notification=builder.build();
        startForeground(9234,notification);
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        stopForeground(true);
        return super.onUnbind(intent);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        builder=new NotificationCompat.Builder(this);
        remoteViews=new RemoteViews(getPackageName(), R.layout.tomato_service);
        Intent intent=new Intent(this,TomatoActivity.class);


        PendingIntent intent1=PendingIntent.getActivity(this,49331,intent,0);

        builder.setContentText("啦啦啦");
        builder.setSubText("asdasd");
        builder.setSmallIcon(R.drawable.photo2);
        builder.setAutoCancel(true);
        builder.setContentIntent(intent1);
        builder.setTicker("通知来啦");
        //builder.setDefaults(Notification.DEFAULT_ALL);

    }
    public class  TomatoBinder extends Binder{
       public TomatoService getLocalService()
        {
            return TomatoService.this;
        }
    }
    public void setTextView(String s)
    {
        remoteViews.setTextViewText(R.id.tomato_service_textview,s);
        builder.setContent(remoteViews);

        notification = builder.build();
        Notification notification=builder.build();
        startForeground(9234,notification);
    }

}
