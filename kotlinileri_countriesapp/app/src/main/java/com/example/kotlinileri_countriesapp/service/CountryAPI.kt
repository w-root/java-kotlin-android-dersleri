package com.example.kotlinileri_countriesapp.service

import com.example.kotlinileri_countriesapp.model.Country
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET

interface CountryAPI {

    @GET("IA19-DataSetCountries/master/countrydataset.json")
    fun getAll() : Single<List<Country>>

 }