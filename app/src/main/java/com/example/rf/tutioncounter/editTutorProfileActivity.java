package com.example.rf.tutioncounter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rf.tutioncounter.interfaces.saveEditedData;
import com.example.rf.tutioncounter.interfaces.tutorCompleteEdition;
import com.example.rf.tutioncounter.presenter.DataEmptyCheckPresenter;
import com.example.rf.tutioncounter.presenter.tutorEditPresenter;

import java.util.ArrayList;

public class editTutorProfileActivity extends AppCompatActivity implements saveEditedData {
    EditText Name, Age, Inst, Pass;
    CheckBox male, female;
    DataEmptyCheckPresenter tuttut;
    tutorEditPresenter presenter;
    ArrayList<String> data;
    String name,age = "N/A", inst = "N/A", pass = "N/A", sex = "N/A", rid,no_std;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tutor_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bundle=getIntent().getExtras();
        rid=bundle.getString("rid",rid);
        tuttut = new DataEmptyCheckPresenter(this);
        Name = (EditText) findViewById(R.id.name);
        Age = (EditText) findViewById(R.id.age);
        Inst = (EditText) findViewById(R.id.location);
        Pass = (EditText) findViewById(R.id.passWord);
        male = (CheckBox) findViewById(R.id.male);
        female = (CheckBox) findViewById(R.id.female);
        presenter=new tutorEditPresenter(this,this);
        data=presenter.loadData(rid);
        Name.setText(data.get(0));
        sex=data.get(1);
        no_std=data.get(4);
        if(sex.equals("male"))
            male.setChecked(true);
        else if(sex.equals("female"))
            female.setChecked(true);
        if(!data.get(2).equals("N/A"))
            Inst.setText(data.get(2));
        if(!data.get(3).equals("N/A"))
            Age.setText(data.get(3));
        if(!data.get(6).equals("N/A"))
            Pass.setText(data.get(6));
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
    public void doneEditing(View view){
        if(Name.getText().toString().length()<1)
            Toast.makeText(this, R.string.Warning, Toast.LENGTH_LONG).show();
        else {
            name = Name.getText().toString();
            if (Age.getText().toString().length() > 0)
                age = Age.getText().toString();
            if (Inst.getText().toString().length() > 0)
                inst = Inst.getText().toString();
            if (Pass.getText().toString().length() > 0)
                pass = Pass.getText().toString();
            if (male.isChecked())
                sex = "male";
            else if (female.isChecked())
                sex = "female";
            presenter.saveData(rid, name, age, inst, pass, sex);
        }

    }
    public void onBackPressed() {
        if (tuttut.chekckEmptiness()) {
            Intent intent = new Intent(this, TutorProfilesListActivity.class);
            startActivity(intent);
        }
        finish();
        editTutorProfileActivity.super.onBackPressed();


    }
    @Override
    public void saveData() {
        Intent intent=new Intent(this,tutorProfileDetils.class);
        intent.putExtra("name",name);
        intent.putExtra("age",age);
        intent.putExtra("inst",inst);
        intent.putExtra("rid",rid);
        intent.putExtra("sex",sex);
        intent.putExtra("no_stdnt",no_std);
        startActivity(intent);
        finish();
    }
}
