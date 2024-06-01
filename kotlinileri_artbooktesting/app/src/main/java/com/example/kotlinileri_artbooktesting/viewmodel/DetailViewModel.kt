package com.example.kotlinileri_artbooktesting.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinileri_artbooktesting.model.Art
import com.example.kotlinileri_artbooktesting.repository.ArtRepository
import com.example.kotlinileri_artbooktesting.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: ArtRepository
) : ViewModel() {

    // Art Details Fragment

    private var insertArtMsg = MutableLiveData<Resource<Art>>()
    val insertArtMessage : LiveData<Resource<Art>>
        get() = insertArtMsg

    private val selectedImage = MutableLiveData<String>()
    val selectedImageUrl : LiveData<String>
        get() = selectedImage

    fun setSelectedImage(url:String){
        selectedImage.postValue(url)
    }
    fun resetInsertArtMsg(){
        insertArtMsg = MutableLiveData<Resource<Art>>()
    }

    fun insertArt(art: Art) = viewModelScope.launch {
        repository.add(art)
    }

    fun makeArt(name:String,artisName:String,year:String){
        if (name.isEmpty() || artisName.isEmpty() || year.isEmpty()){
            insertArtMsg.postValue(Resource.error("Enter name, artist, year",null))
            return
        }
        val yearInt = try {
            year.toInt()
        } catch (e:Exception){
            insertArtMsg.postValue(Resource.error("Year should be number",null))
            return

        }


        val art = Art(name,artisName,yearInt,selectedImage.value ?: "")
        insertArt(art)
        setSelectedImage("")
        insertArtMsg.postValue(Resource.success(art))
    }

}