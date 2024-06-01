package com.example.kotlinileri_countriesapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.UUID

@Entity
data class Country(
    @ColumnInfo("name")
    @SerializedName("name")
    val countryName:String?,

    @ColumnInfo("region")
    @SerializedName("region")
    val countryRegion:String?,

    @ColumnInfo("capital")
    @SerializedName("capital")
    val countryCapital:String?,

    @ColumnInfo("language")
    @SerializedName("language")
    val countryLanguage:String?,

    @ColumnInfo("currency")
    @SerializedName("currency")
    val countryCurrency:String?,

    @ColumnInfo("flag")
    @SerializedName("flag")
    val imageUrl:String) {

    @PrimaryKey(true)
    var uuid: Int = 0
}