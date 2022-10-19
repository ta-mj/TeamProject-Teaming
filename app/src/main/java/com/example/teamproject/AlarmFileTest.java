package com.example.teamproject;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class AlarmFileTest extends AppCompatActivity {
    private Button fileButton;
    private Button alarmButton;
    private Button saveButton;
    private TextView filenameView;
    private File savedFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_file_test);
        filenameView = findViewById(R.id.filename_textview);
        fileButton = findViewById(R.id.file_button);
        fileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "파일 버튼 클릭!", Toast.LENGTH_SHORT).show();
                Intent openfile = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                openfile.addCategory(Intent.CATEGORY_OPENABLE);
                openfile.setType("*/*");
                openfileLauncher.launch(openfile);
            }
        });
        saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(getApplicationContext(),"파일 저장 버튼 클릭!", Toast.LENGTH_SHORT).show();
                Intent savefile = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                savefile.addCategory(Intent.CATEGORY_OPENABLE);
                savefile.setType("*/*");
                savefile.putExtra(Intent.EXTRA_TITLE,filenameView.getText().toString());
                savefileLauncher.launch(savefile);
            }
        });
        alarmButton = findViewById(R.id.alram_button);
        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "알람 버튼 클릭!", Toast.LENGTH_SHORT).show();
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
                        Uri uri = intent.getData();
                        Cursor returnCursor = getContentResolver().query(uri,null,null,null,null);
                        returnCursor.moveToFirst();
                        int nameindex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        savedFile = new File(uri.getPath());
                        filenameView.setText(returnCursor.getString(nameindex));

                    }
                }
            });

    ActivityResultLauncher<Intent> savefileLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>()
            {
                @Override
                public void onActivityResult(ActivityResult result)
                {
                    if (result.getResultCode() == RESULT_OK)
                    {
                        Intent intent = result.getData();
                        Uri uri = intent.getData();
                    }
                }
            });




}