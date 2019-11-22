package com.example.rf.tutioncounter;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rf.tutioncounter.Adapter.dateListAdapter;
import com.example.rf.tutioncounter.presenter.studentsInfoListPresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

public class DateListActivity extends AppCompatActivity {
    MenuItem itemEdit, itemDelete, itemInfo;
    dateListAdapter cAdapter;
    ArrayList<String[]> list;
    ArrayList<String> dates;
    boolean[] listItemSelected;
    Calendar myCalendar = Calendar.getInstance();
    ListView cal;
    TextView t1,t2;
    Button addAclass;
    String name, cls, count, sex, days, subject, date, tutor, rid, sid, tempDays, mcount,m_Text;
    StringTokenizer st, st1;
    Bundle bundle;
    studentsInfoListPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cal=(ListView)findViewById(R.id.datelist) ;
        addAclass=(Button)findViewById(R.id.addAClass);
        bundle = getIntent().getExtras();
        rid=bundle.getString("rid");
        presenter = new studentsInfoListPresenter(this, null, rid);
        date=bundle.getString("date");
        name = bundle.getString("name");
        sex = bundle.getString("sex");
        cls = bundle.getString("class");
        subject = bundle.getString("subject");
        count = bundle.getString("cout");
        days = bundle.getString("day");
        mcount = bundle.getString("mcount");
        tempDays = days;
        date = bundle.getString("dates");
        tutor = bundle.getString("tutor");
        sid = bundle.getString("sid");
        st = new StringTokenizer(date, ";");
        dates = new ArrayList<>();
        list = new ArrayList();
        while (st.hasMoreTokens()) {
            String temp = st.nextToken();
            dates.add(temp + ";");
            st1 = new StringTokenizer(temp, "%");
            if (st1.hasMoreTokens()) {
                String d, w = null;
                d = st1.nextToken();
                if (st1.hasMoreTokens())
                    w = st1.nextToken();
                list.add(new String[]{d, w});
            }
        }
        cAdapter = new dateListAdapter(this, list);
        cal.setAdapter(cAdapter);
        listItemSelected = new boolean[list.size()];
            cal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    t1 = (TextView) view.findViewById(R.id.left);
                    t2 = (TextView) view.findViewById(R.id.right);
                    if (listItemSelected[position]) {
                        t1.setBackgroundColor(getResources().getColor(android.R.color.background_light));
                        t2.setBackgroundColor(getResources().getColor(android.R.color.background_light));
                        listItemSelected[position] = false;
                        int t;
                        for (t = 0; t < list.size(); t++) {
                            if (listItemSelected[t])
                                break;
                        }
                        if (t == list.size()) {
                            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                            setTitle(R.string.studentsList);
                            itemEdit.setVisible(false);
                            itemDelete.setVisible(false);
                            itemInfo.setVisible(true);
                        }
                        return;
                    }
                }
            });
        cal.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    t1 = (TextView) view.findViewById(R.id.left);
                    t2 = (TextView) view.findViewById(R.id.right);
                    listItemSelected[position] = true;
                    itemDelete.setVisible(true);
                    itemInfo.setVisible(false);
                    t1.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                    t2.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                    getSupportActionBar().setHomeButtonEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    setTitle("");
                    return true;
                }
            });

        addAclass.setOnClickListener(new View.OnClickListener() {
            final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                }

            };
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(DateListActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_delete, menu);
        itemDelete = menu.findItem(R.id.action_delete);
        itemEdit = menu.findItem(R.id.action_edit);
        itemInfo = menu.findItem(R.id.action_info);
        itemEdit.setVisible(false);
        itemDelete.setVisible(false);
        if (list != null && list.size() > 0)
            itemInfo.setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.Alert)
                    .setMessage(R.string.deleteDialoge3)
                    .setNegativeButton(R.string.no, null)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            for (int t = 0; t < list.size(); t++) {
                                if (listItemSelected[t]) {
                                    count = (Integer.parseInt(count) - 1) + "";
//                                    Toast.makeText(DateListActivity.this,dates.get(list.size()-t-1),Toast.LENGTH_LONG).show();
                                    date = presenter.deleteDate(sid, dates.get(list.size()-t-1));
                                }
                            }
                            Intent intent = new Intent(DateListActivity.this, DateListActivity.class);
                            intent.putExtra("name", name);
                            intent.putExtra("sex", sex);
                            intent.putExtra("subject", subject);
                            intent.putExtra("class", cls);
                            intent.putExtra("day", tempDays.substring(0, tempDays.length() - 1) + "C");
                            intent.putExtra("cout", count);
                            intent.putExtra("dates", date);
                            intent.putExtra("sid", sid);
                            intent.putExtra("tutor", tutor);
                            intent.putExtra("rid", rid);
                            intent.putExtra("mcount", mcount);
                            startActivity(intent);
                            finish();
                        }
                    }).create().show();
        } else if (id == R.id.action_info) {
            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout, R.string.infoDialogue3, Snackbar.LENGTH_LONG)
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                    .show();
            return true;
        } else if (id == android.R.id.home) {
            for (int i = 0; i < list.size(); i++) {
                View v = getViewByPosition(i);
                t1 = (TextView) v.findViewById(R.id.left);
                t2 = (TextView) v.findViewById(R.id.right);
                t1.setBackgroundColor(getResources().getColor(android.R.color.background_light));
                t2.setBackgroundColor(getResources().getColor(android.R.color.background_light));
                listItemSelected[i] = false;
            }
            int t = 0;
            for (t = 0; t < list.size(); t++) {
                if (listItemSelected[t])
                    break;
            }
            if (t == list.size())
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            setTitle(R.string.studentsList);
            itemDelete.setVisible(false);
            itemInfo.setVisible(true);
        }
        return true;
    }
    public View getViewByPosition(int pos) {
        final int firstListItemPosition = cal.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + cal.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return cal.getAdapter().getView(pos, null, cal);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return cal.getChildAt(childIndex);
        }
    }
    public void onBackPressed() {
            Intent intent = new Intent(DateListActivity.this, studentInfoDetailsActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("sex", sex);
            intent.putExtra("subject", subject);
            intent.putExtra("class", cls);
            intent.putExtra("day", tempDays.substring(0, tempDays.length() - 1) + "C");
            intent.putExtra("cout", count);
            intent.putExtra("dates", date);
            intent.putExtra("sid", sid);
            intent.putExtra("tutor", tutor);
            intent.putExtra("rid", rid);
            intent.putExtra("mcount", mcount);
            startActivity(intent);
            finish();
        DateListActivity.super.onBackPressed();

    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String s=sdf.format(myCalendar.getTime());
        //Toast.makeText(this,sdf.format(),Toast.LENGTH_LONG).show();
        StringTokenizer st=new StringTokenizer(s,"/");
        int month=myCalendar.get(Calendar.MONTH)+1;
        int day=myCalendar.get(Calendar.DAY_OF_MONTH);
        int year=myCalendar.get(Calendar.YEAR);
        count = (Integer.parseInt(count) +1) + "";
        String wDay=""+ CONSTANT_VALUES.DAYS[myCalendar.get(Calendar.DAY_OF_WEEK)-1][1]+"বার";
        String dates=day+" / "+month+" / "+year+"%"+wDay;
        Toast.makeText(this,day+" / "+month+" / "+year+"%"+wDay,Toast.LENGTH_LONG).show();
        String date=presenter.updateDate(sid,dates);
        Intent intent = new Intent(DateListActivity.this, DateListActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("sex", sex);
        intent.putExtra("subject", subject);
        intent.putExtra("class", cls);
        intent.putExtra("day", tempDays.substring(0, tempDays.length() - 1) + "C");
        intent.putExtra("cout", count);
        intent.putExtra("dates", date);
        intent.putExtra("sid", sid);
        intent.putExtra("tutor", tutor);
        intent.putExtra("rid", rid);
        intent.putExtra("mcount", mcount);
        startActivity(intent);
        finish();
        //edittext.setText(sdf.format(myCalendar.getTime()));
    }
}
