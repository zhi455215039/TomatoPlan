package com.firstapp.steven.mishu.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.firstapp.steven.mishu.EditDay.ImportantActivity;
import com.firstapp.steven.mishu.R;
import com.firstapp.steven.mishu.UserIcon;
import com.firstapp.steven.mishu.data.ImportantDay;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.Calendar;

/**
 * Created by steven on 2016/8/25.
 */

public class ImportantNotificationService extends Service {
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
        remoteViews=new RemoteViews(getPackageName(), R.layout.notify_important);
Intent intent=new Intent(this,ImportantNotificationService.class);
intent.putExtra("stop",1);
        PendingIntent intent1=PendingIntent.getService(this,111,intent,0);
       // remoteViews.setPendingIntentTemplate(R.id.impor_notify_root,intent1);
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
    Intent intent1=new Intent(this,ImportantActivity.class);
    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent1);
}
        else {
    ImportantDay day = (ImportantDay) intent.getSerializableExtra("important");
    if (!day.getIcon_add().equals("")) {

        ImageLoader loader = ImageLoader.getInstance();
        loader.init(ImageLoaderConfiguration.createDefault(this));
        Bitmap bitmap = loader.loadImageSync(day.getIcon_add());
        remoteViews.setImageViewBitmap(R.id.impor_notify_icon, bitmap);
    } else {
        remoteViews.setImageViewResource(R.id.impor_notify_icon, UserIcon.photos[day.getPhoto_id()]);
    }
    remoteViews.setTextViewText(R.id.important_notify_name, day.getName());
    remoteViews.setTextViewText(R.id.important_notify_date, day.getDate().get(Calendar.YEAR) + "年" + (day.getDate().get(Calendar.MONTH) + 1) + "月" + day.getDate().get(Calendar.DAY_OF_MONTH) + "日");
    remoteViews.setTextViewText(R.id.impor_notify_day_num, "第" + day.getDayCount() + "天");
    remoteViews.setTextViewText(R.id.importantday_notify_num, day.getDay_num() + "");
    remoteViews.setTextViewText(R.id.importantday_notify_content, "后过" + (day.getYearCount()) + "周年纪念");
    builder.setContent(remoteViews);
    notification = builder.build();
    Notification notification = builder.build();
    startForeground(12345, notification);
}
        return START_STICKY;
    }
}
