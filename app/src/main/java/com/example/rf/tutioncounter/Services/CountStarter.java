package com.example.rf.tutioncounter.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.rf.tutioncounter.CONSTANT_VALUES;
import com.example.rf.tutioncounter.R;
import com.example.rf.tutioncounter.presenter.countServicePresenter;
import com.example.rf.tutioncounter.utilityMethods;

import java.util.Calendar;


public class CountStarter extends Service{
    boolean mgen;
    int i;
    countServicePresenter cSP;
    class myServiceBinder extends Binder {
        public CountStarter getService(){
            return CountStarter.this;
        }
    }
    IBinder myBind=new myServiceBinder();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBind;
    }

    @Override
    public void onDestroy() {
        Log.i("MyService","The Service thread stops: "+Thread.currentThread().getId());
        super.onDestroy();
    }
    @Override
    public int onStartCommand(Intent in, int flags, int startId) {
        Toast.makeText(this,R.string.counting_started,Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mgen=true;
                while(mgen) {
                    int[] calendar=new int[8];
                    utilityMethods.calendarDetails(calendar);
                    int day=calendar[0];
                    int hour=calendar[1];
                    int minute=calendar[2];
                    int second=calendar[3];
                    int Mday=calendar[4];
                    int month=calendar[5]+1;
                    int year=calendar[6];
                    int Wday=calendar[7];
                    String wDay=""+ CONSTANT_VALUES.DAYS[Wday-1][1]+"বার";
                    String date=Mday+" / "+month+" / "+year+"%"+wDay;
                    cSP=new countServicePresenter(CountStarter.this,day,hour,minute,second,date,Mday,month,year);
                    cSP.loadData();
                    try {
                        //Log.i("MyService","The Service thread: "+ ++i);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        return START_STICKY;
    }
    @Override
    public void onTaskRemoved(Intent rootIntent){
        super.onTaskRemoved(rootIntent);
        Intent restartServiceTask = new Intent(getApplicationContext(),CountStarter.class);
        startService(restartServiceTask);
    }

}
