package com.example.teamproject;

import java.io.Serializable;
import java.util.ArrayList;

public class ToDo{
    private String todoname;
    private boolean is_complete;
    private ArrayList<ToDo> mytodo = new ArrayList<>();
    ToDo(String t){
        todoname = t;
        is_complete = false;
    }
    public String getTodoname(){return todoname;}
    public void setTodoname(String todoname){this.todoname = todoname;}
    public void changecompletestate(){is_complete = !is_complete;}
    public Boolean IsComplete(){ return is_complete; }
}
