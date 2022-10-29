package com.example.teamproject;

//프로젝트 정보 화면(설계도 상 5번째 화면) 관련 .java 파일

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class TeamProjectInformation extends AppCompatActivity {
    //변수 선언
    private Button taskbutton,brainstromingbutton,personalbutton;
    private ProgressBar teamprogress;
    private Intent projectInfoToTaskUI;
    private TextView[] memberView;
    private ProgressBar[] memberProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teamproject_information);

        //Intent 설정
        projectInfoToTaskUI = new Intent(TeamProjectInformation.this,TaskUI.class);

        //id로 view 연결
        Toolbar toolbar_bell = (Toolbar) findViewById(R.id.toolbar_bell);
        setSupportActionBar(toolbar_bell);
        taskbutton = findViewById(R.id.project_taskbutton);
        brainstromingbutton = findViewById(R.id.project_brainstromingbutton);
        personalbutton = findViewById(R.id.project_schedulebutton);
        teamprogress = (ProgressBar) findViewById((R.id.team_progressbar));

        memberView = new TextView[4];
        memberView[0] = findViewById(R.id.member0ProgressView);
        memberView[1] = findViewById(R.id.member1ProgressView);
        memberView[2] = findViewById(R.id.member2ProgressView);
        memberView[3] = findViewById(R.id.member3ProgressView);

        memberProgressBar = new ProgressBar[4];
        memberProgressBar[0] = findViewById(R.id.member0_progressbar);
        memberProgressBar[1] = findViewById(R.id.member1_progressbar);
        memberProgressBar[2] = findViewById(R.id.member2_progressbar);
        memberProgressBar[3] = findViewById(R.id.member3_progressbar);


        //멤버 수만큼 progressbar visibillity 설정
        for(int i  = 0 ; i < Users.selectedProject.getUserNum() ; i++){
            memberView[i].setText(Users.selectedProject.getMyUser(i).getName());
            memberProgressBar[i].setVisibility(View.VISIBLE);
        }

        //button onclickevent 설정
        taskbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(projectInfoToTaskUI);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.navigation_notifications:
                Intent teamProjectInformationToAlarm = new Intent (this, AlarmUI.class);
                startActivity(teamProjectInformationToAlarm);

            case R.id.navigation_team_add: //플러스 버튼 클릭 시 이벤트 처리
                // Intent teamProjectToTeamAdd = new Intent(this, -----.class);  //팀원 추가 액티비티와 연결할 것
                // startActivity(teamProjectToTeamAdd);
        }
        return true;
    }
}
