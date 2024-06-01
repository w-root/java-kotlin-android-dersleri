package com.example.java_fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);    FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        SecondFragment secondFragment = new SecondFragment();
        fragmentTransaction.replace(R.id.frameLayout,secondFragment).commit();
    }

    public void goToSecond(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        SecondFragment secondFragment = new SecondFragment();
        fragmentTransaction.replace(R.id.frameLayout,secondFragment).commit();
    }

    public void goToFirst(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FirstFragment firstFragment = new FirstFragment();
        fragmentTransaction.replace(R.id.frameLayout,firstFragment).commit();

    }
}