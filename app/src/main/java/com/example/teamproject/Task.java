package com.example.teamproject;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;

public class Task implements Serializable {
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
    private boolean is_complete;
    //제출 파일
    private Uri file = null;
    Task(String c, User m, String w, LocalDate t, String ex){
        catecory = c;
        manager = m;
        workname = w;
        startDate = LocalDate.now();
        targetDate = t;
        explain = ex;
        is_complete = false;
        m.addTask(this);
        m.addItem(new MainItem(R.drawable.file,this.workname,this));
    }
    public String getWorkName(){
        return workname;
    }
    public LocalDate getStartDate(){
        return startDate;
    }
    public LocalDate getTargetDate(){
        return targetDate;
    }
    public String getCatecory() { return catecory;}
    public User getManager(){ return manager; }
    public String getExplain(){ return explain; }
    public Uri getFile(){ return file; }
    public boolean IsComplete(){ return is_complete; }
    public void setWorkName(String n) {
        workname = n;
    }
    public void setStartDate(LocalDate d) { startDate = d; }
    public void setTargetDate(LocalDate d){
        targetDate = d;
    }
    public void setCatecory(String c){ catecory = c;}
    public void setExplain(String e){ explain = e;}
    public void setFile(Uri u){ file = u; }
    public void changeCompleteState(){
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

