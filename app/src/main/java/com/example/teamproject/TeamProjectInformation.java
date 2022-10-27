package com.example.teamproject;

//프로젝트 정보 화면(설계도 상 5번째 화면) 관련 .java 파일

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class TeamProjectInformation extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teamproject_information);

        Toolbar toolbar_bell = (Toolbar) findViewById(R.id.toolbar_bell);
        setSupportActionBar(toolbar_bell);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                return true;
        }
        return true;
    }
}
