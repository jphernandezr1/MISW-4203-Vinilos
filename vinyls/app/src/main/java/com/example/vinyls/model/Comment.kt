package com.example.vinyls.model

data class Comment(
    val id: Int,
    val description: String?,
    val rating: Int? = null
)
