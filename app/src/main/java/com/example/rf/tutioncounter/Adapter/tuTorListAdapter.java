package com.example.rf.tutioncounter.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rf.tutioncounter.R;
import com.example.rf.tutioncounter.utilityMethods;

import java.util.ArrayList;

public class tuTorListAdapter extends BaseAdapter {
    Context c;
    TextView upper, lower;
    ImageView photo;
    ArrayList<String[]> list;

    public tuTorListAdapter(Context c, ArrayList<String[]> list) {
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
        convertView = inflater.inflate(R.layout.tutor_list_element, parent, false);
        upper = (TextView) convertView.findViewById(R.id.upper);
        lower = (TextView) convertView.findViewById(R.id.details);
        photo = (ImageView) convertView.findViewById(R.id.photo);
        upper.setText(list.get(list.size()-position-1)[0]);
        lower.setText(c.getString(R.string.noOfStudents) + " : " + utilityMethods.E2BnumberConverter(list.get(list.size()-position-1)[2]));
        if (list.get(list.size()-position-1)[1].equals("male"))
            photo.setImageResource(R.drawable.male);

        else if (list.get(list.size()-position-1)[1].equals("female"))
            photo.setImageResource(R.drawable.female);
        return convertView;
    }
}

