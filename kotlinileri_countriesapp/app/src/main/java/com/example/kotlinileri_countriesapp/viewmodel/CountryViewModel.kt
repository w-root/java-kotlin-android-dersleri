package com.example.kotlinileri_countriesapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.kotlinileri_countriesapp.model.Country
import com.example.kotlinileri_countriesapp.service.CountryDatabase
import kotlinx.coroutines.launch

class CountryViewModel (application: Application) : BaseViewModel(application) {

    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom(id:Int){  launch {
        val c = CountryDatabase(getApplication()).countryDao().getCountry(id)
        countryLiveData.value = c
        Toast.makeText(getApplication(),"Countries from SQLite", Toast.LENGTH_SHORT).show()

    }
    }
}