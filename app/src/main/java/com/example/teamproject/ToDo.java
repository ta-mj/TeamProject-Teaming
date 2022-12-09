package com.example.teamproject;

import java.util.ArrayList;

public class ToDo {
    private String todoname;
    boolean iscomplete;
    private ArrayList<ToDo> mytodo = new ArrayList<>();
    ToDo(String t){
        todoname = t;
        iscomplete = false;
    }
    public String getTodoname(){return todoname;}
    public void setTodoname(String todoname){this.todoname = todoname;}
    public void changecompletestate(){iscomplete = !iscomplete;}
}
