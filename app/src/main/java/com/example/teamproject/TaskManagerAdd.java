package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class TaskManagerAdd extends AppCompatActivity {
    Intent taskManagerAddToTaskAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manager_add);

        taskManagerAddToTaskAdd = new Intent(TaskManagerAdd.this, TaskAdd.class);

        ListView listView = (ListView) findViewById(R.id.listview1);
        final UserAdapter userAdapter = new UserAdapter(this,Users.selectedProject.getAllUser());
        listView.setAdapter(userAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                Toast.makeText(getApplicationContext(),userAdapter.getItem(position).getName(),Toast.LENGTH_SHORT).show();
                TaskAdd.thisTaskAdd.setTaskManger(userAdapter.getItem(position));
                finish();
            }
        });
    }
}