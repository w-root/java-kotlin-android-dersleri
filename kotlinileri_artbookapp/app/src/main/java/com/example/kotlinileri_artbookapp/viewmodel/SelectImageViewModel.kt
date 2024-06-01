package com.example.kotlinileri_artbookapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinileri_artbookapp.model.ImageResponse
import com.example.kotlinileri_artbookapp.repository.ArtRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectImageViewModel  @Inject constructor(
    private val repository: ArtRepository,
) : ViewModel() {

    private val images = MutableLiveData<ImageResponse>()

    val imageList : LiveData<ImageResponse>
        get() = images


    fun searchAndGetImagesFromAPI(searchQuery:String) {
        if(searchQuery.isEmpty()){
            return
        }

        viewModelScope.launch {
            val response = repository.searchImages(searchQuery)
            images.value = response.body()
        }
    }

}