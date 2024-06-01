package com.example.java_historybookapp.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.java_historybookapp.R;
import com.example.java_historybookapp.databinding.FragmentDeleteBinding;
import com.example.java_historybookapp.databinding.FragmentUpdateBinding;


public class DeleteFragment extends Fragment {

    FragmentDeleteBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDeleteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
}