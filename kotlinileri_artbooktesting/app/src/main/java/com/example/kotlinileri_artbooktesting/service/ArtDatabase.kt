package com.example.kotlinileri_artbooktesting.service

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kotlinileri_artbooktesting.model.Art

@Database(entities = [Art::class], version = 1)
abstract class ArtDatabase :RoomDatabase(){

    abstract fun ArtDao() : ArtDao

}