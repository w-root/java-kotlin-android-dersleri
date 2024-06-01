package com.example.kotlinileri_artbookapp.service

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.kotlinileri_artbookapp.model.Art

@Dao
interface ArtDao {

    @Insert
    suspend fun insert(art:Art)

    @Delete
    suspend fun delete(art:Art)

    @Query("SELECT * FROM art")
    fun getAll() : LiveData<List<Art>>
}