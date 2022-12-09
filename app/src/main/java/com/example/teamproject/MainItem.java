package com.example.teamproject;

import android.content.Intent;
import android.os.Parcelable;

import java.io.Serializable;

public class MainItem implements Serializable {
    private int image;
    private String content;
    private Object data;//실제 데이터와 연결
    public MainItem(int i, String c,Object o){
        this.image = i;
        this.content = c;
        data = o;
    }
    public void setImage(int i){ this.image = i;}
    public void setContent(String c){ this.content = c;}
    public void setData(Object o){ this.data = o;}
    public int getImage(){ return this.image; }
    public String getContent(){ return this.content; }
    public Object getData(){ return this.data; }

}
