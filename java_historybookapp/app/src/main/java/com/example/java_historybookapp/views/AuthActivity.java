package com.example.java_historybookapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.java_historybookapp.R;
import com.example.java_historybookapp.adapters.TabAdapter;
import com.example.java_historybookapp.databinding.ActivityAuthBinding;
import com.example.java_historybookapp.databinding.ActivityMainBinding;
import com.example.java_historybookapp.services.RetrofitClient;
import com.example.java_historybookapp.services.UserService;
import com.google.android.material.tabs.TabLayoutMediator;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;

public class AuthActivity extends AppCompatActivity {

    ActivityAuthBinding binding;
    CompositeDisposable compositeDisposable;
    Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        compositeDisposable = new CompositeDisposable();
        binding.viewPager.setAdapter(new TabAdapter(this));

        new TabLayoutMediator(binding.tabLayout,binding.viewPager,(tab, position) ->{
           switch (position){
               case 0:
                   tab.setText("Giriş Yap");
                    break;
               case 1:
                   tab.setText("Kayıt Ol");
                   break;

           }
        }).attach();

    }
}