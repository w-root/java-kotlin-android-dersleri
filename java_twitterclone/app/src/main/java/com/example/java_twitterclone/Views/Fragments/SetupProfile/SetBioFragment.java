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
import com.example.java_twitterclone.databinding.FragmentSetBioBinding;


public class SetBioFragment extends Fragment {

    FragmentSetBioBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSetBioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        binding.btnNextFromBioToUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = SetBioFragmentDirections.actionSetBioFragment2ToSetUsernameFragment2();
                Navigation.findNavController(view).navigate(action);

                ProfileViewModel.getInstance().setBio(binding.etBio.getText().toString());
            }
        });

        return root;

    }



}
