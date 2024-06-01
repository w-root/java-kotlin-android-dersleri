package com.example.java_historybookapp.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.java_historybookapp.R;
import com.example.java_historybookapp.adapters.RecyclerAdapter;
import com.example.java_historybookapp.databinding.ActivityMainBinding;
import com.example.java_historybookapp.models.Art;
import com.example.java_historybookapp.services.ArtService;
import com.example.java_historybookapp.services.RetrofitClient;
import com.example.java_historybookapp.viewmodels.ArtsSingleton;
import com.example.java_historybookapp.viewmodels.ArtsViewModel;
import com.example.java_historybookapp.viewmodels.UserSingleton;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    List<Art> artList = new ArrayList<Art>();
    List<Art> filteredList = new ArrayList<Art>();
    CompositeDisposable disposable;
    ArtsViewModel artsViewModel;
    MenuItem item = null;
    
    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        artsViewModel = new ViewModelProvider(this).get(ArtsViewModel.class);

        Retrofit retrofit = RetrofitClient.getClient();
        disposable = new CompositeDisposable();
        ArtService artService = retrofit.create(ArtService.class);

        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.cities));
        binding.spinner.setAdapter(spinnerAdapter);

//        disposable.add(artService.getAll()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(MainActivity.this::handleResponse));

        Call<List<Art>> call = artService.getAllCall();
        call.enqueue(new Callback<List<Art>>() {
            @Override
            public void onResponse(Call<List<Art>> call, Response<List<Art>> response) {

                binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                artList = response.body();
                RecyclerAdapter adapter = new RecyclerAdapter(   response.body(),artsViewModel);
                adapter.notifyDataSetChanged();
                binding.recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Art>> call, Throwable t) {

            }
        });

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String city = getResources().getStringArray(R.array.cities)[i];

                if(getResources().getStringArray(R.array.cities)[i].equals("Seçiniz")){
                    RecyclerAdapter adapter = new RecyclerAdapter(artList,artsViewModel);
                    binding.recyclerView.setAdapter(adapter);
                } else {
                    for (Art art: artList) {
                        if(art.getLocation()==city){
                            System.out.println("aaaaaaaaaaaaaaaaa");
                        }
                        if(art.getLocation().equals("Paris")){
                            filteredList.add(art);
                        }
                    }
                    for (Art art:filteredList
                         ) {
                        System.out.println("cccccc : "+art.getLocation());
                    }
                    RecyclerAdapter adapter = new RecyclerAdapter(filteredList,artsViewModel);
                    binding.recyclerView.setAdapter(adapter);

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        artsViewModel.getArray().observe(MainActivity.this, new Observer<ArrayList<Art>>() {
            @Override
            public void onChanged(ArrayList<Art> arts) {
                ArtsSingleton.getInstance().setArtList(arts);

                if(arts.size() > 1){
                    item.setEnabled(false);
                    item.setIcon(R.drawable.ic_baseline_edit_24s);
                } else {
                    item.setEnabled(true);
                    item.setIcon(R.drawable.ic_baseline_edit_24);
                }
            }
        });
    }

    private void handleResponse(List<Art> arts) {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        artList = arts;
        RecyclerAdapter adapter = new RecyclerAdapter(arts,artsViewModel);
        adapter.notifyDataSetChanged();
        binding.recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(UserSingleton.getInstance().getUser().getRole().equals("admin")){
            getMenuInflater().inflate(R.menu.menu_main,menu);
            item = menu.findItem(R.id.updateArt);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.addArt:
                Intent intent = new Intent(MainActivity.this,MainActivity3.class);
                startActivity(intent);
                break;
            case R.id.deleteArt:
                Intent intent2 = new Intent(MainActivity.this,DeleteActivity.class);
                startActivity(intent2);
                break;
            case R.id.updateArt:
                Intent intent3 = new Intent(MainActivity.this,UpdateActivity.class);
                startActivity(intent3);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}