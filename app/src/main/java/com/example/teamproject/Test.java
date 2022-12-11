package com.example.teamproject;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.example.teamproject.CalendarPerson.EventDecorator;

public class Test {
    private String context;
    private EventDecorator decorator;
    public Test(String c, EventDecorator d){
        this.context = c;
        this.decorator = d;
    }

    public String getContext(){
        return this.context;
    }
    public EventDecorator getDecorator(){
        return this.decorator;
    }
    public void setContext(String c){
        this.context = c;
    }
}
