package com.example.teamproject;

import static com.example.teamproject.TeamProjectUI.text;
import static com.example.teamproject.Users.selectedUser;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddProjectDialog extends AppCompatActivity {
    private Context context;
    //private TeamProjectUI.ProjectAdapter projectAdapter;

    public AddProjectDialog(){}
    public AddProjectDialog(Context context) {
        this.context = context;
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction() {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.activity_add_project_dialog);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final EditText message = (EditText) dlg.findViewById(R.id.project_mesgase);
        final Button okButton = (Button) dlg.findViewById(R.id.project_okButton);
        final Button cancelButton = (Button) dlg.findViewById(R.id.project_cancelButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // '추가' 버튼 클릭시 성공했다는 메시지와 함께 프로젝트를 추가한다.
                text = message.getText().toString();

                Toast.makeText(context, "프로젝트 추가가 완료되었습니다.", Toast.LENGTH_SHORT).show();

                TeamProject newProject = new TeamProject(text,selectedUser);
                TeamProjectUI.projectAdapter.addItem(new ProjectItem(R.drawable.team,newProject));
                //아이템추가
                TeamProjectUI.projectAdapter.notifyDataSetChanged();
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
