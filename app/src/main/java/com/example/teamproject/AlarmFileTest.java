package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class AlarmFileTest extends AppCompatActivity {
    private Button fileButton;
    private Button alarmButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fileButton = findViewById(R.id.file_button);
        fileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "파일 버튼 클릭!", Toast.LENGTH_SHORT).show();
            }
        });
        alarmButton = findViewById(R.id.alram_button);
        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "알람ㅅㅁㄷ 버튼 클릭!", Toast.LENGTH_SHORT).show();
            }
        });
        setContentView(R.layout.activity_alarm_file_test);
    }
}