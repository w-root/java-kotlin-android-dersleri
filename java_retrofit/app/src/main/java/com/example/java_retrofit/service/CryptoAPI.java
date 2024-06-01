package com.example.java_retrofit.service;

import com.example.java_retrofit.model.Crypto;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoAPI {

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    Observable<List<Crypto>> getAll();

    //Call<List<Crypto>> getAll();
}
