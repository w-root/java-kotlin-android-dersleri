package com.example.kotlinileri_artbookapp.service

import com.example.kotlinileri_artbookapp.model.ImageResponse
import com.example.kotlinileri_artbookapp.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {

    @GET("api/")
    suspend fun getImageFromAPI(
        @Query("q") searchQuery : String,
        @Query("key") apiKey : String = Constants.API_KEY,
    ) : Response<ImageResponse>


}