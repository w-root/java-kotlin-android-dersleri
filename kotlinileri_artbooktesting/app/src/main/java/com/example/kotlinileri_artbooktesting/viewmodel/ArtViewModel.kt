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
class ArtViewModel @Inject constructor(
    private val repository: ArtRepository
) : ViewModel() {

    // Art Fragment
    val artList = repository.getAll()

    fun deleteArt(art: Art) = viewModelScope.launch {
        repository.delete(art)
    }

}