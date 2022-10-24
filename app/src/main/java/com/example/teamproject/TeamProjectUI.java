package com.example.teamproject;

//프로젝트 화면(설계도 상 4번째 화면) 관련 .java 파일

import static com.example.teamproject.Users.selectedUser;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class TeamProjectUI extends AppCompatActivity {
    private GridView gridView;
    private Button addButton;
    private EditText projectNameText;
    private ProjectAdapter projectAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teamproject);

        //view들 id로 연결
        Toolbar toolbar = findViewById(R.id.toolbar);
        GridView gridView = findViewById(R.id.gridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"팀프로젝트 : "+ projectAdapter.getItem(i).getText().toString(), Toast.LENGTH_LONG).show();

            }
        });
        projectNameText = findViewById(R.id.projectNameText);
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String projectName = projectNameText.getText().toString();
                projectAdapter.addItem(new ProjectItem(projectName,R.drawable.team));
                projectAdapter.notifyDataSetChanged();
            }
        });


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
    class ProjectAdapter extends BaseAdapter {
        ArrayList<ProjectItem> items = new ArrayList<ProjectItem>();
        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(ProjectItem singerItem){
            items.add(singerItem);
        }

        @Override
        public ProjectItem getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ProjectViewer singerViewer = new ProjectViewer(getApplicationContext());
            singerViewer.setItem(items.get(i));
            return singerViewer;
        }
    }
}
