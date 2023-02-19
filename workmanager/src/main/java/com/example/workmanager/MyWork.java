package com.example.workmanager;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWork extends Worker {


    public MyWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    //定义我们的任务
    @NonNull
    @Override
    public Result doWork() {
        SystemClock.sleep(2000);
        Log.d("hu","MyWork");
        return Result.success();
    }
}
