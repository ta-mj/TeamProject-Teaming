package com.example.teamproject;

//업무분담 화면(설계도 상 7번째 화면) 관련 .java 파일

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;


public class TaskUI extends AppCompatActivity{
    ListView listview;
    ArrayList<String> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_ui);

        Toolbar toolbar_bell = (Toolbar) findViewById(R.id.toolbar_bell);
        setSupportActionBar(toolbar_bell);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        items = new ArrayList<String>();
        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_single_choice,items);
        listview = findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        Button btn_add = (Button)findViewById(R.id.button);
        btn_add.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                int count = adapter.getCount();//아이템 추가
                items.add("List"+Integer.toString(count +1));
            }
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
                return true;
        }
        return true;
    }
}
