package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.app.DatePickerDialog;

import java.util.Calendar;

public class TaskAdd extends AppCompatActivity{

    private DatePickerDialog datePickerDialog;  //스피너 달력 변수
    private Button dateButton;  //이 버튼을 누르면 스피너 달력이 뜸

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_ui);

        Toolbar toolbar_back = (Toolbar) findViewById(R.id.toolbar_back);
        setSupportActionBar(toolbar_back);

        //툴바 로고 글씨 안 보이게 하는 코드
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //툴바 뒤로가기 보이게 하는 코드
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initDatePicker(); //DatePicker 초기화
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
    }

    private String getTodaysDate() //오늘 날짜를 얻어오는 함수
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()  //DatePicker를 초기화 시키는 함수(버튼 텍스트를 오늘 날짜로 설정해준다)
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());  //스피너 달력에 표시되는 최대 년도, 월, 일을 제한 하는 코드

    }

    private String makeDateString(int day, int month, int year)
    {
        return year + " " + month + "월 " + day + "일    "; //스피너 달력으로 마감일 설정시 버튼에 출력되는 양식, 일 뒤에 띄어쓰기 4칸은 좀 더 이쁘게 출력하기 위함.
    }

    /* private String getMonthFormat(int month)  //원래 복붙했을 당시 코드로 지정한 월을 영어 형식으로 변환해서 출력해주던 메소드. 오류 발생 시 복원용으로 남겨둠.
    {
        if(month == 1)
            return "1월";
        if(month == 2)
            return "2월";
        if(month == 3)
            return "3월";
        if(month == 4)
            return "4월";
        if(month == 5)
            return "5월";
        if(month == 6)
            return "6월";
        if(month == 7)
            return "7월";
        if(month == 8)
            return "8월";
        if(month == 9)
            return "9월";
        if(month == 10)
            return "10월";
        if(month == 11)
            return "11월";
        if(month == 12)
            return "12월";

        //default should never happen
        return "1월";
    } */

    public void openDatePicker(View view) //DatePicker 달력을 보여주는 메소드
    {
        datePickerDialog.show();
    }

}









