package com.example.kotlinileri_artbooktesting.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "arts")
data class Art(
                var name : String,
                var artisName : String,
                var year : Int,
                var imageUrl : String,
                @PrimaryKey(true) val id : Int? = null
    ) {

}
