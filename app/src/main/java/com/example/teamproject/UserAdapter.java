package com.example.teamproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<User> members;

    public UserAdapter(Context context, ArrayList<User> data) {
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
    public User getItem(int position) {
        return members.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.listview_user, null);

        ImageView personImage = (ImageView)view.findViewById(R.id.personImage);
        TextView personName = (TextView)view.findViewById(R.id.personName);

        personName.setText(members.get(position).getName());

        return view;
    }
}
