package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    private Button btn_register;
    public static Context context_register;
    private EditText id,pw,checkpw,name,phone,email;
    private String[] userInfo = new String[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context_register = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //EtitText 정보
        id = findViewById(R.id.et_id);
        pw = findViewById(R.id.et_password);
        checkpw = findViewById(R.id.et_password_nu);
        name = findViewById(R.id.et_name);
        phone = findViewById(R.id.et_phone);
        email = findViewById(R.id.et_email);

        //회원가입 버튼
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInfo[0] = id.getText().toString();
                userInfo[1] = pw.getText().toString();
                userInfo[2] = checkpw.getText().toString();
                userInfo[3] = name.getText().toString();
                userInfo[4] = phone.getText().toString();
                userInfo[5] = email.getText().toString();
                int isMakeSuccess = Users.makeUser(userInfo[0],userInfo[1],userInfo[2],userInfo[3],userInfo[4],userInfo[5]);
                if(isMakeSuccess == -1){
                    builder.setTitle("알림");
                    builder.setMessage("입력하신 아이디가 존재합니다.");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog idDuplicate = builder.create();
                    idDuplicate.show();

                }
                else if(isMakeSuccess == -2){
                    builder.setTitle("알림");
                    builder.setMessage("비밀번호가 같지 않습니다.");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog pwNotCorrect = builder.create();
                    pwNotCorrect.show();
                }
                else if(isMakeSuccess == -3){
                    builder.setTitle("알림");
                    builder.setMessage("정보를 정확히 입력해 주십시오.");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog blankExist = builder.create();
                    blankExist.show();
                }
                else{
                    builder.setTitle("알림");
                    builder.setMessage("회원가입에 성공하였습니다..");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            finish();
                        }
                    });
                    AlertDialog success = builder.create();
                    success.show();
                }
            }
        });
    }
}