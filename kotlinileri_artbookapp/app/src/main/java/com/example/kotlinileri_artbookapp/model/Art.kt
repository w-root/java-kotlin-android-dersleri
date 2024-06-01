package com.example.kotlinileri_artbookapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Art(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val name:String,val artisName:String,val year:String,val imageUrl:String)