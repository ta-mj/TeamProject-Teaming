package com.example.teamproject;

//프로젝트 화면(설계도 상 4번째 화면) 관련 .java 파일

import static com.example.teamproject.Users.selectedProject;
import static com.example.teamproject.Users.selectedUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class TeamProjectUI extends AppCompatActivity {
    private GridView gridView;
    private Button addButton;
    private EditText projectNameText;
    private ProjectAdapter projectAdapter;
    private Intent projectUIToProjectInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teamproject_ui);

        Toolbar toolbar_bell = (Toolbar) findViewById(R.id.toolbar_bell);
        setSupportActionBar(toolbar_bell);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        projectUIToProjectInfo = new Intent(TeamProjectUI.this, TeamProjectInformation.class);

        gridView = findViewById(R.id.gridView);
        addButton = findViewById(R.id.addButton);
        projectNameText = findViewById(R.id.projectNameText);

        projectAdapter = new ProjectAdapter();
        //반복문으로 소속된 프로젝트들 가지고 오기
        for(int i = 0 ; i < selectedUser.getProjectNum() ; i++){
            projectAdapter.addItem(new ProjectItem(R.drawable.team , selectedUser.getProject(i)));
        }

        gridView.setAdapter(projectAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedProject = projectAdapter.getItem(i).getProject();
                startActivity(projectUIToProjectInfo);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = projectNameText.getText().toString();
                TeamProject newProject = new TeamProject(text,selectedUser);
                projectAdapter.addItem(new ProjectItem(R.drawable.team,newProject));
                //아이템추가
                projectAdapter.notifyDataSetChanged();
            }
        });
    }

    class ProjectAdapter extends BaseAdapter {
        ArrayList<ProjectItem> items = new ArrayList<ProjectItem>();
        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(ProjectItem singerItem){
            items.add(singerItem);
        }

        @Override
        public ProjectItem getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ProjectViewer singerViewer = new ProjectViewer(getApplicationContext());
            singerViewer.setItem(items.get(i));
            return singerViewer;
        }
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
            case R.id.navigation_notifications: //알림 버튼 클릭 시 이벤트 처리
                Intent teamProjectToAlarm = new Intent (this, AlarmUI.class);
                startActivity(teamProjectToAlarm);
                break;
            case android.R.id.home: //뒤로가기 버튼 클릭 시 이벤트 처리
               finish();
               break;
        }
        return true;
    }
}
