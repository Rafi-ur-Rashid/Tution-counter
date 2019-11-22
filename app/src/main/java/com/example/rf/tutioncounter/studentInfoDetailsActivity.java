package com.example.rf.tutioncounter;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rf.tutioncounter.Adapter.dateListAdapter;
import com.example.rf.tutioncounter.presenter.DataEmptyCheckPresenter;
import com.example.rf.tutioncounter.presenter.studentsInfoListPresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.StringTokenizer;

public class studentInfoDetailsActivity extends AppCompatActivity{
    TextView Name, Cls, Subject, Days, Count, Date, mCount, t1, t2;
    ImageView photo, editCount;
    int[] dayNum;
    String name, cls, count, sex, days, subject, date, tutor, rid, sid, tempDays, mcount;
    StringTokenizer st, st1;
    Bundle bundle;
    Calendar myCalendar = Calendar.getInstance();
    DataEmptyCheckPresenter tuttut;
    studentsInfoListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tuttut = new DataEmptyCheckPresenter(this);
        Name = (TextView) findViewById(R.id.Name);
        Cls = (TextView) findViewById(R.id.Cls);
        Subject = (TextView) findViewById(R.id.Subject);
        Days = (TextView) findViewById(R.id.days);
        Count = (TextView) findViewById(R.id.Count);
        Date = (TextView) findViewById(R.id.addAcls);
        mCount = (TextView) findViewById(R.id.mCount);
        bundle = getIntent().getExtras();
        name = bundle.getString("name");
        sex = bundle.getString("sex");
        cls = bundle.getString("class");
        subject = bundle.getString("subject");
        count = bundle.getString("cout");
        days = bundle.getString("day");
        mcount = bundle.getString("mcount");
        tempDays = days;
        date = bundle.getString("dates");
        tutor = bundle.getString("tutor");
        rid = bundle.getString("rid");
        sid = bundle.getString("sid");
        photo = (ImageView) findViewById(R.id.photo);
        editCount = (ImageView) findViewById(R.id.editCount);
        Name.setText(this.getString(R.string.Name2) + ": " + name);
        Cls.setText(this.getString(R.string.Class) + ": " + cls);
        Subject.setText(this.getString(R.string.Subject) + ": " + subject);
        presenter = new studentsInfoListPresenter(this, null, rid);
        if (days.charAt(days.length() - 1) == 'C')
            Count.setText(this.getString(R.string.monthCount1) + " " + utilityMethods.E2BnumberConverter(count) + " " + this.getString(R.string.monthCount2));
        else {
            editCount.setVisibility(View.VISIBLE);
            editCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(studentInfoDetailsActivity.this)
                            .setTitle(R.string.Alert)
                            .setMessage(R.string.countDialoge)
                            .setNegativeButton(R.string.no, null)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (days.equals("N/Ac"))
                                        Toast.makeText(studentInfoDetailsActivity.this, "Have to select day first", Toast.LENGTH_SHORT).show();
                                    else {
                                        presenter.updateDays(sid);
                                        Intent intent = new Intent(studentInfoDetailsActivity.this, studentInfoDetailsActivity.class);
                                        intent.putExtra("name", name);
                                        intent.putExtra("sex", sex);
                                        intent.putExtra("subject", subject);
                                        intent.putExtra("class", cls);
                                        intent.putExtra("day", tempDays.substring(0, tempDays.length() - 1) + "C");
                                        intent.putExtra("cout", count);
                                        intent.putExtra("dates", date);
                                        intent.putExtra("sid", sid);
                                        intent.putExtra("tutor", tutor);
                                        intent.putExtra("rid", rid);
                                        intent.putExtra("mcount", mcount);
                                        startActivity(intent);
                                        finish();
                                        Toast.makeText(studentInfoDetailsActivity.this, "count started", Toast.LENGTH_SHORT).show();
                                        editCount.setVisibility(View.GONE);
                                    }
                                }
                            }).create().show();
                }
            });
            Count.setText(this.getString(R.string.monthCountNull));
            Count.setTextColor(getResources().getColor(R.color.colorAccent));
        }
        if (sex.equals("male"))
            photo.setImageResource(R.drawable.boy);
        else if (sex.equals("female"))
            photo.setImageResource(R.drawable.girl);
        else
            photo.setImageResource(R.drawable.people1);
        if (!days.equals("N/Ac")) {
            dayNum = utilityMethods.DaysNumGenerate(days.substring(0, days.length() - 1));
            days = "";
            for (int i = dayNum.length - 1; i >= 0; i--)
                days += CONSTANT_VALUES.DAYS[dayNum[i] - 1][1] + " ";
        }
        Days.setText(this.getString(R.string.days) + ": " + days);
        mCount.setText(this.getString(R.string.mcount1) + " " + mcount + " " + this.getString(R.string.mcount2));
//        dates = new ArrayList<>();
        if (date==null || date.length()<1) {
            Date.setText(this.getString(R.string.daysMonthNull));
            Date.setOnClickListener(new View.OnClickListener() {
                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabel();
                    }

                };
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    new DatePickerDialog(studentInfoDetailsActivity.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
        }
        else if (date != null) {
            Date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(studentInfoDetailsActivity.this,DateListActivity.class);
                    intent.putExtra("date",date);
                    intent.putExtra("name", name);
                    intent.putExtra("sex", sex);
                    intent.putExtra("subject", subject);
                    intent.putExtra("class", cls);
                    intent.putExtra("day", tempDays.substring(0, tempDays.length() - 1) + "C");
                    intent.putExtra("cout", count);
                    intent.putExtra("dates", date);
                    intent.putExtra("sid", sid);
                    intent.putExtra("tutor", tutor);
                    intent.putExtra("rid", rid);
                    intent.putExtra("mcount", mcount);
                    startActivity(intent);
                    finish();
                }
            });

        }
    }


    public void onBackPressed() {
        if (tuttut.chekckEmptiness()) {
            Intent intent = new Intent(this, studentInfoListActivity.class);
            intent.putExtra("tutor", tutor);
            intent.putExtra("rid", rid);
            startActivity(intent);
            finish();
        }
        finish();
        studentInfoDetailsActivity.super.onBackPressed();

    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String s=sdf.format(myCalendar.getTime());
        //Toast.makeText(this,sdf.format(),Toast.LENGTH_LONG).show();
        StringTokenizer st=new StringTokenizer(s,"/");
        int month=myCalendar.get(Calendar.MONTH)+1;
        int day=myCalendar.get(Calendar.DAY_OF_MONTH);
        int year=myCalendar.get(Calendar.YEAR);
        count = (Integer.parseInt(count) +1) + "";
        String wDay=""+ CONSTANT_VALUES.DAYS[myCalendar.get(Calendar.DAY_OF_WEEK)-1][1]+"বার";
        String dates=day+" / "+month+" / "+year+"%"+wDay;
        Toast.makeText(this,day+" / "+month+" / "+year+"%"+wDay,Toast.LENGTH_LONG).show();
        String date=presenter.updateDate(sid,dates);
        Intent intent = new Intent(studentInfoDetailsActivity.this, DateListActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("sex", sex);
        intent.putExtra("subject", subject);
        intent.putExtra("class", cls);
        intent.putExtra("day", tempDays.substring(0, tempDays.length() - 1) + "C");
        intent.putExtra("cout", count);
        intent.putExtra("dates", date);
        intent.putExtra("sid", sid);
        intent.putExtra("tutor", tutor);
        intent.putExtra("rid", rid);
        intent.putExtra("mcount", mcount);
        startActivity(intent);
        finish();
        //edittext.setText(sdf.format(myCalendar.getTime()));
    }

}
