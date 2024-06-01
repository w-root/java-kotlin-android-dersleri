package com.example.kotlinileri_artbooktesting.api

import com.example.kotlinileri_artbooktesting.model.ImageResponse
import com.example.kotlinileri_artbooktesting.util.Constans
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {

    @GET("api/")
    suspend fun imageSearch(
        @Query("q") searchQuery : String,
        @Query("key") apiKey : String = Constans.API_KEY,
    ) : Response<ImageResponse>
}