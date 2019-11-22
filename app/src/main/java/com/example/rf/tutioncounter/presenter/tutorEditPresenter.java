package com.example.rf.tutioncounter.presenter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.rf.tutioncounter.interfaces.saveEditedData;
import com.example.rf.tutioncounter.interfaces.tutorCompleteEdition;
import com.example.rf.tutioncounter.model.TutorData;

import java.util.ArrayList;

/**
 * Created by RF on 9/12/2017.
 */

public class tutorEditPresenter {
    saveEditedData load;
    Context context;
    TutorData tutorData;
    SQLiteDatabase sqLiteDatabase1;
    ArrayList<String> data;
    public tutorEditPresenter(Context context, final saveEditedData load) {
        this.context = context;
        this.load=load;
        tutorData=new TutorData(new tutorCompleteEdition() {
            @Override
            public void backtoProfile() {
                load.saveData();
            }
        },context);
        sqLiteDatabase1=tutorData.getWritableDatabase();
    }
    public ArrayList<String> loadData(String rid){
       data=tutorData.SelectData(sqLiteDatabase1,rid);
        return data;
    }
    public void saveData(String rid,String Name,String Age,String inst,String pass,String sex){
        tutorData.updateAll(sqLiteDatabase1,rid,Name,Age,inst,pass,sex);
    }
}
