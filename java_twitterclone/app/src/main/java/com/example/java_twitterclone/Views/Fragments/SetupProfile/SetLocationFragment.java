package com.example.java_twitterclone.Views.Fragments.SetupProfile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.java_twitterclone.ViewModels.ProfileViewModel;
import com.example.java_twitterclone.databinding.FragmentSetLocaitonBinding;


public class SetLocationFragment extends Fragment {

    FragmentSetLocaitonBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSetLocaitonBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnNextFromLocationToSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = SetLocationFragmentDirections.actionSetLocationFragmentToSaveSetupProfileFragment2();
                Navigation.findNavController(view).navigate(action);
                ProfileViewModel.getInstance().setLocation(binding.etLocation.getText().toString());
            }
        });
        return root;

    }



}
