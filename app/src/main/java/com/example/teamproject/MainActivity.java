package com.example.teamproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //drawer navigation
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        //인텐트 설정
        mainToProjectUI = new Intent(MainActivity.this, TeamProjectUI.class);
        mainToPersonUI = new Intent(MainActivity.this, PersonUI.class);

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
            public void onClick(View view) {
                startActivity(mainToPersonUI);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_logout:


                        return true;
                }
                return true;
            }
        });


        //알람 채널 생성
        NotificationHelper.createNotificationChannel(getApplicationContext());
        NotificationHelper.refreshScheduledNotification(getApplicationContext());
        //알람 설정
        setAlram(WorkManager.getInstance(getApplicationContext()));
    }




    public void setAlram(final WorkManager workManager){
        boolean isChannelCreated = NotificationHelper.isNotificationChannelCreated(getApplicationContext());
        if(isChannelCreated){
            PreferenceHelper.setBoolean(getApplicationContext(), Constants.SHARED_PREF_NOTIFICATION_KEY, true);
            NotificationHelper.setScheduledNotification(workManager);
        }
        else{
            NotificationHelper.createNotificationChannel(getApplicationContext());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.bell_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.navigation_notifications:
                Intent mainToAlarm = new Intent (this, AlarmUI.class);
                startActivity(mainToAlarm);
                break;
    }
        if (drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
}