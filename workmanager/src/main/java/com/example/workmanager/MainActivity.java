package com.example.workmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;
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

        //创建一个参数对象
        Data inputData = new Data.Builder()
                .putString("input_data","jack")
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
                //参数传递
                .setInputData(inputData)
                .setInitialDelay(5, TimeUnit.SECONDS).build();
        //任务交给WorkManager队列里
        WorkManager workManager = WorkManager.getInstance(this);
        workManager.enqueue(workRequest);

        //观察任务状态,当任务状态发生改变则会执行
        workManager.getWorkInfoByIdLiveData(workRequest.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                Log.d("ning",workInfo.toString());
                if(workInfo != null && workInfo.getState() == WorkInfo.State.SUCCEEDED){
                    String output_data = workInfo.getOutputData().getString("output_data");
                    Log.d("ning","outputData是: "+output_data);
                }
            }
        });

        //周期性任务
        //周期性任务执行的时间最少不能低于15分钟
        PeriodicWorkRequest workRequest2 = new PeriodicWorkRequest.Builder(MyWork.class,Duration.ofMinutes(15)).build();
        //取消任务
        /*new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                workManager.cancelWorkById(workRequest.getId());
            }
        },2000);*/
    }
}