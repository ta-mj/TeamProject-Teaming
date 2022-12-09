package com.example.teamproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<MainItem> members;

    public MainAdapter(Context context, ArrayList<MainItem> data) {
        mContext = context;
        members = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return members.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public MainItem getItem(int position) {
        return members.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.listview_main, null);

        ImageView image = (ImageView) view.findViewById(R.id.main_image);
        TextView content = (TextView) view.findViewById(R.id.main_textview);

        image.setImageResource(members.get(position).getImage());
        content.setText(members.get(position).getContent());

        return view;
    }
}
