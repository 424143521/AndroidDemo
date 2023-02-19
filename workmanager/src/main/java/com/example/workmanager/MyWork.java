package com.example.workmanager;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
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

        //取出数据
        String input_data = getInputData().getString("input_data");
        SystemClock.sleep(2000);
        Log.d("hu","MyWork");

        //任务执行完之后,返回数据
        Data outputData = new Data.Builder()
                .putString("output_data",input_data+"执行成功")
                .build();


        return Result.success(outputData);
    }
}
