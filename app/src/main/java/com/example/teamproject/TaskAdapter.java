package com.example.teamproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TaskAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    private static ArrayList<Task> tasks;
    private ImageButton fileUploadButton;
    private CheckBox isTaskComplete;
    private Intent taskUIToFileUpload;
    public TaskAdapter(Context context, ArrayList<Task> data) {
        mContext = context;
        tasks = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }
    public void setTasks(ArrayList<Task> t){
        tasks = t;
        for(int i = 0 ; i < tasks.size() ; i++){
            isTaskComplete.setChecked(tasks.get(i).IsComplete());
        }
    }

    public ArrayList<Task> getTasks(){ return tasks; }
    public void removeTask(int position){tasks.remove(position);}
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
        fileUploadButton = (ImageButton)view.findViewById(R.id.commitButton);
        isTaskComplete = (CheckBox)view.findViewById(R.id.isTaskComplete);
        isTaskComplete.setChecked(tasks.get(position).IsComplete());
        managerName.setText(tasks.get(position).getManager().getName());
        workName.setText(tasks.get(position).getWorkName());
        deadline.setText(tasks.get(position).getTargetDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        fileUploadButton.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view) {
                TaskUI.thisTaskUI.removeTask(position);
                return true;
            }
        });
        fileUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext.getApplicationContext(), tasks.get(position).getManager().getName(),Toast.LENGTH_SHORT).show();
                taskUIToFileUpload = new Intent(TaskUI.thisTaskUI, FileUpload.class);
                taskUIToFileUpload.putExtra("선택업무", tasks.get(position));
                TaskUI.thisTaskUI.startActivity(taskUIToFileUpload);
            }
        });

        isTaskComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task t = tasks.get(position);
                t.changeCompleteState();
                if(Users.selectedUser.equals(t.getManager())) {
                    if (t.IsComplete()) {
                        Users.selectedUser.removeItem(t);
                    }
                    else{
                        Users.selectedUser.addItem(new MainItem(R.drawable.file,t.getWorkName(),t));
                    }
                }
            }
        });
        return view;
    }



}
