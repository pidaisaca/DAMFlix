package com.example.damflix.model

data class Movie(
    val id: Int = 0,
    val title: String,
    val director: String,
    val year: String,
    val isWatched: Boolean = false,
    val isFavorite: Boolean = false
)
