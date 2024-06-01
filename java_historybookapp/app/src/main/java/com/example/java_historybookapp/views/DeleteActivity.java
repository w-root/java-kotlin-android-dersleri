package com.example.java_historybookapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.java_historybookapp.R;
import com.example.java_historybookapp.databinding.ActivityDeleteBinding;
import com.example.java_historybookapp.models.Art;
import com.example.java_historybookapp.services.ArtService;
import com.example.java_historybookapp.services.RetrofitClient;
import com.example.java_historybookapp.viewmodels.ArtsSingleton;
import com.example.java_historybookapp.viewmodels.ArtsViewModel;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class DeleteActivity extends AppCompatActivity {

    ActivityDeleteBinding binding;
    ArtsViewModel artsViewModel;
    String str = "";
    CompositeDisposable disposable;
    Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeleteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        retrofit = RetrofitClient.getClient();
        ArtService artService = retrofit.create(ArtService.class);
        disposable = new CompositeDisposable();

        for (Art art: ArtsSingleton.getInstance().getArtList()) {
            str += "Name : "+ art.getName() +"\n" +
            "Description : "+ art.getDescription().substring(0,art.getDescription().length())+"..." +"\n" +
            "Author : "+ art.getAuthor() +"\n" +
            "Year : "+ art.getYear() +"\n" +
            "Location : "+ art.getLocation() +"\n\n\n";
        }

        binding.tvInformations.setText(str);

        binding.etDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Art art: ArtsSingleton.getInstance().getArtList()) {
                    disposable.add(artService.delete(art.getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(response ->{

                            },t -> {
                                System.out.println(t);
                            }));
                    Intent intent = new Intent(DeleteActivity.this,MainActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
}