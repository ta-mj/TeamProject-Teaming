package com.example.teamproject;
import java.util.ArrayList;
import java.util.Scanner;
public class User {
    private String pw;
    private String name;
    private String email;
    private String phone;
    private ArrayList<TeamProject> myProject = new ArrayList<TeamProject>();
    //project 객체
    //user 생성자
    public User(String p , String n , String e, String ph){
        pw = p;
        name = n;
        email = e;
        phone = ph;
    }
    public String getPW() {
        return pw;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPhoneNum() {
        return phone;
    }
    public String printInfo() {
         return "이름" + getName() + "이메일:" + getEmail() + "전화번호:" + getPhoneNum() + "\n";
    }
    public TeamProject makeProject() {
        Scanner sc = new Scanner(System.in);
        System.out.println("프로젝트명을 입력하세요.");
        TeamProject newProject = new TeamProject(sc.nextLine(),this);
        return newProject;
    }

}
