package com.example.rf.tutioncounter.presenter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.rf.tutioncounter.interfaces.saveTutorData;
import com.example.rf.tutioncounter.interfaces.saveTutorProfileSuccessfully;
import com.example.rf.tutioncounter.model.TutorData;

/**
 * Created by RF on 7/18/2017.
 */

public class tutorProfileMakerPresenter {
    saveTutorProfileSuccessfully profSave;
    TutorData tutorData;
    Context context;
    String Name,Age,inst,sex,pass,rid;
    private SQLiteDatabase sqLiteDatabase;
    public tutorProfileMakerPresenter(Context context, saveTutorProfileSuccessfully profSave, String tableName, String Age, String pass, String inst, String Sex,String rid) {
        this.context=context;
        this.profSave=profSave;
        this.Name=tableName;
        this.Age=Age;
        this.inst=inst;
        this.sex=Sex;
        this.pass=pass;
        this.rid=rid;
    }
    public void loadAndSave(){
        tutorData=new TutorData(context, new saveTutorData() {
            @Override
            public void saveTutorData() {
                profSave.letsAddAstudent();
            }
        });
        sqLiteDatabase=tutorData.getWritableDatabase();
        tutorData.createTable(sqLiteDatabase);
        tutorData.insertUser(Name,pass,Age,inst,sex,rid,sqLiteDatabase);
    }

}
