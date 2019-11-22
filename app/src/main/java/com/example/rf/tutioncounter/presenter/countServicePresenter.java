package com.example.rf.tutioncounter.presenter;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.animation.PathInterpolator;

import com.example.rf.tutioncounter.R;
import com.example.rf.tutioncounter.WelcomeActivity;
import com.example.rf.tutioncounter.model.TutorData;
import com.example.rf.tutioncounter.model.studentsInfo;
import com.example.rf.tutioncounter.utilityMethods;

import java.util.ArrayList;

import static android.content.Context.NOTIFICATION_SERVICE;

public class countServicePresenter {
    Context context;
    TutorData tutorData;
    studentsInfo studentInfo;
    ArrayList<String[]>data,data2;
    String name,Temp,check;
    int[] temp;
    int d,h,m,s,Mday,month,year;
    String date;
    SQLiteDatabase sqLiteDatabase;
    NotificationCompat.Builder notification;
    private static final int uniqueID = 45612;
    private SQLiteDatabase sqLiteDatabase2;
    public countServicePresenter(Context context,int d,int h,int m,int s,String date,int Mday,int month,int year) {
        this.context=context;
        this.d=d;
        this.h=h;
        this.m=m;
        this.s=s;
        this.date=date;
        this.Mday=Mday;
        this.month=month;
        this.year=year;
        notification = new NotificationCompat.Builder(context);
        notification.setAutoCancel(true);
        notification.setSmallIcon(R.mipmap.ic_launcher);
    }
    public void loadData(){
        tutorData=new TutorData(context,null);
        sqLiteDatabase=tutorData.getWritableDatabase();
        if (!tutorData.isTableExists(sqLiteDatabase))
            return;
        data=tutorData.selectRID(sqLiteDatabase);
        studentInfo = new studentsInfo(context,null);
        sqLiteDatabase2=studentInfo.getWritableDatabase();
        for (int i=0;i<data.size();i++)
        {
            if(!studentInfo.isTableExists(sqLiteDatabase2,data.get(i)[0]))
                return;
            data2=studentInfo.selectDays(sqLiteDatabase2,data.get(i)[0]);
            for (int j=0;j<data2.size();j++) {
                if(data2.get(j)[0].charAt(data2.get(j)[0].length()-1)!='C')
                    continue;
                Temp=data2.get(j)[0].substring(0,data2.get(j)[0].length()-1);
                temp = utilityMethods.DaysNumGenerate(Temp);
                check=data2.get(j)[3];
                for (int k=0;k<temp.length;k++)
                {
                    if(d==temp[k] ) {
                        if(check.equals(d+""))
                            continue;
                        studentInfo.updateTEST(sqLiteDatabase2,data.get(i)[0],data2.get(j)[1],d+"");
                        studentInfo.updateCount(sqLiteDatabase2,data.get(i)[0],data2.get(j)[1]);
                        studentInfo.updateDates(sqLiteDatabase2,data.get(i)[0],data2.get(j)[1],date);
                        notification.setWhen(System.currentTimeMillis());
                        notification.setContentTitle(data.get(i)[1]);
                        notification.setContentText(context.getString(R.string.Classtaken1) +" "+ data2.get(j)[2] +" "+context.getString(R.string.Classtaken2));
                        Intent intent = new Intent(context, WelcomeActivity.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        notification.setContentIntent(pendingIntent);
                        NotificationManager nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                        nm.notify(uniqueID, notification.build());
                        //Log.i("MyTest", data.get(i)[1] + " Did u take a class of " + data2.get(j)[2] + " today? "+date);
                    }

                }
            }
        }
        sqLiteDatabase.close();
        sqLiteDatabase2.close();

    }
}
