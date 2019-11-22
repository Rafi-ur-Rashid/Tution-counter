package com.example.rf.tutioncounter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rf.tutioncounter.interfaces.saveEditedData;
import com.example.rf.tutioncounter.presenter.DataEmptyCheckPresenter;
import com.example.rf.tutioncounter.presenter.studentEditPresenter;

import java.util.ArrayList;

public class editStudentInfoActivity extends AppCompatActivity implements saveEditedData {
    EditText Name, Class, subject;
    CheckBox male, female, days[], startCount;
    Bundle bundle;
    Spinner spinner;
    TextView spinnerText;
    DataEmptyCheckPresenter tuttut;
    ViewGroup startCountContainer;
    ArrayAdapter<CharSequence> dayNumber;
    studentEditPresenter presenter;
    ArrayList<String>data;
    int check = 0;
    int[] dayIndex;
    boolean daysAttend = false;
    String tableName, name, Cls = "N/A", sub = "N/A", sex = "N/A", Days = "",sid,count,tutor,mCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bundle = getIntent().getExtras();
        sid = bundle.getString("sid");
        tableName = bundle.getString("rid");
        tutor=bundle.getString("tutor");
        presenter=new studentEditPresenter(this,this,sid,tableName);
        data=presenter.loadData();
        startCountContainer = (ViewGroup) findViewById(R.id.startCountContainer);
        tuttut = new DataEmptyCheckPresenter(this);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinnerText = (TextView) findViewById(R.id.preHint);
        spinnerText.setText(data.get(8));
        dayNumber = ArrayAdapter.createFromResource(this, R.array.dayNumber, android.R.layout.simple_spinner_item);
        dayNumber.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dayNumber);
        dayNumber = ArrayAdapter.createFromResource(this, R.array.dayNumber, android.R.layout.simple_spinner_item);
        dayNumber.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dayNumber);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(++check>1)
                    spinnerText.setText(parent.getItemAtPosition(position) + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        days = new CheckBox[7];
        Name = (EditText) findViewById(R.id.stdName);
        Name.setText(data.get(0));
        Class = (EditText) findViewById(R.id.Class);
        if(!data.get(3).equals("N/A"))
            Class.setText(data.get(3));
        subject = (EditText) findViewById(R.id.sub);
        if(!data.get(2).equals("N/A"))
            subject.setText(data.get(2));
        sex=data.get(1);
        male = (CheckBox) findViewById(R.id.boy);
        female = (CheckBox) findViewById(R.id.girl);
        if(sex.equals("male"))
            male.setChecked(true);
        else if(sex.equals("female"))
            female.setChecked(true);
        Days=data.get(4);
        count=data.get(5);
        startCount = (CheckBox) findViewById(R.id.startCount);
        if(Days.charAt(Days.length()-1)=='C')
            startCount.setChecked(true);
        else
            startCount.setChecked(false);
        days[0] = (CheckBox) findViewById(R.id.sun);
        days[1] = (CheckBox) findViewById(R.id.mon);
        days[2] = (CheckBox) findViewById(R.id.tue);
        days[3] = (CheckBox) findViewById(R.id.wed);
        days[4] = (CheckBox) findViewById(R.id.thurs);
        days[5] = (CheckBox) findViewById(R.id.fri);
        days[6] = (CheckBox) findViewById(R.id.sat);
        if(Days.equals("N/Ac"))
            startCount.setEnabled(false);
        else{
            dayIndex=utilityMethods.DaysNumGenerate(Days.substring(0,Days.length()-1));
            for (int i=0;i<dayIndex.length;i++)
                days[dayIndex[i]-1].setChecked(true);
        }
        for (int i = 0; i < 7; i++) {
            days[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        startCount.setEnabled(true);
                        startCount.setChecked(true);
                        startCount.setText(R.string.countAsk);
                    }

                    if (!checkDaysAttend() || spinnerText.getText().toString().equals("০")) {
                        startCount.setChecked(false);
                        startCount.setEnabled(false);
                        startCount.setHint(R.string.countAsk);
                    }

                }
            });

        }
        spinnerText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (!checkDaysAttend() || spinnerText.getText().toString().equals("০")) {
                    startCount.setChecked(false);
                    startCount.setEnabled(false);
                    startCount.setHint(R.string.countAsk);
                }
                return true;
            }
        });
        male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    female.setChecked(false);
                }

            }
        });
        female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    male.setChecked(false);
                }
            }
        });
    }
    public boolean checkDaysAttend() {
        boolean daysAttend = false;
        for (int i = 0; i < 7; i++) {
            if (days[i].isChecked()) {
                daysAttend = true;
            }

        }
        return daysAttend;
    }
    public void onBackPressed() {
        if (tuttut.chekckEmptiness1(tableName)) {
            Intent intent = new Intent(this, studentInfoListActivity.class);
            intent.putExtra("tutor",tutor);
            intent.putExtra("rid",tableName);
            startActivity(intent);
        }
        finish();
        editStudentInfoActivity.super.onBackPressed();


    }
    public void DoneEditing(View view){
        Days="";
        mCount=spinnerText.getText().toString();
        for (int i = 0; i < 7; i++) {
            if (days[i].isChecked()) {
                Days +=CONSTANT_VALUES.DAYSNUM[i][1];
            }
        }
        if (Name.getText().toString().length() <= 0)
            Toast.makeText(this, R.string.Warning, Toast.LENGTH_LONG).show();
        else {
            name = Name.getText().toString();
            if (Class.getText().toString().length() > 0) Cls = Class.getText().toString();
            if (subject.getText().toString().length() > 0) sub = subject.getText().toString();
            if (male.isChecked())
                sex = "male";
            else if (female.isChecked())
                sex = "female";
            daysAttend = checkDaysAttend();
            if (!daysAttend)
                Days = "N/A";
            if(startCount.isChecked()) {
                Days += "C";
            }
            else
                Days+="c";
            Toast.makeText(this, count, Toast.LENGTH_LONG).show();
            //presenter = new studentInfoMakerPresenter(studentInfoMakerActivity.this, this, tableName, name, Cls, sub, Days, sex,mCount, constr);
            if(startCount.isChecked())
            {
                presenter.saveData(name,Days,sub,Cls,sex,mCount);
            }
            else
            {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.Alert)
                        .setMessage(R.string.CountDialoge)
                        .setNegativeButton(R.string.no,null)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                presenter.saveData(name,Days,sub,Cls,sex,mCount);
                            }
                        }).create().show();

            }
        }
    }

    @Override
    public void saveData() {
        Intent intent=new Intent(this,studentInfoDetailsActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("sex",sex);
        intent.putExtra("subject",sub);
        intent.putExtra("class",Cls);
        intent.putExtra("day",Days);
        intent.putExtra("mcount",mCount);
        intent.putExtra("cout",count);
        intent.putExtra("sid",sid);
        intent.putExtra("tutor",tutor);
        intent.putExtra("rid",tableName);
        startActivity(intent);
        finish();
    }

}
