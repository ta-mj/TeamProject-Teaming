package com.example.teamproject;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.nio.file.Path;


public class FileUpload extends AppCompatActivity{
    //변수 설정
    private EditText manager,deadline,workname,explain;
    private Uri file;
    private Button fileUploadButton,confirmButton;

    private View decorView;
    private int uiOption;

    private Task selected_task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_upload);

        Toolbar toolbar_back = (Toolbar) findViewById(R.id.toolbar_back);
        setSupportActionBar(toolbar_back);

        //툴바 로고 안 보이게 하는 코드
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //툴바 뒤로가기 보이게 하는 코드
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //view들 id로 연결
        manager = findViewById(R.id.fileManagerText);
        deadline = findViewById(R.id.fileDeadLineText);
        workname = findViewById(R.id.fileWorkNameText);
        explain = findViewById(R.id.fileExplainText);
        selected_task = (Task)getIntent().getSerializableExtra("선택업무");
        manager.setText(selected_task.getManager().getName());
        deadline.setText(selected_task.getTargetDate().toString());
        workname.setText(selected_task.getWorkName());
        explain.setText(selected_task.getExplain());
        fileUploadButton = findViewById(R.id.fileUploadButton);
        file = selected_task.getFile();
        if(file != null) {
            Cursor returnCursor = getContentResolver().query(file, null, null, null, null);
            returnCursor.moveToFirst();
            int nameindex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            fileUploadButton.setText(returnCursor.getString(nameindex));
        }
        else{
            Toast.makeText(getApplicationContext(),"첨부파일 is null",Toast.LENGTH_SHORT).show();
        }
        fileUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Users.selectedUser.getName().equals(manager.getText().toString())) {
                    Intent openfile = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    openfile.addCategory(Intent.CATEGORY_OPENABLE);
                    openfile.setType("*/*");
                    openfileLauncher.launch(openfile);
                }

            }
        });
        confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected_task.setFile(file);
                selected_task.setExplain(explain.getText().toString());
                selected_task.setWorkName(workname.getText().toString());
                finish();
            }
        });

        //하단 네비게이션바를 숨겨주는 코드(하단을 쓸어올리거나 상단을 쓸어내리면 다시 나옴)
        decorView = getWindow().getDecorView();
        uiOption = getWindow().getDecorView().getSystemUiVisibility();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        decorView.setSystemUiVisibility( uiOption );
    }
    ActivityResultLauncher<Intent> openfileLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>()
            {
                @Override
                public void onActivityResult(ActivityResult result)
                {
                    if (result.getResultCode() == RESULT_OK)
                    {
                        Intent intent = result.getData();
                        file = intent.getData();
                        if(file != null){
                            selected_task.setFile(file);
                        }
                        Cursor returnCursor = getContentResolver().query(file,null,null,null,null);
                        returnCursor.moveToFirst();
                        int nameindex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        fileUploadButton.setText(returnCursor.getString(nameindex));

                    }
                }
            });
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
