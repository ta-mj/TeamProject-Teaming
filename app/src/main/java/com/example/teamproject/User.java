package com.example.teamproject;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
public class User{
    private int image;
    private String pw;
    private String name;
    private String email;
    private String phone;
    private ArrayList<TeamProject> myProject;
    private ArrayList<Task> myTask;
    private ArrayList<String> myAlram;
    private ArrayList<MainItem> myItem;
    private ArrayList<ToDo> myTodo;
    //완료된 개인 할 일 숨기기 기능
    private boolean isCompletedToDoHide;
    //private ArrayList<Test> myTest;
    private HashMap<CalendarDay,Test> myTest;
    //project 객체
    //user 생성자
    public User(String p , String n , String e, String ph){
        image = R.drawable.person;
        pw = p;
        name = n;
        email = e;
        phone = ph;
        myProject = new ArrayList<TeamProject>();
        myTask = new ArrayList<>();
        myAlram = new ArrayList<>();
        myItem = new ArrayList<>();
        myTodo = new ArrayList<>();
        myTest = new HashMap<>();
        isCompletedToDoHide = false;
    }
    public String getPW() {
        return pw;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPhoneNum() {
        return phone;
    }
    public int getProjectNum() {
        if(myProject != null){
            return myProject.size();
        }
        return 0;
    }
    public ArrayList<ToDo> getAllToDo(){return myTodo;}
    public TeamProject getProject(int i) { return myProject.get(i); }
    public ArrayList<Task> getAllTask(){ return myTask; }
    public Task getTask(int i){ return myTask.get(i); }
    public ArrayList<String> getAllAlram(){ return myAlram; }
    public String getAlram(int i){ return myAlram.get(i); }
    public ArrayList<MainItem> getAllItem(){ return myItem; }
    public HashMap<CalendarDay, Test> getAllTest(){ return myTest; }
    public MainItem getItem(int i){ return myItem.get(i); }
    public Test getTest(CalendarDay c){ return myTest.get(c); }
    public Boolean isCompletedToDoHide(){ return isCompletedToDoHide; }
    public void addProject(TeamProject t){
        this.myProject.add(t);
    }
    public void removeProject(int i){ this.myProject.remove(i); }
    public void addTask(Task t){ this.myTask.add(t); }
    public void addToDo(ToDo t){ this.myTodo.add(t); }
    public void addTest(CalendarDay c, Test t){ this.myTest.put(c,t); }
    public void removeToDo(int i){ this.myTodo.remove(i); }
    public void removeTask(int i){ this.myTask.remove(i); }
    public void removeTask(Task t){ this.myTask.remove(t); }
    public void addAlram(String s){ this.myAlram.add(s); }
    public void removeAlram(int i){ this.myAlram.remove(i); }
    public void addItem(MainItem i){this.myItem.add(i); }
    public void removeItem(int i){this.myItem.remove(i); }
    public void removeItem(Object o){
        for(int i = 0 ; i < this.myItem.size() ; i++){
            if(myItem.get(i).getData().equals(o)){
                myItem.remove(i);
            }
        }
    }
    public void setCompletedToDoHide(){ isCompletedToDoHide = !isCompletedToDoHide; }
}
