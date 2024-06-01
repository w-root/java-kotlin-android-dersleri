package com.example.kotlinileri_artbookapp.repository

import androidx.lifecycle.LiveData
import com.example.kotlinileri_artbookapp.model.Art
import com.example.kotlinileri_artbookapp.model.ImageResponse
import com.example.kotlinileri_artbookapp.service.ArtDao
import com.example.kotlinileri_artbookapp.service.RetrofitAPI
import retrofit2.Response
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val api:RetrofitAPI,
    private val db:ArtDao
    ){

    suspend fun searchImages(searchQuery:String): Response<ImageResponse> {
        return api.getImageFromAPI(searchQuery)
    }

    suspend fun insert(art:Art){
        db.insert(art)
    }

    fun getAll(): LiveData<List<Art>> {
        return db.getAll()
    }

    suspend fun delete(art:Art) {
        db.delete(art)
    }
}