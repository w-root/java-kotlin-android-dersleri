package com.example.kotlinileri_artbookapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinileri_artbookapp.model.Art
import com.example.kotlinileri_artbookapp.repository.ArtRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel  @Inject constructor(
    private val repository: ArtRepository,
) : ViewModel() {

    val imageList = repository.getAll()

    fun delete(art:Art){
        viewModelScope.launch {
            repository.delete(art)
        }
    }
}