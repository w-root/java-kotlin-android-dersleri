package com.example.java_historybookapp.views.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.java_historybookapp.databinding.FragmentAddBinding;
import com.example.java_historybookapp.models.Art;
import com.example.java_historybookapp.services.ArtService;
import com.example.java_historybookapp.services.RetrofitClient;
import com.example.java_historybookapp.views.MainActivity;
import com.example.java_historybookapp.views.MainActivity3;
import com.example.java_historybookapp.views.UpdateActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


public class AddFragment extends Fragment {

    FragmentAddBinding binding;
    CompositeDisposable disposable;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        disposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getClient();

        ArtService artService = retrofit.create(ArtService.class);

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.etName.getText().toString();
                String description = binding.etDescription.getText().toString();
                String author = binding.etAuthor.getText().toString();
                String year = binding.etYear.getText().toString();
                String location = binding.etLocation.getText().toString();

                Art art = new Art(0,name,description,author,year,location);
                disposable.add(artService.add(art)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {

                        },e -> {}));
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }


        });

        return root;
    }
}