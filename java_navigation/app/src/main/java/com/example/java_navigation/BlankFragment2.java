package com.example.java_navigation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class BlankFragment2 extends Fragment {

    public BlankFragment2() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_blank2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button button = view.findViewById(R.id.button2);
        TextView textView = view.findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFirst(view);
            }
        });

        if(getArguments() != null){
            // int age = getArguments().getInt("age");
            int age = BlankFragment2Args.fromBundle(getArguments()).getAge();
            textView.setText("Yaşınız : " + age);
        }
    }

    public void goToFirst(View view) {
        NavDirections action = BlankFragment2Directions.actionBlankFragment2ToBlankFragment();

        Navigation.findNavController(view).navigate(action);
    }


}