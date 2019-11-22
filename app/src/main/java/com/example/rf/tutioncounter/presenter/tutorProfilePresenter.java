package com.example.rf.tutioncounter.presenter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.rf.tutioncounter.model.TutorData;

import java.util.ArrayList;

/**
 * Created by RF on 7/19/2017.
 */

public class tutorProfilePresenter {
    Context context;
    TutorData tutorData;
    ArrayList<String[]> data;
    SQLiteDatabase sqLiteDatabase;
    public tutorProfilePresenter(Context context) {
        this.context=context;
    }
    public ArrayList<String[]>loadData(){
        tutorData=new TutorData(context,null);
        sqLiteDatabase=tutorData.getWritableDatabase();
        data=tutorData.selectAllData(sqLiteDatabase);
        return data;
    }
}
