package com.example.teamproject;

//프로젝트 화면(설계도 상 4번째 화면) 관련 .java 파일

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
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
        setContentView(R.layout.activity_teamproject_ui);

        Toolbar toolbar_bell = (Toolbar) findViewById(R.id.toolbar_bell);
        setSupportActionBar(toolbar_bell);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gridView = findViewById(R.id.gridView);
        addButton = findViewById(R.id.addButton);
        projectNameText = findViewById(R.id.projectNameText);

        projectAdapter = new ProjectAdapter();
        projectAdapter.addItem(new ProjectItem("팀프로젝트", R.drawable.team));

        gridView.setAdapter(projectAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"팀프로젝트 : "+ projectAdapter.getItem(i).getText().toString(), Toast.LENGTH_LONG).show();

            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = projectNameText.getText().toString();
                projectAdapter.addItem(new ProjectItem(text,R.drawable.team));
                //아이템추가
                projectAdapter.notifyDataSetChanged();
            }
        });
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
