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
    public static TeamProject selectedProject;
    private static String selectedUserID = null;

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
            selectedUserID = id;
            return true;
        }
        else{
            return false;
        }
    }
    public static boolean findUser(String id){
        return myusers.containsKey(id);
    }
    public static User getUser(String id) {
        return myusers.get(id);
    }
    public static String getUserID(){
        return selectedUserID;
    }
    //회원 탈퇴 함수
    public static void unRegister(User u){
        //1. 유저가 소속된 프로젝트에서 유저 및 유저가 담당한  업무 삭제
        for(int i = 0; i < u.getProjectNum() ; i++){
            TeamProject p = u.getProject(i);
            for(int j = 0 ; j < p.getAllTask().size() ; j++){
                if(p.getOneTask(j).getManager().equals(u)){
                    p.getAllTask().remove(p.getOneTask(j));
                    j--;
                }
            }
            p.removeUser(u);
        }
        //2. 유저를 유저 목록에서 삭제
        if(myusers.containsValue(u)){
            myusers.values().remove(u);
        }
        //3. 유저 데이터 삭제
        u = null;
    }
}

