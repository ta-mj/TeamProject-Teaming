package com.example.teamproject;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddTodoDialog extends AppCompatActivity {
    private Context context;

    public AddTodoDialog(){};
    public AddTodoDialog(Context context){this.context = context;}
    public void Call_Function(){

        final Dialog dlg = new Dialog(context);

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dlg.setContentView(R.layout.activity_add_todo_dialog);

        dlg.show();

        final EditText message = (EditText) dlg.findViewById(R.id.todomessage);
        final Button okButton = (Button) dlg.findViewById(R.id.todo_okbutton);
        final Button cancelButton = (Button) dlg.findViewById(R.id.todo_cancelbutton);

       okButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String text = message.getText().toString();
               ToDo t = new ToDo(text);
               Users.selectedUser.addToDo(t);
               Users.selectedUser.addItem(new MainItem(R.drawable.ic_outline_checklist_24,text,t));
               PersonUI.personAdapter.notifyDataSetChanged();
               Toast.makeText(context, "할일 추가가 완료되었습니다.", Toast.LENGTH_SHORT).show();
               dlg.dismiss();
               PersonUI.thisPersonUI.onResume();
           }
       });

       cancelButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(context, "취소 했습니다.", Toast.LENGTH_SHORT).show();
               dlg.dismiss();
           }
       });





    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo_dialog);
    }
}