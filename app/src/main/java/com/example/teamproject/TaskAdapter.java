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

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TaskAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    private ArrayList<Task> tasks;
    private ImageButton fileUploadButton;
    private CheckBox isTaskComplete;
    private Intent taskUIToFileUpload;

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
        fileUploadButton = (ImageButton)view.findViewById(R.id.commitButton);
        isTaskComplete = (CheckBox)view.findViewById(R.id.isTaskComplete);
        isTaskComplete.setChecked(Users.selectedProject.getAllTask().get(position).is_complete);
        managerName.setText(tasks.get(position).getManager().getName());
        workName.setText(tasks.get(position).getWorkName());
        deadline.setText(tasks.get(position).getTargetDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        fileUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext.getApplicationContext(), tasks.get(position).getManager().getName(),Toast.LENGTH_SHORT).show();
                taskUIToFileUpload = new Intent(TaskUI.thisTaskUI, FileUpload.class);
                taskUIToFileUpload.putExtra("담당자", tasks.get(position).getManager().getName());
                taskUIToFileUpload.putExtra("업무 제목",tasks.get(position).getWorkName());
                taskUIToFileUpload.putExtra("설명",tasks.get(position).getExplain());
                taskUIToFileUpload.putExtra("마감일",tasks.get(position).getTargetDate().toString());
                taskUIToFileUpload.putExtra("파일 이름",tasks.get(position).getFile());
                TaskUI.thisTaskUI.startActivity(taskUIToFileUpload);
            }
        });
        isTaskComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext.getApplicationContext(), String.valueOf(tasks.get(position).is_complete) , Toast.LENGTH_SHORT).show();
                tasks.get(position).changeCompleteState();
            }
        });
        return view;
    }
}
