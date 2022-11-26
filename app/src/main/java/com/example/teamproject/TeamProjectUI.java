package com.example.teamproject;

//프로젝트 화면(설계도 상 4번째 화면) 관련 .java 파일
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Locale;

public class TeamProjectUI extends AppCompatActivity {
    private GridView gridView;
    private Intent projectUIToProjectInfo;
    private SearchView project_search;

    //플로팅 액션버튼 변수
    private FloatingActionButton fabAdd;
    //플로팅버튼 상태
    //private boolean fabMain_status = false;

    //AddProjectDialog에서 프로젝트를 추가할 수 있게 하기 위해서 public static으로 변경
    public static ProjectAdapter projectAdapter;
    public static String text;

    //projectAdapter에 들어갈 arrayList
    private ArrayList<ProjectItem> allItem = new ArrayList<>();
    private ArrayList<ProjectItem> selectedItem = new ArrayList<>();//검색했을 때 조건에 맞는 아이템만 넣기 위한 변수
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
        fabAdd = findViewById(R.id.fabAdd);
        project_search = findViewById(R.id.project_search);

        // projectNameText = findViewById(R.id.projectNameText);
        try{
            projectAdapter = new ProjectAdapter();
            //반복문으로 소속된 프로젝트들 가지고 오기
            for(int i = allItem.size() ; i < Users.selectedUser.getProjectNum() ; i++){
                allItem.add(new ProjectItem(R.drawable.team,Users.selectedUser.getProject(i)));
            }
            projectAdapter.items = allItem;
            projectAdapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), String.valueOf(allItem.size()) ,Toast.LENGTH_SHORT).show();
        }catch(NullPointerException e){
            Toast.makeText(getApplicationContext(),"실패..",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        gridView.setAdapter(projectAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Users.selectedProject = projectAdapter.getItem(i).getProject();
                startActivity(projectUIToProjectInfo);
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(TeamProjectUI.this,"삭제하시겠습니까?",Toast.LENGTH_SHORT).show();
                RemoveProjectDialog removeProjectDialog = new RemoveProjectDialog(TeamProjectUI.this);
                removeProjectDialog.callFunction(i);
                return true;
            }
        });

        //추가 플로팅 버튼 클릭 이벤트 처리
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //toggleFab();
                AddProjectDialog addProjectDialog = new AddProjectDialog(TeamProjectUI.this);
                addProjectDialog.callFunction();
            }
        });
        //검색 기능
        project_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //엔터키 눌렸을때
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            //실시간으로 내용 바뀔때
            @Override
            public boolean onQueryTextChange(String s) {
                //문자 입력시마다 리스트 새로 뿌려주기
                Context c = getApplicationContext();
                int len = Toast.LENGTH_SHORT;
                if(s.equals("")){
                    projectAdapter.items = allItem;
                    Toast.makeText(c,String.valueOf(allItem.size()),len).show();
                }
                else{
                    int cor = 0;
                    selectedItem.clear();
                    for(int i = 0 ; i < allItem.size(); i++){
                        if(allItem.get(i).getProject().getSubject().contains(s)){
                           selectedItem.add(allItem.get(i));
                            cor++;
                        }
                    }
                    Toast.makeText(c,String.valueOf(cor),len).show();
                    projectAdapter.items = selectedItem;
                }
                projectAdapter.notifyDataSetChanged();
                return false;
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
