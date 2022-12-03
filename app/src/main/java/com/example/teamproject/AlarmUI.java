package com.example.teamproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class AlarmUI extends AppCompatActivity {

    //어댑터 변수 선언
    public static AlramAdapter alramAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_ui);

        Toolbar toolbar_back = (Toolbar) findViewById(R.id.toolbar_back);
        setSupportActionBar(toolbar_back);

        //툴바 로고 글씨 안 보이게 하는 코드
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //툴바 뒤로가기 보이게 하는 코드
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //listview 설정
        ListView listView = (ListView) findViewById(R.id.alram_listview);
        alramAdapter = new AlramAdapter(this,Users.selectedUser.getAllAlram());
        listView.setAdapter(alramAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home: //뒤로가기 버튼 클릭 시 이벤트 처리
                finish();
                break;
        }
        return true;
    }
}
