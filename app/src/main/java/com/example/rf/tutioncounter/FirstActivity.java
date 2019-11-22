package com.example.rf.tutioncounter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.rf.tutioncounter.interfaces.consumerRequest;
import com.example.rf.tutioncounter.presenter.FirstActivityPresenter;



public class FirstActivity extends Activity implements consumerRequest {
    FirstActivityPresenter firstActivityPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firstActivityPresenter=new FirstActivityPresenter(this,FirstActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settings=new Intent(this,settingsActivity.class);
            startActivity(settings);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.Alert)
                .setMessage(R.string.QuitDialoge)
                .setNegativeButton(R.string.no,null)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        FirstActivity.super.onBackPressed();
                    }
                }).create().show();

    }
    public void tutorCorner(View view){
        firstActivityPresenter.gotoNext();
    }

    @Override
    public void goToNextActivity(boolean first) {
        if(!first)
        {
            Intent intent=new Intent(FirstActivity.this,Invoke1stActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent=new Intent(FirstActivity.this,TutorProfilesListActivity.class);
            startActivity(intent);
        }
    }
}
