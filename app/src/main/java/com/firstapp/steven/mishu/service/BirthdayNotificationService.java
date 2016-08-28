package com.firstapp.steven.mishu.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.firstapp.steven.mishu.EditDay.BirthdayActivity;
import com.firstapp.steven.mishu.R;
import com.firstapp.steven.mishu.UserIcon;
import com.firstapp.steven.mishu.data.Birthday;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.Calendar;

/**
 * Created by steven on 2016/8/25.
 */

public class BirthdayNotificationService extends Service {
    NotificationManager manager;
    NotificationCompat.Builder builder;
    RemoteViews remoteViews;
    Notification notification;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        builder=new NotificationCompat.Builder(this);
        remoteViews=new RemoteViews(getPackageName(),R.layout.notify_birthday);
Intent intent=new Intent(this,BirthdayNotificationService.class);

intent.putExtra("stop",1);
        PendingIntent intent1=PendingIntent.getService(this,12331,intent,0);

        builder.setContentText("啦啦啦");
        builder.setSubText("asdasd");
        builder.setSmallIcon(R.drawable.photo2);
        builder.setAutoCancel(true);
        builder.setContentIntent(intent1);
        builder.setTicker("通知来啦");
        builder.setDefaults(Notification.DEFAULT_ALL);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent.getIntExtra("stop",0)!=0)
        {
            stopForeground(true);
            Intent intent1=new Intent(this,BirthdayActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);

        }
        else {
            Birthday birthday= (Birthday) intent.getSerializableExtra("birthday");
            remoteViews.setTextViewText(R.id.birthday_notify_name,birthday.getName());
            remoteViews.setTextViewText(R.id.birthday_notify_date,birthday.getDate().get(Calendar.YEAR)+"年"+(birthday.getDate().get(Calendar.MONTH)+1)+"月"+birthday.getDate().get(Calendar.DAY_OF_MONTH)+"日");
            remoteViews.setTextViewText(R.id.day_notify_num,birthday.getDay_num()+"");

            if(!birthday.getIcon_add().equals("")) {

                ImageLoader loader=ImageLoader.getInstance();
                loader.init(ImageLoaderConfiguration.createDefault(this));
              Bitmap bitmap=loader.loadImageSync(birthday.getIcon_add());
                remoteViews.setImageViewBitmap(R.id.birthday_notify_icon,bitmap);
            }
            else {
                remoteViews.setImageViewResource(R.id.birthday_notify_icon, UserIcon.photos[birthday.getPhoto_id()]);
            }
            builder.setContent(remoteViews);
            notification = builder.build();
            Notification notification=builder.build();
            startForeground(1234,notification);
        }



        return START_STICKY;
    }
}
