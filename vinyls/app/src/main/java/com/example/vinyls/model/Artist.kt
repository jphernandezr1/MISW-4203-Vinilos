package com.example.vinyls.model

data class Artist(
    val id: Int,
    val name: String,
    val image: String?,
    val description: String?,
    val birthDate: String? = null,
    val creationDate: String? = null,
    val monthlyListeners: Int? = null,
    val albums: List<ArtistAlbum> = emptyList(),
    val performerPrizes: List<PerformerPrize> = emptyList()
)

data class ArtistAlbum(
    val id: Int,
    val name: String,
    val cover: String? = null,
    val releaseDate: String? = null,
)

data class PerformerPrize(
    val id: Int,
    val premiationDate: String?
)