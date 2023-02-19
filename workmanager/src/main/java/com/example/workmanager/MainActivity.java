package com.example.workmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.view.View;

import java.time.Duration;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void mAddWork(View view) {

        //添加约束,需要网络的时候会执行
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .build();

        //配置我们的任务
        //一次性执行的任务
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest
                //设置触发条件,定义一些约束
                .Builder(MyWork.class)
                .setConstraints(constraints)
                //指数退避策略,线性增长，每次不成功已2秒的倍速继续重复回调，直到成功为止
                .setBackoffCriteria(BackoffPolicy.LINEAR, Duration.ofSeconds(2))
                //设置tag标签
                .addTag("workRequest1")
                .setInitialDelay(5, TimeUnit.SECONDS).build();
        //任务交给WorkManager队列里
        WorkManager workManager = WorkManager.getInstance(this);
        workManager.enqueue(workRequest);
    }
}