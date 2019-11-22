package com.example.rf.tutioncounter.presenter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.rf.tutioncounter.model.TutorData;
import com.example.rf.tutioncounter.model.studentsInfo;

/**
 * Created by RF on 7/21/2017.
 */

public class DataEmptyCheckPresenter {
    Context context;
    TutorData tutorData;
    studentsInfo studentInfo;
    SQLiteDatabase sqLiteDatabase1,sqLiteDatabase2;
    public DataEmptyCheckPresenter(Context context) {
        this.context=context;
        tutorData=new TutorData(context,null);
        studentInfo=new studentsInfo(context,null);
        sqLiteDatabase2=studentInfo.getWritableDatabase();
        sqLiteDatabase1=tutorData.getWritableDatabase();

    }
    public boolean chekckEmptiness(){
        if (tutorData.isTableExists(sqLiteDatabase1)) {
            if (tutorData.getProfilesCount(sqLiteDatabase1))
                    return true;
        }
        return false;
    }
    public boolean chekckEmptiness1(String tableName){
        return (studentInfo.getProfilesCount(sqLiteDatabase2,tableName));
    }
    public boolean chekckEmptiness2(){
        return (tutorData.getProfilesCount(sqLiteDatabase1));
    }
}
