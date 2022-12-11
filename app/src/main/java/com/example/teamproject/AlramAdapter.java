package com.example.teamproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AlramAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    private static ArrayList<String> alrams;
    public AlramAdapter(Context context, ArrayList<String> data) {
        mContext = context;
        alrams = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }
    public void setAlrams(ArrayList<String> a){alrams = a;}

    public ArrayList<String> getAlarms(){ return alrams; }
    public void removeAlram(int position){alrams.remove(position);}
    @Override
    public int getCount() {
        return alrams.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public String getItem(int position) {
        return alrams.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.listview_alram, null);

        String[] text = alrams.get(position).split("\\n");
        String date = text[0];
        String content = text[1] + "\n" + "파일을 제출해주세요.";
        TextView alarmDate = (TextView) view.findViewById(R.id.alramDate);
        TextView alarmContent = (TextView) view.findViewById(R.id.alramContent);
        alarmDate.setText(date);
        alarmContent.setText(content);
        return view;
    }
}
