package com.example.java_workmanager;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class RefreshDatabase extends Worker {

    Context context;
    public RefreshDatabase(Context context, WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @Override
    public Result doWork() {
        Data data = getInputData();
        int number = data.getInt("intkey",0);

        refreshDatabase(number);
        return Result.success();
    }

    private void refreshDatabase(int n){
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.example.java_workmanager",Context.MODE_PRIVATE);
        int number = sharedPreferences.getInt("number",0);
        number += n;
        System.out.println(number);
        sharedPreferences.edit().putInt("number",number).apply();
    }

}
