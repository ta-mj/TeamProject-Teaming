package com.example.teamproject;

//업무분담 화면(설계도 상 7번째 화면) 관련 .java 파일

import static com.example.teamproject.TeamProjectUI.projectAdapter;

import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.widget.SearchView;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.arch.core.internal.FastSafeIterableMap;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TaskUI extends AppCompatActivity{

    //변수 설정
    private FloatingActionButton taskAddButton;
    private SearchView task_search;
    private Intent taskUIToTaskAdd;
    private ArrayList<Task> selectedTask = new ArrayList<>();

    //플로팅 액션버튼 변수
    private FloatingActionButton fab_TaskAdd;

    public static TaskAdapter taskAdapter;
    public static TaskUI thisTaskUI;

    //완료된 아이템 보이는지 안보이는지의 상태를 나타내는 변수
    private static boolean isCompletedItemHide = false;
    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_task_ui);

        Toolbar toolbar_bell = (Toolbar) findViewById(R.id.toolbar_bell);
        setSupportActionBar(toolbar_bell);

        //타이틀 숨기기
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        thisTaskUI = this;

        //플로팅 액션 버튼 연결
        fab_TaskAdd = findViewById(R.id.fab_TaskAdd);

        //visibility
        TextView textView = (TextView) findViewById(R.id.textVisible);

        //Intent 설정
        taskUIToTaskAdd = new Intent(TaskUI.this,TaskAdd.class);

        //listview 설정
        ListView listView = (ListView) findViewById(R.id.listview1);
        if(taskAdapter == null) {
            taskAdapter = new TaskAdapter(this, Users.selectedProject.getAllTask());

        }
        else{
            taskAdapter.setTasks(Users.selectedProject.getAllTask());
        }
        Toast.makeText(getApplicationContext(),String.valueOf(Users.selectedProject.getAllTask().size()),Toast.LENGTH_SHORT).show();
        listView.setAdapter(taskAdapter);
        textView.setVisibility(View.INVISIBLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                Toast.makeText(getApplicationContext(),taskAdapter.getItem(position).getManager().getName(),Toast.LENGTH_SHORT).show();
                //finish();
            }
        });



        //검색 뷰 설정
        task_search = findViewById(R.id.task_search);
        task_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s.length() == 0){
                    taskAdapter.setTasks(Users.selectedProject.getAllTask());
                }
                else{
                    taskAdapter.setTasks(Users.selectedProject.getAllTask());
                    selectedTask.clear();
                    for(int i = 0 ; i < taskAdapter.getTasks().size() ; i++){
                        Task task = taskAdapter.getTasks().get(i);
                        if(task.getManager().getName().contains(s) || task.getWorkName().contains(s)){
                            selectedTask.add(task);
                        }
                    }
                    taskAdapter.setTasks(selectedTask);
                }
                taskAdapter.notifyDataSetChanged();
                return false;
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //추가 플로팅 버튼 클릭 이벤트 처리
        fab_TaskAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { startActivity(taskUIToTaskAdd); }
        });

    }
    //업무 삭제 다이얼로그 호출 함수
    public void removeTask(int position){
        Toast.makeText(getApplicationContext(),"삭제하시겠습니까",Toast.LENGTH_SHORT).show();
        RemoveTaskDialog removeTaskDialog = new RemoveTaskDialog(TaskUI.this);
        removeTaskDialog.CallFunction(position);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.sort_menu, menu);
        MenuItem item = menu.findItem(R.id.hideCompletedTask);
        if(item != null){
            if(Users.selectedProject.isCompletedTaskHide()){
                hideCompletedTask();
                item.setTitle("완료된 업무 보이기");
            }
            else{
                item.setTitle("완료된 업무 숨기기");
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.navigation_notifications: //알림 버튼 클릭 시 화면 전환
                Intent taskUIToAlarm = new Intent (this, AlarmUI.class);
                startActivity(taskUIToAlarm);
                break;
            case R.id.sortByAddDate: //등록일 순 정렬 클릭 시 이벤트 처리
                Users.selectedProject.sortTaskByStartDate();
                onResume();
                break;
            case R.id.sortByDeadLine: //마감일 순 정렬 클릭 시 이벤트 처리
                Users.selectedProject.sortTaskByTargetDate();
                onResume();
                break;
            case R.id.hideCompletedTask: // 완료된 아이템 숨기기/보이기 클릭 시 이벤트 처리
                Users.selectedProject.setCompletedTaskHide();
                if(Users.selectedProject.isCompletedTaskHide()){
                    item.setTitle("완료된 업무 보이기");
                }
                else{
                    item.setTitle("완료된 업무 숨기기");
                }
                hideCompletedTask();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    //완료 업무 숨기는 함수
    public void hideCompletedTask(){
        taskAdapter.setTasks(Users.selectedProject.getAllTask());
        selectedTask.clear();
        if(Users.selectedProject.isCompletedTaskHide()) {
            for (int i = 0; i < taskAdapter.getTasks().size(); i++) {
                Task task = taskAdapter.getTasks().get(i);
                if(!task.IsComplete()){
                    selectedTask.add(task);
                }
            }
            taskAdapter.setTasks(selectedTask);
        }
        taskAdapter.notifyDataSetChanged();
    }

}
