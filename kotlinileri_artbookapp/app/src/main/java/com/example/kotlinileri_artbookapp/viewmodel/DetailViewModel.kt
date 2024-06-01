package com.example.kotlinileri_artbookapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinileri_artbookapp.model.Art
import com.example.kotlinileri_artbookapp.repository.ArtRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DetailViewModel  @Inject constructor(
    private val repository: ArtRepository,
) : ViewModel() {


    private val insertArtMsg = MutableLiveData<String>()
    val insertArtMessage : LiveData<String>
        get() = insertArtMsg


    fun insertArt(art: Art) = viewModelScope.launch {
        repository.insert(art)
    }

    fun validateAndInsertArt(art:Art){
        if(art.name.isEmpty() && art.artisName.isEmpty() && art.year.isEmpty()){
            insertArtMsg.postValue("Enter all values")
            return
        }

        try {
            insertArt(art)
            insertArtMsg.postValue("Success")
        } catch (e:Exception){
            insertArtMsg.postValue("Error")
        }
    }


}