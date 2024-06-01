package com.example.java_retrofit.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.example.java_retrofit.R;
import com.example.java_retrofit.adapter.RecyclerAdapter;
import com.example.java_retrofit.databinding.ActivityMainBinding;
import com.example.java_retrofit.model.Crypto;
import com.example.java_retrofit.service.CryptoAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    List<Crypto> cryptos;
    private String BASE_URL = "https://raw.githubusercontent.com/";
    Retrofit retrofit;
    RecyclerAdapter adapter;
    CompositeDisposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        loadData();
    }

    private void handleResponse(List<Crypto> cryptoList){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        adapter = new RecyclerAdapter(cryptoList);
        binding.recyclerView.setAdapter(adapter);
    }

    private void loadData(){
        CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class);

        disposable = new CompositeDisposable();

        disposable.add(cryptoAPI.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(MainActivity.this::handleResponse)
        );

//        CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class);
//        Call<List<Crypto>> call = cryptoAPI.getAll();
//
//        call.enqueue(new Callback<List<Crypto>>() {
//            @Override
//            public void onResponse(Call<List<Crypto>> call, Response<List<Crypto>> response) {
//                if(response.isSuccessful()){
//                    cryptos = response.body();
//                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//                    adapter = new RecyclerAdapter(cryptos);
//                    binding.recyclerView.setAdapter(adapter);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Crypto>> call, Throwable t) {
//                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}