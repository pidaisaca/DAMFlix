package com.example.damflix.data

import com.example.damflix.model.Movie
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val movieDao: MovieDao) {

    val movies: Flow<List<Movie>> = movieDao.getAllMovies()

    suspend fun getMovieById(id: Int): Movie? {
        return movieDao.getMovieById(id)
    }

    suspend fun insertMovie(movie: Movie): Long {
        return movieDao.insertMovie(movie)
    }

    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean) {
        movieDao.updateFavoriteStatus(id, isFavorite)
    }
}
