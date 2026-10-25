package com.example.damflix.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.damflix.data.MovieDbHelper
import com.example.damflix.data.MovieRepository
import com.example.damflix.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MovieRepository
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies.asStateFlow()

    init {
        val dbHelper = MovieDbHelper(application)
        repository = MovieRepository(dbHelper)
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            _movies.value = repository.getMovies()
        }
    }

    fun toggleFavorite(movieId: Int) {
        viewModelScope.launch {
            val currentMovie = _movies.value.find { it.id == movieId } ?: return@launch
            repository.updateFavoriteStatus(movieId, !currentMovie.isFavorite)
            loadMovies()
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
            loadMovies()
            onSaved?.invoke()
        }
    }
}
