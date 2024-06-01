package com.example.java_currencyapp;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface CurrencyService {

    @GET("getData")
    Observable<List<Currency>> getAll();

}
