package com.example.rf.tutioncounter.presenter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.rf.tutioncounter.interfaces.loadStudentInfoDetails;
import com.example.rf.tutioncounter.interfaces.loadTutorDetails;
import com.example.rf.tutioncounter.model.TutorData;
import com.example.rf.tutioncounter.model.studentsInfo;

import java.util.ArrayList;

/**
 * Created by RF on 8/21/2017.
 */

public class studentsInfoListPresenter {
    Context context;
    studentsInfo studentData;
    TutorData tutorData;
    ArrayList<String> data;
    String tableName;
    loadStudentInfoDetails load;
    SQLiteDatabase sqLiteDatabase1,sqLiteDatabase2;

    public studentsInfoListPresenter(Context context,loadStudentInfoDetails load,String tableName) {
        this.context = context;
        this.load = load;
        this.tableName=tableName;
        studentData=new studentsInfo(context,null);
        tutorData=new TutorData(context,null);
        sqLiteDatabase1=studentData.getWritableDatabase();
        sqLiteDatabase2=tutorData.getWritableDatabase();
    }
    public void loadData(String sid){
        data=studentData.selectData(sqLiteDatabase1,sid,tableName);
        load.showStudentInfo(data);
    }
    public void deleteData(String sid){
        studentData.deleteData(sqLiteDatabase1,sid,tableName);
        tutorData.updateData(sqLiteDatabase2,tableName,false);
    }
    public void updateDays(String sid){
        studentData.updateDay(sqLiteDatabase1,tableName,sid);
    }
    public String deleteDate(String sid,String newDate){
        return studentData.deleteDate(sqLiteDatabase1,sid,tableName,newDate);
    }
    public String updateDate(String sid,String newDate){
        studentData.updateCount(sqLiteDatabase1,tableName,sid);
        return studentData.updateDates(sqLiteDatabase1,tableName,sid,newDate);
    }

}
