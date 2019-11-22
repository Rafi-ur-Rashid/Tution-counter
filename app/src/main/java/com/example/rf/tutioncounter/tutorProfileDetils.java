package com.example.rf.tutioncounter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rf.tutioncounter.presenter.DataEmptyCheckPresenter;

public class tutorProfileDetils extends AppCompatActivity {
    Bundle bundle;
    TextView Name,Age,Inst;
    ImageView photo;
    Button addAstudent;
    String name,age,inst,sex,noStd,rid;
    DataEmptyCheckPresenter tuttut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_profile_detils);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tuttut=new DataEmptyCheckPresenter(this);
        bundle=getIntent().getExtras();
        name=bundle.getString("name");
         age=bundle.getString("age");
         inst=bundle.getString("inst");
        rid=bundle.getString("rid");
         sex=bundle.getString("sex");
         noStd=bundle.getString("no_stdnt");
        addAstudent=(Button)findViewById(R.id.studentList);
        addAstudent.setText(addAstudent.getText()+"("+utilityMethods.E2BnumberConverter(noStd)+")");
        Name=(TextView)findViewById(R.id.name);
        Age=(TextView)findViewById(R.id.age);
        Inst=(TextView)findViewById(R.id.inst);
        photo=(ImageView)findViewById(R.id.photo);
        Name.setText(this.getString(R.string.Name2)+" : "+name);
        Age.setText(this.getString(R.string.Age)+" : "+age);
        Inst.setText(this.getString(R.string.Location)+" : "+inst);
            if (sex.equals("male"))
                photo.setImageResource(R.drawable.male);
            else if (sex.equals("female"))
                photo.setImageResource(R.drawable.female);
    }
    public void onBackPressed() {
        if(tuttut.chekckEmptiness()) {
            Intent intent = new Intent(this, TutorProfilesListActivity.class);
            startActivity(intent);
        }
        finish();
        tutorProfileDetils.super.onBackPressed();

    }
    public void addAnewStudentInfo(View view){
        Intent intent=new Intent(tutorProfileDetils.this,studentInfoMakerActivity.class);
        intent.putExtra("tutor",name);
        intent.putExtra("rid",rid);
        if(noStd.equals("0")) {
            intent.putExtra("constr", true);
        }
        else
            intent.putExtra("constr",false);
        startActivity(intent);
        finish();
    }
    public void currentStudentList(View view){
        if (Integer.parseInt(noStd)<1){
            Toast.makeText(this,this.getString(R.string.underflowStudentNum)+" "+CONSTANT_VALUES.NUMBERS[0][1],Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent=new Intent(tutorProfileDetils.this,studentInfoListActivity.class);
        intent.putExtra("tutor",name);
        intent.putExtra("rid",rid);
        startActivity(intent);
        finish();
    }
}
