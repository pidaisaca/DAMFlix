package com.example.damflix

import android.app.Application
import com.example.damflix.data.MovieDatabase
import com.example.damflix.data.MovieRepository

class DAMFlixApplication : Application() {
    val repository: MovieRepository by lazy {
        val database = MovieDatabase.getInstance(this)
        MovieRepository(database.movieDao())
    }
}
