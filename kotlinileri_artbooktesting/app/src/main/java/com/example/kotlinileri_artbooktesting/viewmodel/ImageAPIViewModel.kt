package com.example.kotlinileri_artbooktesting.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinileri_artbooktesting.model.Art
import com.example.kotlinileri_artbooktesting.model.ImageResponse
import com.example.kotlinileri_artbooktesting.repository.ArtRepository
import com.example.kotlinileri_artbooktesting.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ImageAPIViewModel  @Inject constructor(
    private val repository: ArtRepository,
) : ViewModel() {

    // Image API Fragment
    private val images = MutableLiveData<Resource<ImageResponse>>()

    val imageList : LiveData<Resource<ImageResponse>>
        get() = images



    fun searchImage(searchString:String){
        if(searchString.isEmpty()){
            return
        }
        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.searchImage(searchString)
            images.value = response
        }
    }
}