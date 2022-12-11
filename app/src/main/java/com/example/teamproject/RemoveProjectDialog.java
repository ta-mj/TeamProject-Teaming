package com.example.teamproject;

import static com.example.teamproject.TeamProjectUI.text;
import static com.example.teamproject.Users.selectedProject;
import static com.example.teamproject.Users.selectedUser;

import android.app.Dialog;
import android.content.Context;
import android.telephony.TelephonyCallback;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RemoveProjectDialog extends AppCompatActivity {
    private Context context;
    //private TeamProjectUI.ProjectAdapter projectAdapter;

    public RemoveProjectDialog(){}
    public RemoveProjectDialog(Context context) {
        this.context = context;
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction(int i) {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.activity_remove_project_dialog);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final TextView message = (TextView) dlg.findViewById(R.id.project_rm_message);
        final Button okButton = (Button) dlg.findViewById(R.id.project_rm_okButton);
        final Button cancelButton = (Button) dlg.findViewById(R.id.project_rm_cancelButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // '추가' 버튼 클릭시 성공했다는 메시지와 함께 프로젝트를 추가한다.
                text = message.getText().toString();

                Toast.makeText(context, "프로젝트 삭제가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                for(int j = 0 ; j < selectedProject.getUserNum() ; j++){
                    if(selectedProject.getOneUser(j).equals(selectedUser)){
                        Toast.makeText(context,selectedProject.getOneUser(j).getName(),Toast.LENGTH_SHORT).show();
                        //회원 탈퇴
                        selectedProject.removeUser(j);
                        //해당 유저의 업무 삭제
                        for(int k = 0 ; k < selectedProject.getAllTask().size() ; k++){
                            Task t = selectedProject.getOneTask(k);
                            if(t.getManager().equals(selectedUser)) {
                                selectedProject.getAllTask().remove(k);
                                selectedUser.removeTask(t);
                                k--;
                            }
                        }
                    }
                }
                Users.selectedUser.removeProject(i);
                TeamProjectUI.projectAdapter.items.remove(i);
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
