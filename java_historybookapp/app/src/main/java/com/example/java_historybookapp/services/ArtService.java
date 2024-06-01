package com.example.java_historybookapp.services;

import com.example.java_historybookapp.models.Art;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ArtService {

    @GET("getAll")
    Observable<List<Art>> getAll();

    @GET("getAll")
    Call<List<Art>> getAllCall();

    @POST("add")
    Observable<Art> add(@Body Art art);

    @DELETE("arts/{id}")
    Observable<Art> delete(@Path("id") int id);

    @PUT("arts/{id}")
    Observable<Art> update(@Path("id") int id,@Body Art art);
}
