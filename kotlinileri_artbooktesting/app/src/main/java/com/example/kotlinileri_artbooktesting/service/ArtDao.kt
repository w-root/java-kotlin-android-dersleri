package com.example.kotlinileri_artbooktesting.service

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.kotlinileri_artbooktesting.model.Art

@Dao
interface ArtDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(art: Art)

    @Delete
    suspend fun delete(art: Art)

    @Query("SELECT * FROM arts")
    fun observeArts(): LiveData<List<Art>>
}
