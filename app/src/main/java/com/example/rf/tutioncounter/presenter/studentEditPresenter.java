package com.example.rf.tutioncounter.presenter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.rf.tutioncounter.interfaces.saveEditedData;
import com.example.rf.tutioncounter.interfaces.studentCompleteEdition;
import com.example.rf.tutioncounter.model.studentsInfo;

import java.util.ArrayList;

/**
 * Created by RF on 9/12/2017.
 */

public class studentEditPresenter {
    saveEditedData save;
    Context context;
    studentsInfo studentData;
    SQLiteDatabase sqLiteDatabase;
    ArrayList<String> data;
    String sid,rid;
    public studentEditPresenter(Context context, final saveEditedData save,String sid,String rid) {
        this.context = context;
        this.save=save;
        this.sid=sid;
        this.rid=rid;
        studentData=new studentsInfo(new studentCompleteEdition() {
            @Override
            public void backtoProfile() {
                save.saveData();
            }
        }, context);
        sqLiteDatabase=studentData.getWritableDatabase();
    }
    public ArrayList<String> loadData(){
        data=studentData.selectData(sqLiteDatabase,sid,rid);
        return data;
    }
    public void saveData(String name, String days, String subject, String Class, String Sex,String mCount){
        studentData.updateall(sqLiteDatabase,rid,name,days,subject,Class,Sex,sid,mCount);
    }
//    public void saveData(String rid,String Name,String cls,String subj,String days,String sex){
//        tutorData.updateAll(sqLiteDatabase1,rid,Name,Age,inst,pass,sex);
//    }
}
