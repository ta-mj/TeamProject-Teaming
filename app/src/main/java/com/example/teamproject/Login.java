package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    public static Context context_main;
    private Button btn_register,btn_login;
    private EditText id,pw;
    private String id_str,pw_str;
    public User myUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //유저 미리 만들어두기
        Users.makeUser("taewoo9240","ptwmju2199@","ptwmju2199@","박태우","01020449240","kiatae0722@naver.com");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //context 설정
        context_main = this;
        //Intent 설정
        Intent loginToRegister = new Intent(Login.this,Register.class);
        //잠시 테스트를 위해 testactivity로 intent 변경
        Intent loginToMain = new Intent(Login.this,AlarmFileTest.class);
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