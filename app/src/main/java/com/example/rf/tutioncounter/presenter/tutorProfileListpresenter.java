package com.example.rf.tutioncounter.presenter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.rf.tutioncounter.interfaces.loadTutorDetails;
import com.example.rf.tutioncounter.model.TutorData;
import com.example.rf.tutioncounter.model.studentsInfo;

import java.util.ArrayList;

public class tutorProfileListpresenter {
    Context context;
    TutorData tutorData;
    studentsInfo studentData;
    ArrayList<String> data;
    loadTutorDetails load;
    SQLiteDatabase sqLiteDatabase1, sqLiteDatabase2;

    public tutorProfileListpresenter(Context context, loadTutorDetails load) {
        this.context = context;
        this.load = load;
        studentData = new studentsInfo(context, null);
        tutorData = new TutorData(context, null);
        sqLiteDatabase1 = studentData.getWritableDatabase();
        sqLiteDatabase2 = tutorData.getWritableDatabase();
    }

    public void loadData(String rid) {
        data = tutorData.SelectData(sqLiteDatabase2,rid);
        load.showTutorProfile(data);
    }

    public void deleteData(String rid) {
        tutorData.deleteData(sqLiteDatabase2, rid);
        studentData.deleteTable(sqLiteDatabase1,rid);
    }
}
