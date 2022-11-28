package com.example.teamproject;
import java.util.ArrayList;
import java.util.Scanner;
public class User {
    private int image;
    private String pw;
    private String name;
    private String email;
    private String phone;
    private ArrayList<TeamProject> myProject = new ArrayList<TeamProject>();
    //project 객체
    //user 생성자
    public User(String p , String n , String e, String ph){
        image = R.drawable.person;
        pw = p;
        name = n;
        email = e;
        phone = ph;
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
    public TeamProject getProject(int i) { return myProject.get(i); }
    public ArrayList<Task> getAllTask(){ return myTask; }
    public Task getTask(int i){ return myTask.get(i); }
    public String printInfo() {
         return "이름" + getName() + "이메일:" + getEmail() + "전화번호:" + getPhoneNum() + "\n";
    }
    public void addProject(TeamProject t){
        this.myProject.add(t);
    }
    public void removeProject(int i){ this.myProject.remove(i); }
    public void addTask(Task t){ this.myTask.add(t); }
    public void removeTask(int i){ this.myTask.remove(i); }
}
