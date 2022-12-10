package com.example.teamproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.work.WorkManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.time.Duration;

import com.google.android.material.internal.NavigationMenuItemView;
import com.google.android.material.navigation.NavigationView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

    private View decorView;
    private int uiOption;
    private Intent mainToLogin,mainToProjectUI,mainToTaskUI, mainToPersonUI, mainToTeamCalender;
    private Button projectButton,mainButton,personalButton;

    public static MainAdapter mainAdapter;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_notifications:
                Intent mainToAlarm = new Intent(this, AlarmUI.class);
                startActivity(mainToAlarm);
                break;
        }
        if (drawerToggle.onOptionsItemSelected(item))
        {
            Toast.makeText(getApplicationContext(),"설정 메뉴 눌림",Toast.LENGTH_SHORT).show();
            NavigationMenuItemView logout = findViewById(R.id.item_logout);
            NavigationMenuItemView unregister = findViewById(R.id.item_unregister);
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                    dlg.setTitle("로그아웃");
                    dlg.setMessage("로그아웃 하시겠습니까?");
                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Users.selectedUser = null;
                            startActivity(mainToLogin);
                        }
                    });
                    dlg.show();
                }
            });
            unregister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                    dlg.setTitle("회원 탈퇴");
                    dlg.setMessage("정말로 탈퇴 하시겠습니까?");
                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //이 유저에 관한 모든 데이터 삭제
                            Users.unRegister(Users.selectedUser);
                            startActivity(mainToLogin);
                        }
                    });
                    dlg.show();
                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_main);

        Toolbar toolbar_bell = (Toolbar) findViewById(R.id.toolbar_bell);
        setSupportActionBar(toolbar_bell);

        TextView textView = findViewById(R.id.noItemText);
        //현재 유저의 mainlist가 비어있지 않다면 내용을 추가하라는 textview을 안보이게 함.
        if(Users.selectedUser.getAllItem().size() == 0){
            textView.setVisibility(View.VISIBLE);
        }
        else{
            //listview 설정
            ListView listView = (ListView) findViewById(R.id.main_listview);
            if(mainAdapter == null) {
                mainAdapter = new MainAdapter(this, Users.selectedUser.getAllItem());
            }
            else{
                mainAdapter.members = Users.selectedUser.getAllItem();
            }
            listView.setAdapter(mainAdapter);
            textView.setVisibility(View.GONE);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MainItem item = mainAdapter.getItem(position);
                    switch (item.getImage()){
                        case R.drawable.file:
                            startActivity(mainToTaskUI);
                            mainAdapter.notifyDataSetChanged();
                            break;
                        case R.drawable.ic_outline_checklist_24:
                            startActivity(mainToPersonUI);
                            mainAdapter.notifyDataSetChanged();
                        case R.drawable.calendar:
                            startActivity(mainToTeamCalender);
                            mainAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        //타이틀 숨기기
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //drawer navigation
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle =  new ActionBarDrawerToggle (this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //인텐트 설정
        mainToLogin = new Intent(MainActivity.this,Login.class);
        mainToProjectUI = new Intent(MainActivity.this,TeamProjectUI.class);
        mainToTaskUI = new Intent(MainActivity.this,TaskUI.class);
        mainToPersonUI = new Intent(MainActivity.this,PersonUI.class);
        mainToTeamCalender = new Intent(MainActivity.this, Calendar.class);

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
            public void onClick(View view) { startActivity(mainToPersonUI); }
        });

        //알람 설정
        //setAlram(WorkManager.getInstance(getApplicationContext()));

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
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            //로그아웃 했을 때만 나갈 수 있도록 막아놓음
            //super.onBackPressed();
        }
    }
}