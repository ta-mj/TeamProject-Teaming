package com.example.teamproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PersonAdapter extends BaseAdapter {
    Context mcontext = null;
    LayoutInflater mLayoutInflater = null;
    public static ArrayList<ToDo> todolist;
    private CheckBox istodoComplete;
    private TextView todotext;
    private Button scheduleButton;
    private Button todoButton;
    private String todo;
    public PersonAdapter(Context context,ArrayList<ToDo> data){
        mcontext = context;
        todolist = data;
        mLayoutInflater = LayoutInflater.from(mcontext);
    }

    public void removeTodo(int position){todolist.remove(position);}

    @Override
    public int getCount() {return todolist.size();}

    @Override
    public ToDo getItem(int position) {return todolist.get(position);}

    @Override
    public long getItemId(int position) {return position;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.listview_to_do,null);

        TextView todotext = (TextView)view.findViewById(R.id.todotext);
        istodoComplete = (CheckBox)view.findViewById(R.id.istodoComplete);
        todotext.setText(todolist.get(position).getTodoname());


        istodoComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext.getApplicationContext(),String.valueOf(todolist.get(position).iscomplete),Toast.LENGTH_SHORT).show();
                todolist.get(position).changecompletestate();
            }
        });

        return view;
    }
}

