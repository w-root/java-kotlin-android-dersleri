package com.example.java_workmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.os.Bundle;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Data data = new Data.Builder().putInt("intkey",1).build();
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)

                .setRequiresCharging(false)
                .build();


        // BİR KEZ ÇALIŞTIRMA
//        WorkRequest request = new OneTimeWorkRequest.Builder(RefreshDatabase.class)
//                .setConstraints(constraints)
//                .setInputData(data)
//                .setInitialDelay(5, TimeUnit.SECONDS)
//                .addTag("myTag")
//                .build();
//
//        WorkManager.getInstance(this).enqueue(request);


        // ARALIKLI PERİYODİK ÇALIŞTIRMA
        WorkRequest request = new PeriodicWorkRequest.Builder(RefreshDatabase.class,15,TimeUnit.MINUTES)
                .build();
        WorkManager.getInstance(this).enqueue(request);

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(request.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        // O ANKİ DURUMUNU KONTROL ETME
                        if(workInfo.getState() == WorkInfo.State.FAILED){

                        } else if(workInfo.getState() == WorkInfo.State.SUCCEEDED){

                        } else if(workInfo.getState() == WorkInfo.State.RUNNING){

                        }
                    }
                });

        // Chaining -> Sadece bir defa yapılan workrequest ler birbirine bağlanır, periyodik olanlar bağlanmaz
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(RefreshDatabase.class)
                .setInputData(data)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(this).beginWith(oneTimeWorkRequest) // farklı kabul edersek ayrı ayrı çalıştırcak
                .then(oneTimeWorkRequest) // önceki bitmesi gerek
                .then(oneTimeWorkRequest) // önceki bitmesi gerek
                .enqueue();

        // BÜTÜN WORKLERİ İPTAL ETME
        // WorkManager.getInstance(this).cancelAllWork();

    }
}