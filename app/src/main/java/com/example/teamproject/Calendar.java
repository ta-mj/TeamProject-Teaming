package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;


import java.io.FileInputStream;
import java.io.FileOutputStream;


public class Calendar extends AppCompatActivity {
    public String readDay = null;  //선택한 일자 읽어오는 변수
    public String str = null; //EditText에 입력되는 내용을 저장하는 변수
    public CalendarView calendarView; //캘린더뷰 변수
    public Button changeButton, deleteButton, saveButton; //수정, 삭제, 저장 버튼
    public TextView diaryTextView, contentTextView; //diaryTextView = 선택한 날짜를 표시해줌, contentTextView = 일정 내용(str변수의 값)을 저장하는 변수
    public EditText contextEditText; //입력 창 관련 변수

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Toolbar toolbar_back = (Toolbar) findViewById(R.id.toolbar_back);
        setSupportActionBar(toolbar_back);

        //툴바 로고 안 보이게 하는 코드
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //툴바 뒤로가기 보이게 하는 코드
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //캘린더뷰 관련 변수들
        calendarView = findViewById(R.id.calendarView);
        diaryTextView = findViewById(R.id.diaryTextView);
        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton);
        changeButton = findViewById(R.id.changeButton);
        contentTextView = findViewById(R.id.contentTextView);
        //textView3 = findViewById(R.id.textView3);
        contextEditText = findViewById(R.id.contextEditText);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) //일자 클릭 시 일자를 받아 처리하는 메소드
            {
                diaryTextView.setVisibility(View.VISIBLE);
                saveButton.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
                contentTextView.setVisibility(View.INVISIBLE);
                changeButton.setVisibility(View.INVISIBLE);
                deleteButton.setVisibility(View.INVISIBLE);
                diaryTextView.setText(String.format("%d / %d / %d", year, month + 1, dayOfMonth));
                contextEditText.setText("");
                checkDay(year, month, dayOfMonth);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() //저장 버튼을 누르면 입력한 내용을 해당 일자에 저장하는 메소드 / 저장 버튼 클릭 이벤트 처리
        {
            @Override
            public void onClick(View view)
            {
                saveDiary(readDay);
                str = contextEditText.getText().toString();
                contentTextView.setText(str);
                saveButton.setVisibility(View.INVISIBLE);
                changeButton.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.INVISIBLE);
                contentTextView.setVisibility(View.VISIBLE);

            }
        });
    }

    public void checkDay(int cYear, int cMonth, int cDay) //선택한 일자를 readDay 변수에 저장하는 메소드
    {
        readDay = "" + cYear + "-" + (cMonth + 1) + "" + "-" + cDay + ".txt";
        FileInputStream fis;

        try
        {
            fis = openFileInput(readDay);

            byte[] fileData = new byte[fis.available()];
            fis.read(fileData);
            fis.close();

            str = new String(fileData);

            contextEditText.setVisibility(View.INVISIBLE);
            contentTextView.setVisibility(View.VISIBLE);
            contentTextView.setText(str);

            saveButton.setVisibility(View.INVISIBLE);
            changeButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);

            changeButton.setOnClickListener(new View.OnClickListener()  //수정 버튼을 누르면 입력한 내용을 불러와 수정할 수 있게 해주는 메소드 / 수정 버튼 클릭 이벤트 처리
            {
                @Override
                public void onClick(View view)
                {
                    contextEditText.setVisibility(View.VISIBLE);
                    contentTextView.setVisibility(View.INVISIBLE);
                    contextEditText.setText(str);

                    saveButton.setVisibility(View.VISIBLE);
                    changeButton.setVisibility(View.INVISIBLE);
                    deleteButton.setVisibility(View.INVISIBLE);
                    contentTextView.setText(contextEditText.getText());
                }

            });
            deleteButton.setOnClickListener(new View.OnClickListener() //삭제 버튼을 누르면 해당 일자에 저장된 일정을 삭제해주는 메소드 / 삭제 버튼 클릭 이벤트 처리
            {
                @Override
                public void onClick(View view)
                {
                    contentTextView.setVisibility(View.INVISIBLE);
                    contextEditText.setText("");
                    contextEditText.setVisibility(View.VISIBLE);
                    saveButton.setVisibility(View.VISIBLE);
                    changeButton.setVisibility(View.INVISIBLE);
                    deleteButton.setVisibility(View.INVISIBLE);
                    removeDiary(readDay);
                }
            });
            if (contentTextView.getText() == null)
            {
                contentTextView.setVisibility(View.INVISIBLE);
                diaryTextView.setVisibility(View.VISIBLE);
                saveButton.setVisibility(View.VISIBLE);
                changeButton.setVisibility(View.INVISIBLE);
                deleteButton.setVisibility(View.INVISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
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

    @SuppressLint("WrongConstant")
    public void removeDiary(String readDay) //선택한 일자에 저장한 내용을 삭제하는 메소드
    {
        FileOutputStream fos;
        try
        {
            fos = openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS);
            String content = "";
            fos.write((content).getBytes());
            fos.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @SuppressLint("WrongConstant")
    public void saveDiary(String readDay) //선택한 일자에 입력 내용을 저장하는 메소드
    {
        FileOutputStream fos;
        try
        {
            fos = openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS);
            String content = contextEditText.getText().toString();
            fos.write((content).getBytes());
            fos.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

}