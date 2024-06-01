package com.example.kotlinileri_countriesapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.kotlinileri_countriesapp.model.Country
import com.example.kotlinileri_countriesapp.service.CountryAPIService
import com.example.kotlinileri_countriesapp.service.CountryDatabase
import com.example.kotlinileri_countriesapp.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : BaseViewModel(application) {

    private val countryAPIService = CountryAPIService()
    private val disposable = CompositeDisposable()
    private val customerPreferences = CustomSharedPreferences(getApplication())
    private val refreshTime = 10 * 60 * 1000 * 1000 * 1000L

    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData(){
        val updateTime = customerPreferences.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime)
            getDataFromSQLite()
        else
            getDataFromAPI()
    }
    fun refreshFromAPI(){
        getDataFromAPI()
    }

    private fun getDataFromSQLite(){
        countryLoading.value = true

        launch {
            val countries1 = CountryDatabase(getApplication()).countryDao().getAll()
            countries.value = countries1
            countryLoading.value = false
            countryError.value = false
            Toast.makeText(getApplication(),"Countries from SQLite",Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDataFromAPI(){
        countryLoading.value = true

        disposable.add(countryAPIService
                .getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {
                        storeSQLite(t)
                        Toast.makeText(getApplication(),"Countries from API",Toast.LENGTH_SHORT).show()

                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        countryLoading.value = false
                        countryError.value = true
                    }
                }))
    }

    private fun storeSQLite(t : List<Country>){
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            val list = dao.insertAll(*t.toTypedArray())
            var i = 0
            while(i < list.size){
                t[i].uuid = list[i].toInt()
                i += 1
            }
            countries.value = t
            countryLoading.value = false
            countryError.value = false
        }

        customerPreferences.saveTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}