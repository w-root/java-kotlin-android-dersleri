package com.example.kotlinileri_artbooktesting.repository

import androidx.lifecycle.LiveData
import com.example.kotlinileri_artbooktesting.api.RetrofitAPI
import com.example.kotlinileri_artbooktesting.model.Art
import com.example.kotlinileri_artbooktesting.model.ImageResponse
import com.example.kotlinileri_artbooktesting.service.ArtDao
import com.example.kotlinileri_artbooktesting.util.Resource
import javax.inject.Inject

class ArtRepository @Inject constructor(private val api : RetrofitAPI,private val dao:ArtDao) {

    suspend fun add(art :Art){
        dao.insert(art)
    }

    suspend fun delete(art :Art){
        dao.delete(art)
    }

    suspend fun searchImage(imageString :String): Resource<ImageResponse> {
        return try {
            val response = api.imageSearch(imageString)
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            } else {
                Resource.error("Error",null)
            }
        } catch (e:Exception){
            Resource.error("No data!",null)
        }
    }

    fun getAll(): LiveData<List<Art>> {
        return dao.observeArts()
    }








}