package com.example.teamproject;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Users {
    public static HashMap<String,User> myusers = new HashMap<>();
    public static User selectedUser;
    public static int makeUser(String id,String pw,String checkpw,String name,String ph,String email) {
        if(myusers.containsKey(id)) {
            return -1;
        }
        else if(pw.equals(checkpw) != true){
            return -2;
        }
        else if(id.length() == 0|| pw.length() == 0 || checkpw.length() == 0 || name.length() == 0 || ph.length() == 0 || email.length() == 0){
            return -3;
        }
        User newuser =  new User(pw,name,email,ph);
        myusers.put(id, newuser);
        return 1;
    }
    public static boolean Login(String id,String pw) {
        if(myusers.containsKey(id) == true && myusers.get(id).getPW().equals(pw)){
            return true;
        }
        else{
            return false;
        }
    }

}

