package com.example.teamproject;

import static android.graphics.Color.RED;
import static android.os.Build.VERSION_CODES.O;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;
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
            public void onClick(View view) {//ㅎ
                Toast.makeText(getApplicationContext(), "파일 버튼 클릭!", Toast.LENGTH_SHORT).show();
                Intent openfile = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                openfile.addCategory(Intent.CATEGORY_OPENABLE);
                openfile.setType("*/*");
                openfileLauncher.launch(openfile);
            }
        });
        saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "파일 저장 버튼 클릭!", Toast.LENGTH_SHORT).show();
                Intent savefile = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                savefile.addCategory(Intent.CATEGORY_OPENABLE);
                savefile.setType("*/*");
                savefile.putExtra(Intent.EXTRA_TITLE, filenameView.getText().toString());
                savefileLauncher.launch(savefile);
            }
        });
        alarmButton = findViewById(R.id.alram_button);
        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification();
            }
        });
        createNotificationChannel();
    }

    public void createNotificationChannel() {
        mNotificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, "TESTNOTIFICIATION", mNotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Mascot");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private NotificationCompat.Builder getNotificationBuilder() {
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("알림")
                .setContentText("마감 기한이 얼마 안남았습니다.")
                .setSmallIcon(R.drawable.ic_notifications_black_24dp);//이미지 에셋 설정 --> file - new - imageasset으로 가 ic_android 설정해 newt - finish할것
        return notifyBuilder;
    }

    public void sendNotification() {
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        mNotificationManager.notify(NOTIFICATION_ID, notifyBuilder.build());
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