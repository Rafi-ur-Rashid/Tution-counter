package com.example.rf.tutioncounter.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.rf.tutioncounter.interfaces.saveTutorData;
import com.example.rf.tutioncounter.interfaces.tutorCompleteEdition;

import java.util.ArrayList;

public class TutorData extends SQLiteOpenHelper {
    saveTutorData save;
    tutorCompleteEdition compl;
    private static final String DATABASE_NAME = "TutorData";
    private static final int DATABASE_VERSION = 1;
    Context context;
    private static final String TABLE_NAME = "TUTOR";
    private static final String UID = "_id";
    private static final String NAME = "Name";
    private static final String PASSWORD = "PassWord";
    private static final String AGE = "Age";
    private static final String INST = "Inst";
    private static final String SEX = "Sex";
    private static final String No_STUDENT = "Student_no";
    private static final String RID = "rid";
    //private static final String CREATE_TABLE_QUERY="CREATE TABLE "+TABLE_NAME+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255), "+PASSWORD+" VARCHAR(255));";
    private static final String CREATE_TABLE_QUERY2 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
            + UID + " INTEGER PRIMARY KEY," + NAME + " TEXT,"
            + PASSWORD + " TEXT," + AGE + " TEXT," + INST + " TEXT," + SEX + " TEXT," + No_STUDENT + " TEXT," + RID + " INTEGER" +")";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static SQLiteDatabase sqLiteDatabase;

    public TutorData(Context context, saveTutorData save) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        this.save = save;
        //Toast.makeText(context,"DATABASE constructor called",Toast.LENGTH_LONG).show();
        if (save != null)
            save.saveTutorData();
    }
    public TutorData(tutorCompleteEdition compl,Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        this.compl = compl;
        //Toast.makeText(context,"DATABASE constructor called",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            sqLiteDatabase = db;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Toast.makeText(context,"DATABASE onCreate called",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TABLE);
            onCreate(db);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Toast.makeText(context,"DATABASE onUPGRADE called",Toast.LENGTH_LONG).show();
    }

    public void createTable(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY2);
    }

    public boolean isTableExists(SQLiteDatabase db) {
        if (db == null || !db.isOpen())
        {
            return false;
        }
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", TABLE_NAME});
        if (!cursor.moveToFirst())
        {
            cursor.close();
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }

    public void insertUser(String name, String password, String Age, String Inst, String Sex, String rid, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(PASSWORD, password);
        contentValues.put(AGE, Age);
        contentValues.put(INST, Inst);
        contentValues.put(SEX, Sex);
        contentValues.put(No_STUDENT, 0);
        contentValues.put(RID, rid);
        long id = db.insert(TABLE_NAME, null, contentValues);
        //Toast.makeText(context,"Inserted "+id+" th Name: "+name+" & password: "+password,Toast.LENGTH_LONG).show();
    }

    public ArrayList<String[]> selectAllData(SQLiteDatabase db) {
        String[] columns = {NAME, SEX, No_STUDENT,RID};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        ArrayList<String[]> data = new ArrayList<>();
        while (cursor.moveToNext()) {
            String temp[] = new String[4];
            String name = cursor.getString(cursor.getColumnIndex(NAME));
            String sex = cursor.getString(cursor.getColumnIndex(SEX));
            int student_no = cursor.getInt(cursor.getColumnIndex(No_STUDENT));
            String rid = cursor.getString(cursor.getColumnIndex(RID));
            temp[0] = name;
            temp[1] = sex;
            temp[2] = "" + student_no;
            temp[3]=rid;
            data.add(temp);
        }
        cursor.close();
        return data;

    }

    public ArrayList<String[]> selectRID(SQLiteDatabase db) {
        String[] columns = {NAME,RID};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        ArrayList<String[]>rid=new ArrayList<>();
        while (cursor.moveToNext()) {
            String Temp[]=new String[2];
            Temp[1] = cursor.getString(cursor.getColumnIndex(NAME));
            Temp[0] = cursor.getString(cursor.getColumnIndex(RID));
            rid.add(Temp);
        }
        cursor.close();
        return rid;

    }
    //    public ArrayList<String> selectData(SQLiteDatabase db,String name){
//        String[]  columns={SEX,INST,AGE,No_STUDENT};
//        ArrayList<String> data=new ArrayList<>();
//        String[] selectionArgs={name};
//        Cursor cursor=db.query(TABLE_NAME,columns,NAME+" =?",selectionArgs,null,null,null,null);
//        while (cursor.moveToNext()){
//            data.add(cursor.getString(cursor.getColumnIndex(SEX)));
//            data.add(cursor.getString(cursor.getColumnIndex(INST)));
//            data.add(cursor.getString(cursor.getColumnIndex(AGE)));
//            data.add(cursor.getString(cursor.getColumnIndex(No_STUDENT)));
//        }
//        return data;
//    }
    public ArrayList<String> SelectData(SQLiteDatabase db, String rid) {
        String[] columns = {NAME, SEX, INST, AGE, No_STUDENT, RID,PASSWORD};
        ArrayList<String> data = new ArrayList<>();
        String[] selectionArgs = {rid};
        Cursor cursor = db.query(TABLE_NAME, columns, RID + " =?", selectionArgs, null, null, null, null);
        while (cursor.moveToNext()) {
            data.add(cursor.getString(cursor.getColumnIndex(NAME)));
            data.add(cursor.getString(cursor.getColumnIndex(SEX)));
            data.add(cursor.getString(cursor.getColumnIndex(INST)));
            data.add(cursor.getString(cursor.getColumnIndex(AGE)));
            data.add(cursor.getString(cursor.getColumnIndex(No_STUDENT)));
            data.add(cursor.getString(cursor.getColumnIndex(RID)));
            data.add(cursor.getString(cursor.getColumnIndex(PASSWORD)));

        }
        cursor.close();
        return data;
    }

    public int selectData(SQLiteDatabase db, String rid) {
        String[] columns = {No_STUDENT};
        String[] selectionArgs = {rid};
        int data = 0;
        Cursor cursor = db.query(TABLE_NAME, columns, RID + " =?", selectionArgs, null, null, null, null);
        while (cursor.moveToNext()) {
            data = cursor.getInt(cursor.getColumnIndex(No_STUDENT));
        }
        cursor.close();
        return data;
    }
    public void updateData(SQLiteDatabase db, String rid,boolean increase) {
        int oldStd_no = selectData(db, rid);
        ContentValues value = new ContentValues();
        if(increase)
            value.put(No_STUDENT, oldStd_no + 1);
        else
            value.put(No_STUDENT, oldStd_no - 1);
        String[] whereArgs = {rid};
        db.update(TABLE_NAME, value, RID + " =?", whereArgs);
    }
    public void updateAll(SQLiteDatabase db, String rid,String Name,String Age,String inst,String pass,String sex) {
        ContentValues value = new ContentValues();
        value.put(NAME,Name);
        value.put(AGE,Age);
        value.put(SEX,sex);
        value.put(PASSWORD,pass);
        value.put(INST,inst);
        String[] whereArgs = {rid};
        db.update(TABLE_NAME, value, RID + " =?", whereArgs);
        if (compl != null)
            compl.backtoProfile();

    }

    public int deleteData(SQLiteDatabase db, String rid) {
        String[] whereArgs = {rid};
        int count = db.delete(TABLE_NAME, RID + " =?", whereArgs);
        return count;
    }
    public boolean getProfilesCount(SQLiteDatabase db) {
        long cnt  = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        if(cnt<1)
            return false;
        return true;
    }

}
