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
    public static ArrayList<ToDo> items;
    private CheckBox istodoComplete;
    private TextView todotext;
    private Button scheduleButton;
    private Button todoButton;
    private String todo;
    public PersonAdapter(Context context,ArrayList<ToDo> data){
        mcontext = context;
        items = data;
        mLayoutInflater = LayoutInflater.from(mcontext);
    }
    public void addTodo(ToDo t){ items.add(t); }
    public void removeTodo(int position){items.remove(position);}
    public ArrayList<ToDo> getAllToDo(){
        return items;
    }
    public void setAllToDo(ArrayList<ToDo> data){
        items = data;
    }
    @Override
    public int getCount() {return items.size();}

    @Override
    public ToDo getItem(int position) {return items.get(position);}

    @Override
    public long getItemId(int position) {return position;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.listview_to_do,null);

        TextView todotext = (TextView)view.findViewById(R.id.todotext);
        istodoComplete = (CheckBox)view.findViewById(R.id.istodoComplete);
        todotext.setText(items.get(position).getTodoname());
        istodoComplete.setChecked(items.get(position).IsComplete());

        istodoComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext.getApplicationContext(),String.valueOf(items.get(position).IsComplete()),Toast.LENGTH_SHORT).show();
                items.get(position).changecompletestate();
                //메인 화면 설정
                if(items.get(position).IsComplete()){
                    Users.selectedUser.removeItem(items.get(position));
                    if(Users.selectedUser.isCompletedToDoHide()){
                        PersonUI.thisPersonUI.hideCompletedToDo();
                    }
                }
                else{
                    Users.selectedUser.addItem(new MainItem(R.drawable.ic_outline_checklist_24,items.get(position).getTodoname(),items.get(position)));
                }
            }
        });
        //체크 박스 롱클릭시 할 일 삭제
        istodoComplete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PersonUI.thisPersonUI.removeToDo(position);
                return true;
            }
        });

        return view;
    }
}

