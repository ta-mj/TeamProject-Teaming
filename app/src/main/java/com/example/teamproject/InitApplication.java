package com.example.teamproject;

import android.app.Application;
import android.content.Context;

import androidx.work.WorkManager;

public class InitApplication extends Application {
    public Context mContext;
    @Override
    public void onCreate(){
        super.onCreate();
        //알람 채널 생성
        NotificationHelper.createNotificationChannel(getApplicationContext());
        NotificationHelper.refreshScheduledNotification(getApplicationContext());
    }
}
