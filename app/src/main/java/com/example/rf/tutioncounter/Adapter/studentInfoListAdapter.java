package com.example.rf.tutioncounter.Adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rf.tutioncounter.R;
import com.example.rf.tutioncounter.utilityMethods;

import java.util.ArrayList;

public class studentInfoListAdapter extends BaseAdapter {
    Context c;
    TextView upper,lower2;
    ImageView photo;
    ArrayList<String[]> list;

    public studentInfoListAdapter(Context c, ArrayList<String[]> list) {
        this.c = c;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.student_info_list_element, parent, false);
        upper = (TextView) convertView.findViewById(R.id.upper);
        lower2= (TextView) convertView.findViewById(R.id.details2);
        photo = (ImageView) convertView.findViewById(R.id.photo);
        upper.setText(list.get(list.size()-position-1)[0]);
        final String days=list.get(list.size()-position-1)[3];
        if(days.charAt(days.length()-1)=='C')
            lower2.setText(c.getString(R.string.monthCount1) +" "+ utilityMethods.E2BnumberConverter(list.get(list.size()-position-1)[5]) +" "+c.getString(R.string.monthCount2));
        else {
            lower2.setText(c.getString(R.string.monthCountNull));
            lower2.setTextColor(c.getResources().getColor(R.color.colorAccent));
        }
        if (list.get(list.size()-position-1)[1].equals("male"))
            photo.setImageResource(R.drawable.boy);
        else if (list.get(list.size()-position-1)[1].equals("female"))
            photo.setImageResource(R.drawable.girl);
        return convertView;
    }
}

