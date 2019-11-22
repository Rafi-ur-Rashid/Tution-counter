package com.example.rf.tutioncounter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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

import com.example.rf.tutioncounter.interfaces.saveStudentInfoSuccessfully;
import com.example.rf.tutioncounter.presenter.studentInfoMakerPresenter;
import com.example.rf.tutioncounter.presenter.DataEmptyCheckPresenter;

import java.util.Random;

public class studentInfoMakerActivity extends AppCompatActivity implements saveStudentInfoSuccessfully {
    EditText Name, Class, subject;
    CheckBox male, female, days[], startCount;
    ImageView pp;
    Bundle bundle;
    Spinner spinner;
    TextView spinnerText;
    DataEmptyCheckPresenter tuttut;
    ViewGroup startCountContainer;
    boolean daysAttend = false, constr;
    ArrayAdapter<CharSequence> dayNumber;
    String Tutorname, tableName, name, Cls = "N/A", sub = "N/A", sex = "N/A", Days = "",sid,mCount;
    studentInfoMakerPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info_maker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        startCountContainer = (ViewGroup) findViewById(R.id.startCountContainer);
        tuttut = new DataEmptyCheckPresenter(this);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinnerText = (TextView) findViewById(R.id.preHint);
        dayNumber = ArrayAdapter.createFromResource(this, R.array.dayNumber, android.R.layout.simple_spinner_item);
        dayNumber.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dayNumber);
        spinnerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerText.setText("");
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerText.setText(parent.getItemAtPosition(position) + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        bundle = getIntent().getExtras();
        Tutorname = bundle.getString("tutor");
        tableName = bundle.getString("rid");
        constr = bundle.getBoolean("constr");
        days = new CheckBox[7];
        Name = (EditText) findViewById(R.id.stdName);
        Class = (EditText) findViewById(R.id.Class);
        subject = (EditText) findViewById(R.id.sub);
        male = (CheckBox) findViewById(R.id.boy);
        female = (CheckBox) findViewById(R.id.girl);
        startCount = (CheckBox) findViewById(R.id.startCount);
        startCount.setChecked(false);
        days[0] = (CheckBox) findViewById(R.id.sun);
        days[1] = (CheckBox) findViewById(R.id.mon);
        days[2] = (CheckBox) findViewById(R.id.tue);
        days[3] = (CheckBox) findViewById(R.id.wed);
        days[4] = (CheckBox) findViewById(R.id.thurs);
        days[5] = (CheckBox) findViewById(R.id.fri);
        days[6] = (CheckBox) findViewById(R.id.sat);
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
        pp = (ImageView) findViewById(R.id.pp);
        male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    female.setChecked(false);
                    pp.setImageResource(R.drawable.boy);
                }
                if(!male.isChecked() && !female.isChecked())
                    pp.setImageResource(R.drawable.people1);

            }
        });
        female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    male.setChecked(false);
                    pp.setImageResource(R.drawable.girl);
                }
                if(!male.isChecked() && !female.isChecked())
                    pp.setImageResource(R.drawable.people1);
            }
        });
    }

    public void saveStudentInfo(View view) {
        Days="";
        mCount=spinnerText.getText().toString();
        for (int i = 0; i < 7; i++) {
            if (days[i].isChecked()) {
                Days +=CONSTANT_VALUES.DAYSNUM[i][1];
            }
        }
        if (Name.getText().toString().length() <= 0)
            Toast.makeText(studentInfoMakerActivity.this, R.string.Warning, Toast.LENGTH_LONG).show();
        else {
            name = Name.getText().toString();
            Random rd = new Random();
            sid = name + rd.nextInt(1000);
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
            presenter = new studentInfoMakerPresenter(studentInfoMakerActivity.this, this, tableName, name, Cls, sub, Days, sex,mCount, constr);
            if(startCount.isChecked())
            {
                presenter.loadAndSave(sid);
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
                                presenter.loadAndSave(sid);
                            }
                        }).create().show();

            }
        }
    }

    public void onBackPressed() {
        if (tuttut.chekckEmptiness()) {
            Intent intent = new Intent(this, TutorProfilesListActivity.class);
            startActivity(intent);
        }
        finish();
        studentInfoMakerActivity.super.onBackPressed();


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
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void letsAddAnotherstudent() {
        final Intent intent = new Intent(studentInfoMakerActivity.this, studentInfoGatheredActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("sex", sex);
        intent.putExtra("class", Cls);
        intent.putExtra("subject", sub);
        intent.putExtra("days", Days);
        intent.putExtra("rid", tableName);
        intent.putExtra("tutor", Tutorname);
        intent.putExtra("sid",sid);
        intent.putExtra("mcount",mCount);
        startActivity(intent);
        finish();
    }
}
