package com.example.teamproject;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class AlarmUI extends AppCompatActivity {

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
    }
}
