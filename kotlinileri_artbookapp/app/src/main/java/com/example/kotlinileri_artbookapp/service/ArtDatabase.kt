package com.example.kotlinileri_artbookapp.service

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kotlinileri_artbookapp.model.Art


@Database(entities = [Art::class], version = 1)
abstract class ArtDatabase:RoomDatabase() {

    abstract fun ArtDao() : ArtDao
}