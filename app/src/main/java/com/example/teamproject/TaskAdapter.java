package com.example.teamproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TaskAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<Task> tasks;

    public TaskAdapter(Context context, ArrayList<Task> data) {
        mContext = context;
        tasks = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Task getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.listview_task, null);

        TextView managerName = (TextView)view.findViewById(R.id.managerName);
        TextView workName = (TextView)view.findViewById(R.id.workNameText);
        TextView deadline = (TextView)view.findViewById(R.id.deadLineText);

        managerName.setText(tasks.get(position).getManager().getName());
        workName.setText(tasks.get(position).getWorkName());
        deadline.setText(tasks.get(position).getTargetDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return view;
    }
}
