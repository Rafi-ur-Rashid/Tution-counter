package com.example.rf.tutioncounter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.rf.tutioncounter.presenter.DataEmptyCheckPresenter;

public class tutorProfileCreatedActivity extends AppCompatActivity {
    Bundle bundle;
    DataEmptyCheckPresenter tuttut;
    String name,age="N/A",inst="N/A",sex="N/A",noStd="0",rid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_profile_created);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tuttut=new DataEmptyCheckPresenter(this);
        bundle=getIntent().getExtras();
        name=bundle.getString("Name");
        age=bundle.getString("age");
        inst=bundle.getString("inst");
        sex=bundle.getString("sex");
        rid=bundle.getString("rid");
    }
    public void checkProfile(View view){
        Intent intent=new Intent(tutorProfileCreatedActivity.this,tutorProfileDetils.class);
        intent.putExtra("sex", sex);
        intent.putExtra("inst",inst);
        intent.putExtra("age",age);
        intent.putExtra("no_stdnt",noStd);
        intent.putExtra("name",name);
        intent.putExtra("rid",rid);
        startActivity(intent);
        finish();
    }
    public void addAstudentNow(View view){
        Intent intent=new Intent(tutorProfileCreatedActivity.this,studentInfoMakerActivity.class);
        intent.putExtra("tutor",name);
        intent.putExtra("rid",rid);
        intent.putExtra("constr",true);
        startActivity(intent);
        finish();
    }
    public void onBackPressed() {
        if(tuttut.chekckEmptiness()) {
            Intent intent = new Intent(tutorProfileCreatedActivity.this, TutorProfilesListActivity.class);
            startActivity(intent);
        }
        finish();
        tutorProfileCreatedActivity.super.onBackPressed();
    }

}
