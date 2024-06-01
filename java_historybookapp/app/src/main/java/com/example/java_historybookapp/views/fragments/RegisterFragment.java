package com.example.java_historybookapp.views.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.java_historybookapp.databinding.FragmentRegisterBinding;
import com.example.java_historybookapp.models.User;
import com.example.java_historybookapp.services.RetrofitClient;
import com.example.java_historybookapp.services.UserService;
import com.example.java_historybookapp.viewmodels.UserSingleton;
import com.example.java_historybookapp.views.MainActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


public class RegisterFragment extends Fragment {

    FragmentRegisterBinding binding;
    Retrofit retrofit;
    CompositeDisposable compositeDisposable;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        retrofit = RetrofitClient.getClient();
        compositeDisposable = new CompositeDisposable();
        UserService service = retrofit.create(UserService.class);


        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.etRegisterEmail.getText().toString();
                String password = binding.etRegisterPassword.getText().toString();
                String username = binding.etRegisterUsername.getText().toString();

                User user = new User(username,email,password,"user");
                compositeDisposable.add(service.register(user).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                        response -> {
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            startActivity(intent);
                            UserSingleton.getInstance().setUser(response.getData());
                        },throwable -> {
                            System.out.println(throwable);
                        }
                ));
            }
        });




        return root;

    }
}