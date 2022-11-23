package com.example.teamproject;

//프로젝트 화면(설계도 상 4번째 화면) 관련 .java 파일

import static com.example.teamproject.Users.selectedProject;
import static com.example.teamproject.Users.selectedUser;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TeamProjectUI extends AppCompatActivity {
    private GridView gridView;
    private Intent projectUIToProjectInfo;

    //플로팅 액션버튼 변수
    private FloatingActionButton fabMain;
    private FloatingActionButton fabAdd;
    private FloatingActionButton fabDelete;

    //플로팅버튼 상태
    private boolean fabMain_status = false;

    //AddProjectDialog에서 프로젝트를 추가할 수 있게 하기 위해서 public static으로 변경
    public static ProjectAdapter projectAdapter;
    public static String text;
    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_teamproject_ui);

        Toolbar toolbar_bell = (Toolbar) findViewById(R.id.toolbar_bell);
        setSupportActionBar(toolbar_bell);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        projectUIToProjectInfo = new Intent(TeamProjectUI.this, TeamProjectInformation.class);

        gridView = findViewById(R.id.gridView);
        fabMain = findViewById(R.id.fabMain);
        fabAdd = findViewById(R.id.fabAdd);
        fabDelete = findViewById(R.id.fabDelete);

       // projectNameText = findViewById(R.id.projectNameText);

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
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(TeamProjectUI.this,"삭제하시겠습니까?",Toast.LENGTH_SHORT).show();
//                selectedUser.removeProject(i);
//                projectAdapter.items.remove(i);
//                projectAdapter.notifyDataSetChanged();
                RemoveProjectDialog removeProjectDialog = new RemoveProjectDialog(TeamProjectUI.this);
                removeProjectDialog.callFunction(i);
                return true;
            }
        });

        //메인 플로팅 버튼 클릭 이벤트 처리
        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFab();
            }
        });

        //추가 플로팅 버튼 클릭 이벤트 처리
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddProjectDialog addProjectDialog = new AddProjectDialog(TeamProjectUI.this);
                addProjectDialog.callFunction();
            }
        });

        //삭제 플로팅 버튼 클릭 이벤트 처리
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //구현 바랍니다.
            }
        });
    }

    //플로팅 액션 버튼 클릭시 애니메이션 효과
    public void toggleFab(){
        if(fabMain_status){
            //플로팅 액션 버튼 닫기
            // 애니메이션 추가
            ObjectAnimator fc_animation = ObjectAnimator.ofFloat(fabAdd, "translationY", 0f);
            fc_animation.start();
            ObjectAnimator fe_animation = ObjectAnimator.ofFloat(fabDelete, "translationY", 0f);
            fe_animation.start();

        }else {
            // 플로팅 액션 버튼 열기
            ObjectAnimator fc_animation = ObjectAnimator.ofFloat(fabAdd, "translationY", -175f);
            fc_animation.start();
            ObjectAnimator fe_animation = ObjectAnimator.ofFloat(fabDelete, "translationY", -325f);
            fe_animation.start();
        }
        // 플로팅 버튼 상태 변경
        fabMain_status = !fabMain_status;
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
