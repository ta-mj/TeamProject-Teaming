package com.example.teamproject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
public class TeamProject {
    //프로젝트 정보
    private String subject;
    //구성원
    private ArrayList<User> myUser = new ArrayList<>();
    //업무
    private LinkedList<Task> myTask = new LinkedList<>();
    //브레인스토밍
    //일정
    //알림
    TeamProject(String s, User u){
        subject = s;
        myUser.add(u);
        Users.selectedProject = this;
    }
    public void setSubject(String s) {
        subject = s;
    }
    public void addUser(User u) {
        myUser.add(u);
    }
    public String getSubject() { return this.subject; }
    public int getUserNum() { return this.myUser.size(); }
    public void makeTask(String n, User m, LocalDate t, String ex){
        Task newTask = new Task(n,m,t,ex);
        myTask.add(newTask);
    }
    public void sortTaskByStartDate() {
        myTask.sort(new StartDateComparator());
    }
    public void sortTaskByTargetDate() {
        myTask.sort(new TargetDateComparator());
    }
}
