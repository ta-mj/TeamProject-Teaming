package com.example.teamproject;


import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class TeamProject{
    //프로젝트 정보
    private String subject;
    //구성원
    private ArrayList<User> myUser;
    //업무
    private ArrayList<Task> myTask;
    //브레인스토밍
    //일정
    private HashMap<CalendarDay,Schedule> teamSchedule;
    //알림
    //완료 업무 숨기기 기능
    private boolean isCompletedTaskHide;
    TeamProject(String s, User u){
        subject = s;
        myUser = new ArrayList<>();
        myTask = new ArrayList<>();
        myUser.add(u);
        Users.selectedUser.addProject(this);
        Users.selectedProject = this;
        teamSchedule = new HashMap<>();
        isCompletedTaskHide = false;
    }
    public void setSubject(String s) {
        subject = s;
    }
    public void addUser(User u) {
        myUser.add(u);
        u.addProject(this);
    }
    public void removeUser(int i){
        myUser.remove(i);
    }
    public void removeUser(User u){ myUser.remove(u); }
    public void setCompletedTaskHide(){
        this.isCompletedTaskHide = !isCompletedTaskHide;
    }
    public String getSubject() { return this.subject; }
    public ArrayList<User> getAllUser(){ return this.myUser;}
    public ArrayList<Task> getAllTask(){ return this.myTask;}
    public HashMap<CalendarDay, Schedule> getAllTeamSchedule(){ return teamSchedule; }
    public Schedule getTeamSchedule(CalendarDay c){ return teamSchedule.get(c); }
    public void addTeamSchedule(CalendarDay c, Schedule t){ this.teamSchedule.put(c,t); }
    public User getOneUser(int i){ return this.myUser.get(i); }
    public Task getOneTask(int i){ return this.myTask.get(i); }
    public int getUserNum() { return this.myUser.size(); }
    public void makeTask(String c, User m, String w,LocalDate t, String ex){
        Task newTask = new Task(c,m,w,t,ex);
        myTask.add(newTask);
    }
    public boolean findUser(User u){
        return myUser.contains(u);
    }
    public boolean isCompletedTaskHide(){ return this.isCompletedTaskHide; }
    public void sortTaskByStartDate() {
        myTask.sort(new StartDateComparator());
    }
    public void sortTaskByTargetDate() {
        myTask.sort(new TargetDateComparator());
    }
}
