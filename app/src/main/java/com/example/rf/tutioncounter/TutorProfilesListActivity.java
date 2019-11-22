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
import android.widget.Button;
import android.widget.ListView;

import com.example.rf.tutioncounter.Adapter.tuTorListAdapter;
import com.example.rf.tutioncounter.interfaces.loadTutorDetails;
import com.example.rf.tutioncounter.presenter.DataEmptyCheckPresenter;
import com.example.rf.tutioncounter.presenter.tutorProfileListpresenter;
import com.example.rf.tutioncounter.presenter.tutorProfilePresenter;

import java.util.ArrayList;

public class TutorProfilesListActivity extends AppCompatActivity implements loadTutorDetails {
    ListView l;
    tutorProfilePresenter profilepresenter;
    ArrayList<String[]>data;
    boolean[] listItemSelected;
    MenuItem itemEdit,itemDelete,itemInfo;
    tuTorListAdapter adapter;
    Snackbar snackbar;
    DataEmptyCheckPresenter tuttut;
    tutorProfileListpresenter profileList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_profiles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tuttut = new DataEmptyCheckPresenter(this);
        l=(ListView)findViewById(R.id.tutorList);
        profilepresenter=new tutorProfilePresenter(this);
        data=profilepresenter.loadData();
        listItemSelected=new boolean[data.size()];
        adapter=new tuTorListAdapter(this,data);
        l.setAdapter(adapter);
        profileList=new tutorProfileListpresenter(TutorProfilesListActivity.this,TutorProfilesListActivity.this);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listItemSelected[position]) {
                    view.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    //view.setLongClickable(true);
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
                        setTitle(R.string.tutorsList);
                        itemEdit.setVisible(false);
                        itemDelete.setVisible(false);
                        itemInfo.setVisible(true);
                    }
                    return;
                }
                profileList.loadData(data.get(data.size()-position-1)[3]);

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
        Button addAtutor = (Button) findViewById(R.id.addAtutor);
        addAtutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TutorProfilesListActivity.this,TutorProfileMakerActivity.class);
                startActivity(intent);
                finish();
            }
        });
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
                    .setMessage(R.string.deleteDialoge2)
                    .setNegativeButton(R.string.no,null)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            for (int t=0;t<data.size();t++){
                                if(listItemSelected[t])
                                {
                                    profileList.deleteData(data.get(data.size()-t-1)[3]);
                                }
                            }
                            if (tuttut.chekckEmptiness2()) {
                                Intent intent = new Intent(TutorProfilesListActivity.this, TutorProfilesListActivity.class);
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
            Intent intent=new Intent(this,editTutorProfileActivity.class);
            intent.putExtra("rid",data.get(data.size()-t-1)[3]);
            startActivity(intent);
            finish();
            return true;
        }
        else if (id == R.id.action_info) {
            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout,R.string.infoDialogue1, Snackbar.LENGTH_LONG)
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
    public void showTutorProfile(ArrayList<String>details) {
        Intent intent=new Intent(TutorProfilesListActivity.this,tutorProfileDetils.class);
        intent.putExtra("name",details.get(0));
        intent.putExtra("sex",details.get(1));
        intent.putExtra("inst",details.get(2));
        intent.putExtra("age",details.get(3));
        intent.putExtra("no_stdnt",details.get(4));
        intent.putExtra("rid",details.get(5));
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
