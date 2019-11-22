package com.example.rf.tutioncounter.presenter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.rf.tutioncounter.model.TutorData;
import com.example.rf.tutioncounter.model.studentsInfo;

import java.util.ArrayList;

/**
 * Created by RF on 7/30/2017.
 */

public class studentInfoPresenter {
    Context context;
    String tutorName;
    studentsInfo studentData;
    ArrayList<String[]> data;
    SQLiteDatabase sqLiteDatabase;
    public studentInfoPresenter(Context context,String tutorName) {
        this.context=context;
        this.tutorName=tutorName;
    }
    public ArrayList<String[]>loadData(){
        studentData=new studentsInfo(context,null);
        sqLiteDatabase=studentData.getWritableDatabase();
        data=studentData.selectAllData(sqLiteDatabase,tutorName);
        return data;
    }
}
