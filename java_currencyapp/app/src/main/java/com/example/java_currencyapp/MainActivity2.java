package com.example.java_currencyapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.java_currencyapp.databinding.ActivityMain2Binding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity2 extends AppCompatActivity {

    ActivityMain2Binding binding;
    FirebaseFirestore firebaseFirestore;
    RecyclerAdapter adapter;
    List<Currency> currencyList = new ArrayList<>();
    Retrofit retrofit;
    CompositeDisposable disposable;
    private static final String BASE_URL = "http://192.168.1.33:3000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseFirestore = FirebaseFirestore.getInstance();

        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        getData();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity2.this));
        adapter = new RecyclerAdapter(currencyList);
        binding.recyclerView.setAdapter(adapter);
    }

    private void handleResponse(List<Currency> cList){
        for (Currency c: cList) {
            currencyList.add(c);
            System.out.println(c);
        }
    }

    public void getData(){
        disposable = new CompositeDisposable();
        CurrencyService service = retrofit.create(CurrencyService.class);

        disposable.add(service.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(MainActivity2.this::handleResponse));

        firebaseFirestore.collection("currencies").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error == null){
                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        Currency currency = new Currency(snapshot.getId(),snapshot.getString("currencyName"),
                                snapshot.getString("type"),snapshot.getDouble("buying"),snapshot.getDouble("selling"),snapshot.getDouble("change"));
                        currencyList.add(currency);

                    }
                    adapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(MainActivity2.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.addCurrency:
                Intent intent = new Intent(MainActivity2.this,MainActivity3.class);
                Singleton.getInstance().setCurrency(null);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}