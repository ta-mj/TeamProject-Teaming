package com.example.teamproject;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class TaskWorker extends Worker {
    public TaskWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork(){
        NotificationHelper mNotificationHelper = new NotificationHelper(getApplicationContext());
        return Result.success();
    }

}
