package com.example.teamproject;

//업무분담 화면(설계도 상 7번째 화면) 관련 .java 파일

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class TaskUI extends AppCompatActivity{
    //변수 설정
    private Button taskAddButton;
    private Intent taskUIToTaskAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_ui);

        Toolbar toolbar_bell = (Toolbar) findViewById(R.id.toolbar_bell);
        setSupportActionBar(toolbar_bell);

        //Intent 설정
        taskUIToTaskAdd = new Intent(TaskUI.this,TaskAdd.class);

        //View id로 연결
        taskAddButton = findViewById(R.id.taskaddButton);
        taskAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(taskUIToTaskAdd);
            }
        });

        //listview 설정
        ListView listView = (ListView) findViewById(R.id.listview1);
        final TaskAdapter taskAdapter = new TaskAdapter(this,Users.selectedProject.getAllTask());
        listView.setAdapter(taskAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                Toast.makeText(getApplicationContext(),taskAdapter.getItem(position).getManager().getName(),Toast.LENGTH_SHORT).show();
                //finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.bell_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.navigation_notifications:
                return true;
        }
        return true;
    }
}
