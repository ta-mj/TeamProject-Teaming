package com.example.teamproject;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.Comparator;

public class Task{
    //업무 이름
    private String workname;
    //담당자
    private User manager;
    //업무 설명
    private String explain;
    //등록일
    private LocalDate startDate;
    //마감일
    private LocalDate targetDate;
    //완료 여부
    boolean is_complete;
    //제출 파일 --> 향후
    @RequiresApi(api = Build.VERSION_CODES.O)
    Task(String n, User m, LocalDate t, String ex){
        workname = n;
        manager = m;
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
