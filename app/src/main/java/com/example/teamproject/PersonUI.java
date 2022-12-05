package com.example.teamproject;

import android.content.Intent;
import android.os.Build;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class PersonUI extends AppCompatActivity {
    //하단 네비게이션바를 숨기기 위한 변수
//    private View decorView;
//    private int uiOption;

    //플로팅 액션버튼
    private FloatingActionButton fab_toDoAdd;

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

/*
        //추가 플로팅 버튼 클릭 이벤트 처리
        fab_toDoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity();
            }
        }); */
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
            case android.R.id.home: //뒤로가기 버튼 클릭 시 이벤트 처리
                finish();
                break;
        }
        return true;
    }
}
