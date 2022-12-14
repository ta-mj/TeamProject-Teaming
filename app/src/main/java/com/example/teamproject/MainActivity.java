package com.example.teamproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
    private Intent mainToLogin,mainToProjectUI,mainToTaskUI, mainToPersonUI, mainToTeamCalender, mainToPersonCalender;
    private Button projectButton,mainButton,personalButton;
    private SearchView main_search;
    private ArrayList<MainItem> selectedItem;

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
            Toast.makeText(getApplicationContext(),"?????? ?????? ??????",Toast.LENGTH_SHORT).show();
            NavigationMenuItemView logout = findViewById(R.id.item_logout);
            NavigationMenuItemView unregister = findViewById(R.id.item_unregister);
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                    dlg.setTitle("????????????");
                    dlg.setMessage("???????????? ???????????????????");
                    dlg.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dlg.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Users.selectedUser = null;
                            mainAdapter = null;
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
                    dlg.setTitle("?????? ??????");
                    dlg.setMessage("????????? ?????? ???????????????????");
                    dlg.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dlg.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //??? ????????? ?????? ?????? ????????? ??????
                            Users.unRegister(Users.selectedUser);
                            mainAdapter = null;
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
        //?????? ????????? mainlist??? ???????????? ????????? ????????? ??????????????? textview??? ???????????? ???.
        if(mainAdapter == null){
            //?????? ??????
            NotificationHelper.createNotificationChannel(getApplicationContext());
            NotificationHelper.refreshScheduledNotification(getApplicationContext());
            //setAlram(WorkManager.getInstance(getApplicationContext()));
        }
        if(Users.selectedUser.getAllItem().size() == 0){
            textView.setVisibility(View.VISIBLE);
        }
        else{
            //listview ??????
            ListView listView = (ListView) findViewById(R.id.main_listview);
            if(mainAdapter == null) {
                mainAdapter = new MainAdapter(this, Users.selectedUser.getAllItem());
            }
            else{
                mainAdapter.setItems(Users.selectedUser.getAllItem());
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
                            break;
                        case R.drawable.calendar:
                            if( item.getContent().charAt(0) == '???'){
                                startActivity(mainToTeamCalender);
                                mainAdapter.notifyDataSetChanged();
                            }else{
                                startActivity(mainToPersonCalender);
                                mainAdapter.notifyDataSetChanged();
                            }
                            break;

                    }
                }
            });
        }
        selectedItem = new ArrayList<>();
        //?????? ??? ??????
        main_search = findViewById(R.id.main_search);
        main_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s.length() == 0){
                    mainAdapter.setItems(Users.selectedUser.getAllItem());
                }
                else{
                    mainAdapter.setItems(Users.selectedUser.getAllItem());
                    selectedItem.clear();
                    for(int i = 0 ; i < mainAdapter.getItems().size() ; i++){
                        MainItem m = mainAdapter.getItem(i);
                        if(m.getContent().contains(s)){
                            selectedItem.add(m);
                        }
                    }
                    mainAdapter.setItems(selectedItem);
                }
                mainAdapter.notifyDataSetChanged();
                return false;
            }
        });

        //????????? ?????????
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //drawer navigation
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle =  new ActionBarDrawerToggle (this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //????????? ??????
        mainToLogin = new Intent(MainActivity.this,Login.class);
        mainToProjectUI = new Intent(MainActivity.this,TeamProjectUI.class);
        mainToTaskUI = new Intent(MainActivity.this,TaskUI.class);
        mainToPersonUI = new Intent(MainActivity.this,PersonUI.class);
        mainToTeamCalender = new Intent(MainActivity.this, CalendarTeam.class);
        mainToPersonCalender = new Intent(MainActivity.this, CalendarPerson.class);

        //?????? ????????????????????? ???????????? ??????(????????? ?????????????????? ????????? ??????????????? ?????? ??????)
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

        //???????????? ??????
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
            //???????????? ?????? ?????? ?????? ??? ????????? ????????????
            //super.onBackPressed();
        }
    }
}