package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class TaskAdd extends AppCompatActivity{

    private Intent taskAddToTaskMangerAdd;
    //private Intent taskAddToTaskUI; //확인 버튼을 누르면 finish();가 실행되므로 필요없음
    private EditText catecoryText, taskNameText, explainText;
    private Button managerAddButton,confirmButton;
    private DatePickerDialog datePickerDialog;  //스피너 달력 변수S
    private Button dateButton;  //이 버튼을 누르면 스피너 달력이 뜸
    private String catecory,taskName,explain,date;
    private User taskManger = null;
    public static TaskAdd thisTaskAdd;

    private View decorView;
    private int uiOption;

    public void setTaskManger(User u){
        taskManger = u;
        managerAddButton.setText(taskManger.getName());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_add);

        thisTaskAdd = this;

        Toolbar toolbar_back = (Toolbar) findViewById(R.id.toolbar_back);
        setSupportActionBar(toolbar_back);

        //툴바 로고 글씨 안 보이게 하는 코드
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //툴바 뒤로가기 보이게 하는 코드
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Intent 설정
        taskAddToTaskMangerAdd = new Intent(TaskAdd.this, TaskManagerAdd.class);
        //taskAddToTaskUI = new Intent(TaskAdd.this, TaskManagerAdd.class);  //확인 버튼을 누르면 finish();가 실행되므로 필요없음

        //View 들 id로 연결
        catecoryText = findViewById(R.id.catecoryField);
        taskNameText = findViewById(R.id.taskNameField);
        explainText = findViewById(R.id.taskExplainField);

        //담당자 설정
        managerAddButton = findViewById(R.id.taskManagerAddButton);
        managerAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(taskAddToTaskMangerAdd);
                Toast.makeText(getApplicationContext(),"두번째 액티비티 종료",Toast.LENGTH_SHORT);
            }
        });


        //마감일 설정
        initDatePicker(); //DatePicker 초기화
        dateButton = findViewById(R.id.calendarButton);
        dateButton.setText(getTodaysDate());

        confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                catecory = catecoryText.getText().toString();
                taskName = taskNameText.getText().toString();
                explain = explainText.getText().toString();
                date = dateButton.getText().toString();
                if(catecory != "" && taskManger != null && taskName != "" && explain != ""){
                    LocalDate deadline = LocalDate.parse(date);
                    Users.selectedProject.makeTask(catecory,taskManger,taskName,deadline,explain);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"업무 정보를 정확히 입력해주십시오.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //하단 네비게이션바를 숨겨주는 코드(하단을 쓸어올리거나 상단을 쓸어내리면 다시 나옴)
        decorView = getWindow().getDecorView();
        uiOption = getWindow().getDecorView().getSystemUiVisibility();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        decorView.setSystemUiVisibility( uiOption );
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
        if(day >= 10 && month >= 10){
            return year + "-" + month + "-" + day; //스피너 달력으로 마감일 설정시 버튼에 출력되는 양식, 일 뒤에 띄어쓰기 4칸은 좀 더 이쁘게 출력하기 위함.
        }
        else if(day >= 10 && month < 10){
            return year + "-0" + month + "-" + day;
        }
        else if(day < 10 && month >= 10){
            return year + "-" + month + "-0" + day;
        }
        else{
            return year + "-0" + month + "-0" + day; //스피너 달력으로 마감일 설정시 버튼에 출력되는 양식, 일 뒤에 띄어쓰기 4칸은 좀 더 이쁘게 출력하기 위함.
        }
    }

    public void openDatePicker(View view) //DatePicker 달력을 보여주는 메소드
    {
        datePickerDialog.show();
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
ㅅㅁ            return "5월";
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









