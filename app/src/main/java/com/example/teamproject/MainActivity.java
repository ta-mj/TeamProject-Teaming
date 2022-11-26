package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private View decorView;
    private int uiOption;
    private Intent mainToProjectUI, mainToPersonUI;
    private Button projectButton,mainButton,personalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar_bell = (Toolbar) findViewById(R.id.toolbar_bell);
        setSupportActionBar(toolbar_bell);

        //타이틀 숨기기
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //인텐트 설정
        mainToProjectUI = new Intent(MainActivity.this,TeamProjectUI.class);
        mainToPersonUI = new Intent(MainActivity.this,PersonUI.class);

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

        //프로젝트 버튼
        projectButton = findViewById(R.id.projectbutton);
        projectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(mainToProjectUI);
            }
        });


        personalButton = findViewById(R.id.personalbutton);
        personalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { startActivity(mainToPersonUI); }
        });

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
            case R.id.navigation_notifications:
                Intent mainToAlarm = new Intent (this, AlarmUI.class);
                startActivity(mainToAlarm);
                break;
        }
        return true;
    }

}