package com.example.rf.tutioncounter.model;

/**
 * Created by RF on 7/19/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.rf.tutioncounter.DateListActivity;
import com.example.rf.tutioncounter.interfaces.saveStudentInfo;
import com.example.rf.tutioncounter.interfaces.saveTutorData;
import com.example.rf.tutioncounter.interfaces.studentCompleteEdition;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class studentsInfo extends SQLiteOpenHelper {
    saveStudentInfo save;
    studentCompleteEdition compl;
    private static final String DATABASE_NAME = "StudentsInfo";
    private static int DATABASE_VERSION = 1;
    Context context;
    private static String TABLE_NAME;
    private static final String UID = "_id";
    private static final String NAME = "Name";
    private static final String DAYS = "Days";
    private static final String SEX = "Sex";
    private static final String SUBJECT = "subject";
    private static final String CLASS = "Class";
    private static final String SID = "SID";
    private static final String DATES = "Dates";
    private static final String COUNT = "Count";
    private static final String MCOUNT = "MCount";
    private static final String TEST = "Test";
    //private static final String CREATE_TABLE_QUERY="CREATE TABLE "+TABLE_NAME+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255), "+PASSWORD+" VARCHAR(255));";
    //private static final String DROP_TABLE="DROP TABLE IF EXISTS " + TABLE_NAME;
    public static SQLiteDatabase sqLiteDatabase;

    public studentsInfo(Context context, String tableName, saveStudentInfo save) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        this.save = save;
        TABLE_NAME = tableName;
        //Toast.makeText(context,"DATABASE constructor 1 called",Toast.LENGTH_LONG).show();
        if (save != null)
            save.saveStudentData();
    }

    public studentsInfo(Context context, saveStudentInfo save) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        this.save = save;
        //Toast.makeText(context,"DATABASE constructor 2 called",Toast.LENGTH_LONG).show();
        if (save != null)
            save.saveStudentData();
    }
    public studentsInfo(studentCompleteEdition compl, Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        this.compl = compl;
        //Toast.makeText(context,"DATABASE constructor 2 called",Toast.LENGTH_LONG).show();
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
        onCreate(db);
        //Toast.makeText(context,"DATABASE onUPGRADE called",Toast.LENGTH_LONG).show();
    }

    public boolean isTableExists(SQLiteDatabase db, String tableName) {
        if (db == null || !db.isOpen()) {
            return false;
        }
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[]{"table", tableName});
        if (!cursor.moveToFirst()) {
            cursor.close();
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }

    public void insertUser(String TABLE_name, String name, String days, String subject, String Class, String Sex, String sid,String mCount, SQLiteDatabase db) {
        final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_name + "("
                + UID + " INTEGER PRIMARY KEY," + NAME + " TEXT,"
                + DAYS + " TEXT," + SUBJECT + " TEXT," + SEX + " TEXT," + CLASS + " TEXT," + SID + " TEXT," + DATES + " TEXT,"+MCOUNT + " TEXT,"+ COUNT + " TEXT,"+TEST + " TEXT"+")";
        db.execSQL(CREATE_TABLE_QUERY);
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(DAYS, days);
        contentValues.put(SUBJECT, subject);
        contentValues.put(CLASS, Class);
        contentValues.put(SEX, Sex);
        contentValues.put(SID, sid);
        contentValues.put(DATES,"");
        contentValues.put(MCOUNT, mCount);
        contentValues.put(TEST, "infinity");
        contentValues.put(COUNT, "0");
        db.insert(TABLE_name, null, contentValues);
        //Toast.makeText(context, mCount, Toast.LENGTH_LONG).show();
    }

    public ArrayList<String[]> selectAllData(SQLiteDatabase db, String Table_name) {
        String[] columns = {NAME, SEX, SUBJECT, DAYS, CLASS, COUNT,DATES,SID,MCOUNT};
        Cursor cursor = db.query(Table_name, columns, null, null, null, null, null);
        ArrayList<String[]> data = new ArrayList<>();
        while (cursor.moveToNext()) {
            String temp[] = new String[9];
            String name = cursor.getString(cursor.getColumnIndex(NAME));
            String sex = cursor.getString(cursor.getColumnIndex(SEX));
            String subject = cursor.getString(cursor.getColumnIndex(SUBJECT));
            String days = cursor.getString(cursor.getColumnIndex(DAYS));
            String Class = cursor.getString(cursor.getColumnIndex(CLASS));
            String count = cursor.getString(cursor.getColumnIndex(COUNT));
            String dates = cursor.getString(cursor.getColumnIndex(DATES));
            String sid = cursor.getString(cursor.getColumnIndex(SID));
            String mcount = cursor.getString(cursor.getColumnIndex(MCOUNT));
            temp[0] = name;
            temp[1] = sex;
            temp[2] = subject;
            temp[3] = days;
            temp[4] = Class;
            temp[5] = count;
            temp[6]=dates;
            temp[7]=sid;
            temp[8]=mcount;
            data.add(temp);
        }
        cursor.close();
        return data;

    }

    public ArrayList<String[]> selectDays(SQLiteDatabase db, String Table_name) {
        String[] columns = {NAME, DAYS,SID,TEST};
        Cursor cursor = db.query(Table_name, columns, null, null, null, null, null);
        ArrayList<String[]> data = new ArrayList<>();
        while (cursor.moveToNext()) {
            String[] Temp = new String[4];
            Temp[3] = cursor.getString(cursor.getColumnIndex(TEST));
            Temp[2] = cursor.getString(cursor.getColumnIndex(NAME));
            Temp[0] = cursor.getString(cursor.getColumnIndex(DAYS));
            Temp[1] = cursor.getString(cursor.getColumnIndex(SID));
            data.add(Temp);
        }
        cursor.close();
        return data;

    }

    public ArrayList<String> selectData(SQLiteDatabase db,String sid,String tableName) {
        String[] columns = {NAME,SEX, SUBJECT,CLASS, DAYS,COUNT,DATES,SID,MCOUNT};
        ArrayList<String> data = new ArrayList<>();
        String[] selectionArgs = {sid};
        Cursor cursor = db.query(tableName, columns, SID + " =?", selectionArgs, null, null, null, null);
        while (cursor.moveToNext()) {
            data.add(cursor.getString(cursor.getColumnIndex(NAME)));
            data.add(cursor.getString(cursor.getColumnIndex(SEX)));
            data.add(cursor.getString(cursor.getColumnIndex(SUBJECT)));
            data.add(cursor.getString(cursor.getColumnIndex(CLASS)));
            data.add(cursor.getString(cursor.getColumnIndex(DAYS)));
            data.add(cursor.getString(cursor.getColumnIndex(COUNT)));
            data.add(cursor.getString(cursor.getColumnIndex(DATES)));
            data.add(cursor.getString(cursor.getColumnIndex(SID)));
            data.add(cursor.getString(cursor.getColumnIndex(MCOUNT)));
        }
        cursor.close();
        return data;
    }

    public void updateCount(SQLiteDatabase db, String tableName, String sid) {
        String[] columns = {COUNT};
        String[] selectionArgs = {sid};
        String oldCount = null, newCount;
        Cursor cursor = db.query(tableName, columns, SID + " =?", selectionArgs, null, null, null, null);
        while (cursor.moveToNext()) {
            oldCount = (cursor.getString(cursor.getColumnIndex(COUNT)));
        }
        int old = Integer.parseInt(oldCount);
        newCount =Integer.toString(++old);
        ContentValues value = new ContentValues();
        value.put(COUNT, newCount);
        String[] whereArgs = {sid};
        db.update(tableName, value, SID + " =?", whereArgs);
        cursor.close();
    }
    public String updateDates(SQLiteDatabase db, String tableName, String sid, String date) {
        String[] columns = {DATES};
        String[] selectionArgs = {sid};
        String oldDates = null;
        Cursor cursor = db.query(tableName, columns, SID + " =?", selectionArgs, null, null, null, null);
        while (cursor.moveToNext()) {
            oldDates = (cursor.getString(cursor.getColumnIndex(DATES)));
        }
        ContentValues value = new ContentValues();
        value.put(DATES, oldDates+date+";");
        String[] whereArgs = {sid};
        db.update(tableName, value, SID + " =?", whereArgs);
        cursor.close();
        return (oldDates+date+";");
    }
    public void updateDay(SQLiteDatabase db, String tableName, String sid) {
        String[] columns = {DAYS};
        String[] selectionArgs = {sid};
        String oldDays = null;
        Cursor cursor = db.query(tableName, columns, SID + " =?", selectionArgs, null, null, null, null);
        while (cursor.moveToNext()) {
            oldDays = (cursor.getString(cursor.getColumnIndex(DAYS)));
        }
        ContentValues value = new ContentValues();
        value.put(DAYS, oldDays.substring(0,oldDays.length()-1)+"C");
        String[] whereArgs = {sid};
        db.update(tableName, value, SID + " =?", whereArgs);
        cursor.close();
    }
    public void updateTEST(SQLiteDatabase db, String tableName, String sid,String check) {
        String[] columns = {TEST};
        String[] selectionArgs = {sid};
        ContentValues value = new ContentValues();
        value.put(TEST, check);
        String[] whereArgs = {sid};
        db.update(tableName, value, SID + " =?", whereArgs);
    }
    public void updateall(SQLiteDatabase db,String tableName, String name, String days, String subject, String Class, String Sex, String sid,String mCount){
        String[] columns = {NAME, SEX, SUBJECT, DAYS, CLASS,MCOUNT};
        String[] whereArgs = {sid};
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(SEX, Sex);
        contentValues.put(SUBJECT, subject);
        contentValues.put(DAYS, days);
        contentValues.put(CLASS, Class);
        contentValues.put(MCOUNT, mCount);
        //contentValues.put(CHECK, " ");
        db.update(tableName, contentValues, SID + " =?", whereArgs);
        if (compl != null)
            compl.backtoProfile();

    }
    public int deleteData(SQLiteDatabase db, String sid,String tableName) {
        String[] whereArgs = {sid};
        int count = db.delete(tableName, SID + " =?", whereArgs);
        return count;
    }
    public String deleteDate(SQLiteDatabase db, String sid,String tableName,String Date) {
        String[] columns = {COUNT};
        String[] selectionArgs = {sid};
        String oldCount = null, newCount;
        Cursor cursor = db.query(tableName, columns, SID + " =?", selectionArgs, null, null, null, null);
        while (cursor.moveToNext()) {
            oldCount = (cursor.getString(cursor.getColumnIndex(COUNT)));
        }
        int old = Integer.parseInt(oldCount);
        newCount =Integer.toString(--old);
        ContentValues value = new ContentValues();
        value.put(COUNT, newCount);
        String[] whereArgs = {sid};
        db.update(tableName, value, SID + " =?", whereArgs);
        String[] columns1={DATES};
        String oldDate = null;
        Cursor cursor1 = db.query(tableName, columns1, SID + " =?", selectionArgs, null, null, null, null);
        while (cursor1.moveToNext()) {
            oldDate = (cursor1.getString(cursor1.getColumnIndex(DATES)));
        }
        int i=oldDate.lastIndexOf(Date);
        StringBuffer temp=new StringBuffer(oldDate);
        String newDate=temp.replace(i,i+Date.length(),"").toString();
        ContentValues value1 = new ContentValues();
        value1.put(DATES, newDate);
        db.update(tableName, value1, SID + " =?", whereArgs);
        cursor.close();
        return newDate;
    }
    public void changeTableName(String original, String latest, SQLiteDatabase db) {
        final String RENAME_TABLE_QUERY = "ALTER TABLE " + original + " RENAME TO " + latest + ";";
        db.execSQL(RENAME_TABLE_QUERY);
    }
    public void deleteTable(SQLiteDatabase db,String tableName)
    {
        db.execSQL("DROP TABLE IF EXISTS "+tableName);
    }
    public boolean getProfilesCount(SQLiteDatabase db,String tableName) {
        long cnt  = DatabaseUtils.queryNumEntries(db, tableName);
        if(cnt<1)
            return false;
        return true;
    }

}

