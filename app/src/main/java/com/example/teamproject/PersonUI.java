package com.example.teamproject;

import android.content.Intent;
import android.os.Build;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class PersonUI extends AppCompatActivity {
    //하단 네비게이션바를 숨기기 위한 변수
//    private View decorView;
//    private int uiOption;
    private ListView listView;
    private FloatingActionButton fab_toDoAdd;
    public static PersonAdapter personadapter;
    public static PersonUI thisPersonUI;

    //private Button person_toDoButton, person_scheduleButton;
    //private Intent personUIToCalendar;

    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_person_ui);

        //intent 설정 -> 팀 일정으로 테스트함
        //personUIToCalendar = new Intent(PersonUI.this,Calendar.class);

        Toolbar toolbar_bell = (Toolbar) findViewById(R.id.toolbar_bell);
        setSupportActionBar(toolbar_bell);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        thisPersonUI = this;
        fab_toDoAdd = findViewById(R.id.fab_ToDoAdd);

        ListView listview = (ListView) findViewById(R.id.person_listview);
        personadapter = new PersonAdapter(this,Users.selectedUser.getAllToDo());

        listview.setAdapter(personadapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),personadapter.getItem(position).getTodoname(),Toast.LENGTH_SHORT).show();
            }
        });


        //하단 네비게이션바를 숨겨주는 코드(하단을 쓸어올리거나 상단을 쓸어내리면 다시 나옴)
//        decorView = getWindow().getDecorView();
//        uiOption = getWindow().getDecorView().getSystemUiVisibility();
//        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
//            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
//            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
//        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
//            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//
//        decorView.setSystemUiVisibility( uiOption );

        //button onclickevent 설정
//        person_scheduleButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(personUIToCalendar);
//            }
//        });


        //추가 플로팅 버튼 클릭 이벤트 처리
        fab_toDoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTodoDialog addtododialog= new AddTodoDialog(PersonUI.this);
                addtododialog.Call_Function();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.todo_sort_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.navigation_notifications: //알림 버튼 클릭 시 이벤트 처리
                Intent personUIToAlarm = new Intent (this, AlarmUI.class);
                startActivity(personUIToAlarm);
                break;
            case R.id.hideCompletedToDo: // 완료된 할 일 숨기기 이벤트 처리
                break;
            case android.R.id.home: //뒤로가기 버튼 클릭 시 이벤트 처리
                finish();
                break;
        }
        return true;
    }
}
