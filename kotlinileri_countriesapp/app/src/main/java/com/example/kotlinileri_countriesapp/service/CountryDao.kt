package com.example.kotlinileri_countriesapp.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.kotlinileri_countriesapp.model.Country

@Dao
interface CountryDao {

    @Query("SELECT * FROM country")
    suspend fun getAll() : List<Country>

    @Query("SELECT * FROM country WHERE uuid = :id")
    suspend fun getCountry(id: Int) : Country

    @Query("DELETE FROM country")
    suspend fun deleteAllCountries()

    @Insert
    suspend fun insertAll(vararg countries: Country) : List<Long>


}