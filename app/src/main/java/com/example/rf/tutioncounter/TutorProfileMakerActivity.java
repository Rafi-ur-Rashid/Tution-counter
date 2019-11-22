package com.example.rf.tutioncounter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rf.tutioncounter.interfaces.saveTutorProfileSuccessfully;
import com.example.rf.tutioncounter.presenter.DataEmptyCheckPresenter;
import com.example.rf.tutioncounter.presenter.tutorProfileMakerPresenter;

import java.util.Random;

public class TutorProfileMakerActivity extends AppCompatActivity implements saveTutorProfileSuccessfully {
    EditText Name, Age, Inst, Pass;
    CheckBox male, female;
    ImageView pp;
    String name, age = "N/A", inst = "N/A", pass = "N/A", sex = "N/A", rid;
    tutorProfileMakerPresenter presenter;
    DataEmptyCheckPresenter tuttut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_profile_maker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tuttut = new DataEmptyCheckPresenter(this);
        Name = (EditText) findViewById(R.id.name);
        Age = (EditText) findViewById(R.id.age);
        Inst = (EditText) findViewById(R.id.location);
        Pass = (EditText) findViewById(R.id.passWord);
        male = (CheckBox) findViewById(R.id.boy);
        female = (CheckBox) findViewById(R.id.female);
        pp = (ImageView) findViewById(R.id.pp);
        male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    female.setChecked(false);
                        pp.setImageResource(R.drawable.male);
                }
                if(!male.isChecked() && !female.isChecked())
                    pp.setImageResource(R.drawable.people);

            }
        });
        female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    male.setChecked(false);
                        pp.setImageResource(R.drawable.female);
                }
                if(!male.isChecked() && !female.isChecked())
                    pp.setImageResource(R.drawable.people);
            }
        });
    }

    public void createTutorProfile(View view) {
        if (Name.getText().toString().length() <= 0)
            Toast.makeText(TutorProfileMakerActivity.this, R.string.Warning, Toast.LENGTH_LONG).show();
        else {
            name = Name.getText().toString();
            Random rd = new Random();
            rid = name + rd.nextInt(1000);
            if (Age.getText().toString().length() > 0) age = Age.getText().toString();
            if (Inst.getText().toString().length() > 0) inst = Inst.getText().toString();
            if (Pass.getText().toString().length() > 0) pass = Pass.getText().toString();
            if (male.isChecked())
                sex = "male";
            else if (female.isChecked())
                sex = "female";
            presenter = new tutorProfileMakerPresenter(TutorProfileMakerActivity.this, this, name, age, pass, inst, sex, rid);
            presenter.loadAndSave();
        }

    }

    public void onBackPressed() {
        if (tuttut.chekckEmptiness()) {
            Intent intent = new Intent(this, TutorProfilesListActivity.class);
            startActivity(intent);
        }
        finish();
        TutorProfileMakerActivity.super.onBackPressed();


    }

    @Override
    public void letsAddAstudent() {
        Intent intent = new Intent(TutorProfileMakerActivity.this, tutorProfileCreatedActivity.class);
        intent.putExtra("Name", name);
        intent.putExtra("sex", sex);
        intent.putExtra("inst", inst);
        intent.putExtra("age", age);
        intent.putExtra("no_stdnt", "0");
        intent.putExtra("rid", rid);
        startActivity(intent);
        finish();
    }
}
