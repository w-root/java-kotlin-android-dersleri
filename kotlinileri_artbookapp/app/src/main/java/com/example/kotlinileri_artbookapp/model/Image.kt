package com.example.kotlinileri_artbookapp.model

data class Image(
    val comments : Int,
    val downloads : Int,
    val favorites : Int,
    val id : Int,
    val imageHeight : Int,
    val imageSize : Int,
    val imageWidth : Int,
    val lageImageURL : String,
    val likes : Int,
    val pageURL : String,
    val previewHeight : Int,
    val previewURL : String,
    val previewWidth : Int,
    val tags : String,
    val type : String,
    val user : String,
    val user_id : Int,
    val userImageURL : String,
    val view : Int,
    val webformatHeigth : Int,
    val webformatURL : String,
    val webformatWidth : Int
)