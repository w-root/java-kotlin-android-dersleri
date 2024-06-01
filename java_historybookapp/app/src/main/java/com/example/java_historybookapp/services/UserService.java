package com.example.java_historybookapp.services;

import com.example.java_historybookapp.models.Result;
import com.example.java_historybookapp.models.User;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {


    @POST("login")
    Observable<Result<User>> login(@Body User user);

    @POST("register")
    Observable<Result<User>> register(@Body User user);
}
