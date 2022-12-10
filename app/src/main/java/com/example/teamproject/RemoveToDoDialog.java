package com.example.teamproject;

import static com.example.teamproject.TeamProjectUI.text;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RemoveToDoDialog extends AppCompatActivity {
    private Context context;

    public RemoveToDoDialog(){}
    public RemoveToDoDialog(Context context){this.context = context;}
    // 호출할 다이얼로그 함수를 정의한다.
    public void CallFunction(int position) {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlgs = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlgs.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlgs.setContentView(R.layout.activity_remove_todo_dialog);

        // 커스텀 다이얼로그를 노출한다.
        dlgs.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final TextView messages = (TextView) dlgs.findViewById(R.id.todo_rm_message1);
        final Button okButtons = (Button) dlgs.findViewById(R.id.todo_rm_okButton1);
        final Button cancelButtons = (Button) dlgs.findViewById(R.id.todo_rm_cancelButton1);

        okButtons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = messages.getText().toString();
                ToDo t = PersonUI.personadapter.getItem(position);
                Users.selectedUser.removeItem(t);
                PersonUI.personadapter.removeTodo(position);
                PersonUI.personadapter.notifyDataSetChanged();
                // 커스텀 다이얼로그를 종료한다.
                Toast.makeText(context, "할 일 삭제가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                dlgs.dismiss();
            }
        });
        cancelButtons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "취소 했습니다.", Toast.LENGTH_SHORT).show();

                // 커스텀 다이얼로그를 종료한다.
                dlgs.dismiss();
            }
        });
    }
}
