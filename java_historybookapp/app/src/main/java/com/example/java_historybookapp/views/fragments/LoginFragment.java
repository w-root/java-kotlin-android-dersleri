package com.example.java_historybookapp.views.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.java_historybookapp.databinding.FragmentLoginBinding;
import com.example.java_historybookapp.models.User;
import com.example.java_historybookapp.services.RetrofitClient;
import com.example.java_historybookapp.services.UserService;
import com.example.java_historybookapp.viewmodels.UserSingleton;
import com.example.java_historybookapp.views.MainActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class LoginFragment extends Fragment {

    CompositeDisposable compositeDisposable;
    Retrofit retrofit;
    FragmentLoginBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        retrofit = RetrofitClient.getClient();
        compositeDisposable = new CompositeDisposable();
        UserService service = retrofit.create(UserService.class);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.etUsername.getText().toString();
                String password = binding.etPassword.getText().toString();

                User user = new User(username,"",password,"");
                compositeDisposable.add(service.login(user).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
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