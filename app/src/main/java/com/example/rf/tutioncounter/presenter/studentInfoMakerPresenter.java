package com.example.rf.tutioncounter.presenter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.rf.tutioncounter.interfaces.saveStudentInfo;
import com.example.rf.tutioncounter.interfaces.saveStudentInfoSuccessfully;
import com.example.rf.tutioncounter.interfaces.saveTutorData;
import com.example.rf.tutioncounter.interfaces.saveTutorProfileSuccessfully;
import com.example.rf.tutioncounter.model.TutorData;
import com.example.rf.tutioncounter.model.studentsInfo;

import java.util.Random;

/**
 * Created by RF on 7/20/2017.
 */

public class studentInfoMakerPresenter {
    saveStudentInfoSuccessfully profSave;
    studentsInfo studentInfo;
    Context context;
    boolean constr;
    TutorData tutorData;
    String TableName,name,Cls,Sub,sex,Days,sid,mCount;
    private SQLiteDatabase sqLiteDatabase;
    public studentInfoMakerPresenter(Context context, saveStudentInfoSuccessfully profSave, String tableName,String name ,String Cls, String Sub, String Days, String Sex,String mCount,boolean constr) {
        this.context=context;
        this.profSave=profSave;
        this.TableName=tableName;
        this.name=name;
        this.Cls=Cls;
        this.Sub=Sub;
        this.sex=Sex;
        this.Days=Days;
        this.mCount=mCount;
        this.constr=constr;
    }
    public void loadAndSave(String sid){
        if(constr) {
            studentInfo = new studentsInfo(context, TableName, new saveStudentInfo() {
                @Override
                public void saveStudentData() {
                    profSave.letsAddAnotherstudent();
                }
            });
        }
        else
        {
            studentInfo = new studentsInfo(context,new saveStudentInfo() {
                @Override
                public void saveStudentData() {
                    profSave.letsAddAnotherstudent();
                }
            });
        }
        sqLiteDatabase=studentInfo.getWritableDatabase();
        studentInfo.insertUser(TableName,name,Days,Sub,Cls,sex,sid,mCount,sqLiteDatabase);
        tutorData=new TutorData(context,null);
        sqLiteDatabase=tutorData.getWritableDatabase();
        tutorData.updateData(sqLiteDatabase,TableName,true);
    }

}
