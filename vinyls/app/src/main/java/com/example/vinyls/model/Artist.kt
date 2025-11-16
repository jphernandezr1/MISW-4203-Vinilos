package com.example.vinyls.model

data class Artist(
    val id: Int,
    val name: String,
    val image: String?,
    val description: String?,
    val birthDate: String? = null,
    val creationDate: String? = null,
    val albums: List<ArtistAlbum>? = null
)

data class ArtistAlbum(
    val id: Int,
    val name: String
)