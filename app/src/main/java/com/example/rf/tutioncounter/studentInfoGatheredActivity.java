package com.example.rf.tutioncounter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.rf.tutioncounter.presenter.DataEmptyCheckPresenter;

public class studentInfoGatheredActivity extends AppCompatActivity {
    Bundle bundle;
    String Tutorname,name,Cls = "N/A",sub = "N/A", sex = "N/A",Days="N/A",rid,sid,mCount;
    DataEmptyCheckPresenter tuttut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info_gathered);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tuttut=new DataEmptyCheckPresenter(this);
        bundle=getIntent().getExtras();
        Tutorname=bundle.getString("tutor");
        rid=bundle.getString("rid");
        name=bundle.getString("name");
        sex=bundle.getString("sex");
        Cls=bundle.getString("class");
        sub=bundle.getString("subject");
        Days=bundle.getString("days");
        sid=bundle.getString("sid");
        mCount=bundle.getString("mcount");
    }
    public void checkInfo(View view){
        //Toast.makeText(this,sid, Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(studentInfoGatheredActivity.this,studentInfoDetailsActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("sex", sex);
        intent.putExtra("class",Cls);
        intent.putExtra("subject",sub);
        intent.putExtra("day",Days);
        intent.putExtra("cout","0");
        intent.putExtra("tutor",Tutorname);
        intent.putExtra("rid",rid);
        intent.putExtra("sid",sid);
        intent.putExtra("mcount",mCount);
        startActivity(intent);
        finish();
    }
    public void addAnotherStudent(View view){
        Intent intent=new Intent(studentInfoGatheredActivity.this,studentInfoMakerActivity.class);
        intent.putExtra("tutor",Tutorname);
        intent.putExtra("rid",rid);
        intent.putExtra("constr",false);
        startActivity(intent);
        finish();
    }
    public void onBackPressed() {
        if(tuttut.chekckEmptiness()) {
            Intent intent = new Intent(this, TutorProfilesListActivity.class);
            startActivity(intent);
        }
        finish();
        studentInfoGatheredActivity.super.onBackPressed();


    }

}
