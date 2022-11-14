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
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;


public class FileUpload extends AppCompatActivity{
    //변수 설정
    private EditText manager,deadline,workname,explain;
    private Uri file;
    private Button fileUploadButton,confirmButton;

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
        manager.setText(getIntent().getStringExtra("담당자"));
        deadline.setText(getIntent().getStringExtra("마감일"));
        workname.setText(getIntent().getStringExtra("업무 제목"));
        explain.setText(getIntent().getStringExtra("설명"));
        fileUploadButton = findViewById(R.id.fileUploadButton);
        if(getIntent().getParcelableExtra("파일 이름") != null) {
            Uri uri = getIntent().getParcelableExtra("파일 이름");
            Cursor returnCursor = getContentResolver().query(uri, null, null, null, null);
            returnCursor.moveToFirst();
            int nameindex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            fileUploadButton.setText(returnCursor.getString(nameindex));
        }
        fileUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openfile = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                openfile.addCategory(Intent.CATEGORY_OPENABLE);
                openfile.setType("*/*");
                openfileLauncher.launch(openfile);

            }
        });
        confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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
