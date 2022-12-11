package com.example.teamproject;

//프로젝트 정보 화면(설계도 상 5번째 화면) 관련 .java 파일

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class TeamProjectInformation extends AppCompatActivity {
    //변수 선언
    private Button taskButton, brainstormingButton, scheduleButton;
    private FloatingActionButton addUserButton;
    private ProgressBar teamProgress;
    private Intent projectInfoToTaskUI,projectInfoToCalender, projectInfoToBrainstorming;
    private TextView[] memberView;
    private ProgressBar[] memberProgressBar;
    public static TeamProjectInformation thisTeamProjectInformation;
    //알람 관련 변수
    private AlarmManager alarmManager;
    private GregorianCalendar mCalender;

    private NotificationManager notificationManager;
    NotificationCompat.Builder builder;
    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_teamproject_information);

        thisTeamProjectInformation = this;

        //알람 변수 설정
        mCalender = new GregorianCalendar();
        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        //Intent 설정
        projectInfoToTaskUI = new Intent(TeamProjectInformation.this,TaskUI.class);
        projectInfoToCalender = new Intent(TeamProjectInformation.this, Calendar.class);
        projectInfoToBrainstorming = new Intent(TeamProjectInformation.this, Brainstorming.class);

        //id로 view 연결
        Toolbar toolbar_bell = (Toolbar) findViewById(R.id.toolbar_bell);
        setSupportActionBar(toolbar_bell);
        taskButton = findViewById(R.id.project_taskbutton);
        brainstormingButton = findViewById(R.id.project_brainstormingbutton);
        scheduleButton = findViewById(R.id.project_schedulebutton);
        addUserButton = findViewById(R.id.fabTeamAdd);
        teamProgress = (ProgressBar) findViewById((R.id.team_progressbar));

        //툴바 로고 글씨 안 보이게 하는 코드
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //툴바 뒤로가기 보이게 하는 코드
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
            memberView[i].setText(Users.selectedProject.getOneUser(i).getName());
            memberView[i].setVisibility(View.VISIBLE);
            memberProgressBar[i].setVisibility(View.VISIBLE);
        }

        //프로그레스바 진행도 설정
        int totalTask = 0;
        int totalCompleteTask = 0;
        int[] numTask = new int[Users.selectedProject.getUserNum()];
        int[] numCompleteTask = new int[Users.selectedProject.getUserNum()];
        for(int i = 0 ; i < Users.selectedProject.getAllTask().size() ; i++){
            Task thisTask = Users.selectedProject.getAllTask().get(i);
            for(int j = 0 ; j < Users.selectedProject.getUserNum() ; j++){
                if(thisTask.getManager().getName().equals(memberView[j].getText().toString())){
                    numTask[j]++;
                    totalTask++;
                    if(thisTask.IsComplete() == true){
                        numCompleteTask[j]++;
                        totalCompleteTask++;
                    }
                }
            }
        }
        for(int i = 0 ; i < Users.selectedProject.getUserNum() ; i++){
            if(numTask[i] != 0){
                memberProgressBar[i].setProgress((int)(100 * numCompleteTask[i] / numTask[i]));
            }
        }
        if(totalTask != 0){
            teamProgress.setProgress((int)(100 * totalCompleteTask / totalTask));
        }

        //button onclickevent 설정
        taskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(projectInfoToTaskUI);
            }
        });

        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(projectInfoToCalender);
            }
        });

        brainstormingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { startActivity( projectInfoToBrainstorming); }
        });

        //팀원 추가 플로팅 버튼 클릭 이벤트 처리
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddUserDialog addUserDialog = new AddUserDialog(TeamProjectInformation.this);
                addUserDialog.callFunction();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.bell_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.navigation_notifications:
                Intent teamProjectInformationToAlarm = new Intent(this, AlarmUI.class);
                startActivity(teamProjectInformationToAlarm);
                break;

            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

}
