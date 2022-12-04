package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalDate;

public class Login extends AppCompatActivity {
    public static Context context_main;
    private Button btn_register,btn_login;
    private EditText id,pw;
    private String id_str,pw_str;
    public User myUser;
    public static int numResume = 0;
    @Override
    protected void onResume() {
        super.onResume();
        //유저 미리 만들어두기
        if(numResume == 0){
            Users.makeUser("taewoo9240","ptwmju2199@","ptwmju2199@","박태우","01020449240","kiatae0722@naver.com");
            Users.makeUser("ckdtlr2000","125125","125125","김창식","01063729793","ckdtlr2000@naver.com");
            Users.makeUser("parkkh9989","125125","125125","박규환","01012345678","parkkh9989@gmail.com");
            Users.makeUser("hyjmex","125125","125125","한단비","01012345678","hyjmex@hanmail.net");
            Users.selectedUser = Users.getUser("taewoo9240");
            TeamProject example = new TeamProject("팀프로젝트1",Users.selectedUser);
            Users.selectedProject.addUser(Users.getUser("ckdtlr2000"));
            Users.selectedProject.addUser(Users.getUser("parkkh9989"));
            Users.selectedProject.addUser(Users.getUser("hyjmex"));
            example.makeTask("1111",Users.getUser("taewoo9240"),"1111", LocalDate.parse("2022-12-01"),"1111");
            example.makeTask("2222",Users.getUser("ckdtlr2000"),"2222", LocalDate.parse("2022-12-02"),"2222");
            example.makeTask("3333",Users.getUser("parkkh9989"),"3333", LocalDate.parse("2022-12-03"),"3333");
            example.makeTask("4444",Users.getUser("hyjmex"),"4444", LocalDate.parse("2022-12-11"),"4444");
            numResume++;
        }
        setContentView(R.layout.activity_login);
        //context 설정
        context_main = this;
        //Intent 설정
        Intent loginToRegister = new Intent(Login.this,Register.class);
        Intent loginToMain = new Intent(Login.this,MainActivity.class);
        //텍스트필드 id로 연결
        id = findViewById(R.id.et_id);
        pw = findViewById(R.id.et_password);
        //버튼 id로 연결 및 클릭 이벤트 처리
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id_str = id.getText().toString();
                pw_str = pw.getText().toString();
                if(Users.Login(id_str,pw_str) == true){
                    Toast.makeText(getApplicationContext(), "로그인 성공!", Toast.LENGTH_SHORT).show();
                    Users.selectedUser = Users.myusers.get(id_str);
                    startActivity(loginToMain);
                }
                else{
                    Toast.makeText(getApplicationContext(), "로그인 실패,,,,", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(loginToRegister); // 액티비티 이동.
            }
        });

    }
}