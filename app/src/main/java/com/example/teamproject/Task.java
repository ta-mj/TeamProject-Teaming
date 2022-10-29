package com.example.teamproject;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.Comparator;

public class Task{
    //업무 이름
    private String catecory;
    //담당자
    private User manager;
    //업무 제목
    private String workname;
    //업무 설명
    private String explain;
    //등록일
    private LocalDate startDate;
    //마감일
    private LocalDate targetDate;
    //완료 여부
    boolean is_complete;
    //제출 파일 --> 향후
    Task(String c, User m, String w, LocalDate t, String ex){
        catecory = c;
        manager = m;
        workname = w;
        startDate = LocalDate.now();
        targetDate = t;
        explain = ex;
        is_complete = false;
    }
    String getWorkName(){
        return workname;
    }
    LocalDate getStartDate(){
        return startDate;
    }
    LocalDate getTargetDate(){
        return targetDate;
    }
    String getCatecory() { return catecory;}
    User getManager(){ return manager; }
    String getWorkname(){ return workname; }
    String getExplain(){ return explain; }
    boolean Is_complete(){ return is_complete; }
    void setWorkname(String n) {
        workname = n;
    }
    void setTargetDate(LocalDate d){
        targetDate = d;
    }
    void changeCompleteState(){
        is_complete = !is_complete;
    }


}
class StartDateComparator implements Comparator<Task> {
    public int compare(Task t1, Task t2){
        return t1.getStartDate().compareTo(t2.getStartDate());
    }
}
class TargetDateComparator implements Comparator<Task> {
    public int compare(Task t1, Task t2){
        return t1.getTargetDate().compareTo(t2.getTargetDate());
    }
}
