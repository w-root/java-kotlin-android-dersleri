package com.example.java_historybookapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.java_historybookapp.databinding.ActivityDeleteBinding;
import com.example.java_historybookapp.databinding.ActivityUpdateBinding;
import com.example.java_historybookapp.models.Art;
import com.example.java_historybookapp.services.ArtService;
import com.example.java_historybookapp.services.RetrofitClient;
import com.example.java_historybookapp.viewmodels.ArtsSingleton;
import com.example.java_historybookapp.viewmodels.ArtsViewModel;
import com.example.java_historybookapp.views.fragments.UpdateFragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class UpdateActivity extends AppCompatActivity {

    ActivityUpdateBinding binding;
    CompositeDisposable disposable;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            binding = ActivityUpdateBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

        retrofit = RetrofitClient.getClient();
        ArtService artService = retrofit.create(ArtService.class);
        disposable = new CompositeDisposable();

        binding.etAuthor.setText(ArtsSingleton.getInstance().getArtList().get(0).getAuthor());
        binding.etDescription.setText(ArtsSingleton.getInstance().getArtList().get(0).getDescription());
        binding.etName.setText(ArtsSingleton.getInstance().getArtList().get(0).getName());
        binding.etYear.setText(ArtsSingleton.getInstance().getArtList().get(0).getYear());
        binding.etLocation.setText(ArtsSingleton.getInstance().getArtList().get(0).getLocation());

            binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = binding.etName.getText().toString();
                    String description = binding.etDescription.getText().toString();
                    String author = binding.etAuthor.getText().toString();
                    String year = binding.etYear.getText().toString();
                    String location = binding.etLocation.getText().toString();

                    Art art = new Art(ArtsSingleton.getInstance().getArtList().get(0).getId(),name,description,author,year,location);
                    disposable.add(artService.update(art.getId(),art)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(response -> {

                            },e -> {}));
                    Intent intent = new Intent(UpdateActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            });

    }
}