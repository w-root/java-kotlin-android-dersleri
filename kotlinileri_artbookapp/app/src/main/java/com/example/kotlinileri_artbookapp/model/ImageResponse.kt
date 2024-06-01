package com.example.kotlinileri_artbookapp.model


data class ImageResponse(
    val hits : List<Image>,
    val totalHits: Int,
    val total: Int,
)