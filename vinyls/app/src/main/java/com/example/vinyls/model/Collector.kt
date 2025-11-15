package com.example.vinyls.model



data class Collector(
    val id: Int,
    val name: String,
    val telephone: String,
    val email: String,
    val comments: List<Comment>,
    val favoritePerformers: List<CollectorPerformer>,
    val collectorAlbums: List<CollectorAlbum>
)

data class CollectorPerformer(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val birthDate: String
)

data class CollectorAlbum(
    val id: Int,
    val price: Int,
    val status: String
)