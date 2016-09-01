package com.firstapp.steven;

import android.os.Handler;
import android.os.Message;

/**
 * Created by steven on 2016/8/30.
 */

public class Timer {
    private Handler handler;
    private Handler upDate;
    private Runnable runnable;
    private boolean stop=false;
public Timer(Handler hand)
{
    upDate=hand;
    handler=new Handler();
    runnable=new Runnable() {
        @Override
        public void run() {

            if(!stop){
                Message message=new Message();
                message.what=1;
                upDate.sendMessage(message);
                handler.postDelayed(runnable,1000);
            }
        }
    };
}
    public void start()
    {
        stop=false;
        handler.postDelayed(runnable,0);
    }
    public void stop()
    {
        stop=true;
    }


}
