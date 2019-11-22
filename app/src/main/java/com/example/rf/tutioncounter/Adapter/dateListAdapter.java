package com.example.rf.tutioncounter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rf.tutioncounter.R;

import java.util.ArrayList;

/**
 * Created by RF on 11/14/2017.
 */

public class dateListAdapter extends BaseAdapter {
    Context c;
    ArrayList<String[]> list;
    TextView left,right;
    public dateListAdapter(Context context, ArrayList<String[]> list){
        c=context;
        this.list=list;
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
        convertView = inflater.inflate(R.layout.datelist_item, parent, false);
        left = (TextView) convertView.findViewById(R.id.left);
        right= (TextView) convertView.findViewById(R.id.right);
        left.setText(list.get(list.size()-position-1)[0]);
        right.setText(list.get(list.size()-position-1)[1]);
        return convertView;
    }
}
