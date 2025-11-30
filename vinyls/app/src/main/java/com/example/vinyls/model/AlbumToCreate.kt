package com.example.vinyls.model

data class AlbumToCreate(
    val name: String,
    val cover: String?,
    val releaseDate: String?,
    val description: String?,
    val genre: String?,
    val recordLabel: String?,
)