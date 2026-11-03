package com.example.damflix.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "director")
    val director: String,

    @ColumnInfo(name = "year")
    val year: String,

    @ColumnInfo(name = "is_watched")
    val isWatched: Boolean = false,

    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false,
)
