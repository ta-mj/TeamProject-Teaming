package com.example.teamproject;

import static android.view.View.GONE;

import android.app.Person;
import android.content.Intent;
import android.os.Build;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class PersonUI extends AppCompatActivity {
    //하단 네비게이션바를 숨기기 위한 변수
//    private View decorView;
//    private int uiOption;
    private ListView listView;
    private FloatingActionButton fab_toDoAdd;
    public static PersonAdapter personAdapter;
    public static PersonUI thisPersonUI;

    private Button person_toDoButton, person_scheduleButton;
    private Intent personUIToCalendar;
    //검색 관련 변수
    private SearchView to_do_search;
    private ArrayList<ToDo> selectedToDo;

    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_person_ui);

        //intent 설정 -> 팀 일정으로 테스트함
        //personUIToCalendar = new Intent(PersonUI.this,Calendar.class);

        Toolbar toolbar_bell = (Toolbar) findViewById(R.id.toolbar_bell);
        setSupportActionBar(toolbar_bell);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView textView = findViewById(R.id.noToDoList);

        if(Users.selectedUser.getAllToDo().size() == 0){
            textView.setVisibility(View.VISIBLE);
        }
        else{
            textView.setVisibility(View.GONE);
        }
        thisPersonUI = this;
        fab_toDoAdd = findViewById(R.id.fab_ToDoAdd);
        person_scheduleButton = findViewById(R.id.person_schedulebutton);

        //인텐트 설정
        personUIToCalendar = new Intent(PersonUI.this, CalendarPerson.class);

        ListView listview = (ListView) findViewById(R.id.person_listview);

        personAdapter = new PersonAdapter(this,Users.selectedUser.getAllToDo());

        listview.setAdapter(personAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),personAdapter.getItem(position).getTodoname(),Toast.LENGTH_SHORT).show();
            }
        });

        //검색 뷰 설정
        to_do_search = findViewById(R.id.to_do_search);
        selectedToDo = new ArrayList<>();
        to_do_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s.length() == 0){
                    personAdapter.setAllToDo(Users.selectedUser.getAllToDo());
                }
                else{
                    personAdapter.setAllToDo(Users.selectedUser.getAllToDo());
                    selectedToDo.clear();
                    for(int i = 0 ; i < personAdapter.getAllToDo().size() ; i++){
                        ToDo todo = personAdapter.getAllToDo().get(i);
                        if(todo.getTodoname().contains(s)){
                            selectedToDo.add(todo);
                        }
                    }
                    personAdapter.setAllToDo(selectedToDo);
                }
                personAdapter.notifyDataSetChanged();
                return false;
            }
        });


        //추가 플로팅 버튼 클릭 이벤트 처리
        fab_toDoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTodoDialog addtododialog= new AddTodoDialog(PersonUI.this);
                addtododialog.Call_Function();
            }
        });

        person_scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(personUIToCalendar);
            }
        });

    }

    //할 일 삭제 함수
    public void removeToDo(int position){
        Toast.makeText(getApplicationContext(),"삭제하시겠습니까",Toast.LENGTH_SHORT).show();
        RemoveToDoDialog removeToDoDialog = new RemoveToDoDialog(PersonUI.this);
        removeToDoDialog.CallFunction(position);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.todo_sort_menu, menu);
        MenuItem item = menu.findItem(R.id.hideCompletedToDo);
        if(item != null){
            if(Users.selectedUser.isCompletedToDoHide()){
                hideCompletedToDo();
                item.setTitle("완료된 할 일 보이기");
            }
            else{
                item.setTitle("완료된 할 일 숨기기");
            }
        }

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.hideCompletedToDo: // 완료된 아이템 숨기기/보이기 클릭 시 이벤트 처리
                Users.selectedUser.setCompletedToDoHide();
                if(Users.selectedUser.isCompletedToDoHide()){
                    item.setTitle("완료된 할 일 보이기");
                }
                else{
                    item.setTitle("완료된 할 일 숨기기");
                }
                hideCompletedToDo();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
    public void hideCompletedToDo(){
        personAdapter.setAllToDo(Users.selectedUser.getAllToDo());
        selectedToDo.clear();
        if(Users.selectedUser.isCompletedToDoHide()) {
            for (int i = 0; i < personAdapter.getAllToDo().size(); i++) {
                ToDo todo = personAdapter.getAllToDo().get(i);
                if(!todo.IsComplete()){
                    selectedToDo.add(todo);
                }
            }
            personAdapter.setAllToDo(selectedToDo);
        }
        else{

        }
        personAdapter.notifyDataSetChanged();
    }



}
