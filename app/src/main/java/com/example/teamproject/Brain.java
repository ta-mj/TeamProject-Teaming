package com.example.teamproject;

public class Brain {
    //public int headId;
    public String name;
    public Brain(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Idea["+name+"]";
    }
}