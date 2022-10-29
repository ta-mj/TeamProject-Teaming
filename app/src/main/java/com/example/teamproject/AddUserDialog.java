package com.example.teamproject;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddUserDialog {

    private Context context;

    public AddUserDialog(Context context) {
        this.context = context;
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction() {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.activity_add_user_dialog);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final EditText message = (EditText) dlg.findViewById(R.id.mesgase);
        final Button okButton = (Button) dlg.findViewById(R.id.okButton);
        final Button cancelButton = (Button) dlg.findViewById(R.id.cancelButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // '추가' 버튼 클릭시 id를 체크하고 이미 추가된 유저인 경우와 없는 유저인 경우 실패 메시지를 , 아닌 경우엔
                // 성공했다는 메시지와 함께 유저를 추가한다.
                String id = message.getText().toString();
                if(Users.findUser(id)){
                    User u = Users.getUser(id);
                    if(Users.selectedProject.findUser(u)){
                        Toast.makeText(context, "검색하신 유저가 이미 존재합니다.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Users.selectedProject.addUser(u);
                        Toast.makeText(context, "유저 추가가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(context, "찾으려는 유저가 없습니다.", Toast.LENGTH_SHORT).show();
                }
                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "취소 했습니다.", Toast.LENGTH_SHORT).show();

                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });
    }
}