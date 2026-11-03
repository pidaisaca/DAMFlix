package com.example.damflix.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.damflix.data.MovieDatabase
import com.example.damflix.data.MovieRepository
import com.example.damflix.model.Movie
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MovieRepository
    val movies: StateFlow<List<Movie>>

    init {
        val movieDao = MovieDatabase.getInstance(application).movieDao()
        repository = MovieRepository(movieDao)
        movies = repository.movies.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    fun toggleFavorite(movieId: Int) {
        viewModelScope.launch {
            val currentMovie = movies.value.find { it.id == movieId } ?: return@launch
            repository.updateFavoriteStatus(movieId, !currentMovie.isFavorite)
        }
    }

    fun addMovie(
        title: String,
        director: String,
        year: String,
        isWatched: Boolean,
        onSaved: (() -> Unit)? = null,
    ) {
        viewModelScope.launch {
            val newMovie = Movie(
                title = title.trim(),
                director = director.trim(),
                year = year.trim(),
                isWatched = isWatched,
                isFavorite = false
            )
            repository.insertMovie(newMovie)
            onSaved?.invoke()
        }
    }
}
