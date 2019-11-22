package com.example.rf.tutioncounter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rf.tutioncounter.Adapter.studentInfoListAdapter;
import com.example.rf.tutioncounter.interfaces.loadStudentInfoDetails;
import com.example.rf.tutioncounter.presenter.studentInfoPresenter;
import com.example.rf.tutioncounter.presenter.studentsInfoListPresenter;
import com.example.rf.tutioncounter.presenter.DataEmptyCheckPresenter;

import java.util.ArrayList;

public class studentInfoListActivity extends AppCompatActivity implements loadStudentInfoDetails {
    ListView l;
    ArrayList<String[]> data;
    studentInfoListAdapter adapter;
    studentInfoPresenter presenter;
    Bundle bundle;
    boolean[] listItemSelected;
    MenuItem itemEdit,itemDelete,itemInfo;
    String Tutorname,tableName;
    Snackbar snackbar;
    studentsInfoListPresenter profileList;
    boolean constr;
    DataEmptyCheckPresenter tuttut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        l=(ListView)findViewById(R.id.studentInfoList);
        tuttut = new DataEmptyCheckPresenter(this);
        bundle=getIntent().getExtras();
        Tutorname = bundle.getString("tutor");
        tableName = bundle.getString("rid");
        presenter=new studentInfoPresenter(this,tableName);
        data=presenter.loadData();
        listItemSelected=new boolean[data.size()];
        adapter=new studentInfoListAdapter(this,data);
        l.setAdapter(adapter);
        profileList=new studentsInfoListPresenter(studentInfoListActivity.this,studentInfoListActivity.this,tableName);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listItemSelected[position]) {
                    view.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    listItemSelected[position]=false;
                    int t,c=0;
                    for (int i=0;i<data.size();i++){
                        if(listItemSelected[i])
                            c++;
                    }
                    if(c>1)
                        itemEdit.setVisible(false);
                    else
                        itemEdit.setVisible(true);
                    for (t=0;t<data.size();t++){
                        if(listItemSelected[t])
                            break;
                    }
                    if(t==data.size())
                    {
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        setTitle(R.string.studentsList);
                        itemEdit.setVisible(false);
                        itemDelete.setVisible(false);
                        itemInfo.setVisible(true);
                    }
                    return;
                }
                profileList.loadData(data.get(data.size()-position-1)[7]);
            }
        });
        l.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent,View view, int position, long id) {

                listItemSelected[position]=true;
                int c=0;
                for (int t=0;t<data.size();t++){
                    if(listItemSelected[t])
                        c++;
                }
                if(c>1)
                    itemEdit.setVisible(false);
                else
                    itemEdit.setVisible(true);
                itemDelete.setVisible(true);
                itemInfo.setVisible(false);
                view.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                setTitle("");
                return true;
            }
        });
    }
    public void addAnewStudent(View view) {
        Intent intent = new Intent(studentInfoListActivity.this, studentInfoMakerActivity.class);
        intent.putExtra("tutor",Tutorname);
        intent.putExtra("rid", tableName);
        intent.putExtra("constr", false);
        startActivity(intent);
        finish();
    }
    public void onBackPressed() {
        if(tuttut.chekckEmptiness()) {
            Intent intent = new Intent(this, TutorProfilesListActivity.class);
            startActivity(intent);
        }
        finish();
        studentInfoListActivity.super.onBackPressed();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_delete, menu);
        itemDelete=menu.findItem(R.id.action_delete);
        itemEdit=menu.findItem(R.id.action_edit);
        itemInfo=menu.findItem(R.id.action_info);
        itemEdit.setVisible(false);
        itemDelete.setVisible(false);
        itemInfo.setVisible(true);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.Alert)
                    .setMessage(R.string.deleteDialoge1)
                    .setNegativeButton(R.string.no,null)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            for (int t=0;t<data.size();t++){
                                if(listItemSelected[t])
                                {
                                    profileList.deleteData(data.get(data.size()-t-1)[7]);
                                }
                            }
                            if (tuttut.chekckEmptiness1(tableName)) {
                                Intent intent = new Intent(studentInfoListActivity.this, studentInfoListActivity.class);
                                intent.putExtra("tutor", Tutorname);
                                intent.putExtra("rid", tableName);
                                startActivity(intent);
                            }
                            else if(tuttut.chekckEmptiness()) {
                                Intent intent = new Intent(studentInfoListActivity.this, TutorProfilesListActivity.class);
                                startActivity(intent);
                            }
                            finish();
                        }
                    }).create().show();

            return true;
        }
        else if (id == R.id.action_edit) {
            int t;
            for (t=0;t<data.size();t++) {
                if (listItemSelected[t])
                    break;
            }
            Intent intent=new Intent(this,editStudentInfoActivity.class);
            intent.putExtra("sid",data.get(data.size()-t-1)[7]);
            intent.putExtra("rid",tableName);
            intent.putExtra("tutor",Tutorname);
            startActivity(intent);
            finish();
            return true;
        }
        else if (id == R.id.action_info) {
            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout,R.string.infoDialogue2, Snackbar.LENGTH_LONG)
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                    .show();
            return true;
        }
        else if (id == android.R.id.home) {
            for(int i=0;i<data.size();i++) {
                getViewByPosition(i).setBackgroundColor(getResources().getColor(android.R.color.transparent));
                listItemSelected[i]= false;
            }
            int t=0;
            for (t=0;t<data.size();t++){
                if(listItemSelected[t])
                    break;
            }
            if(t==data.size())
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            setTitle(R.string.studentsList);
            itemEdit.setVisible(false);
            itemDelete.setVisible(false);
            itemInfo.setVisible(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void showStudentInfo(ArrayList<String> details) {
        Intent intent=new Intent(this,studentInfoDetailsActivity.class);
        intent.putExtra("name",details.get(0));
        intent.putExtra("sex",details.get(1));
        intent.putExtra("subject",details.get(2));
        intent.putExtra("class",details.get(3));
        intent.putExtra("day",details.get(4));
        intent.putExtra("cout",details.get(5));
        intent.putExtra("dates",details.get(6));
        intent.putExtra("sid",details.get(7));
        intent.putExtra("mcount",details.get(8));
        intent.putExtra("tutor",Tutorname);
        intent.putExtra("rid",tableName);
        startActivity(intent);
        finish();
    }
    public View getViewByPosition(int pos) {
        final int firstListItemPosition = l.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + l.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return l.getAdapter().getView(pos, null, l);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return l.getChildAt(childIndex);
        }
    }
}
