package com.example.kotlinileri_artbooktesting.model

data class ImageResponse(
    val hits : List<ImageResult>,
    val totalHits: Int,
    val total: Int,
)
