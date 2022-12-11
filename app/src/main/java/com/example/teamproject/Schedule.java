package com.example.teamproject;

public class Schedule {
    private String context;
    private EventDecorator decorator;
    public Schedule (String c, EventDecorator d){
        this.context = c;
        this.decorator = d;
        Users.selectedUser.addItem(new MainItem(R.drawable.calendar,c,this));
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
