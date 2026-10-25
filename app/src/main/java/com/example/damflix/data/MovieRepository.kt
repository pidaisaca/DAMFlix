package com.example.damflix.data

import android.content.ContentValues
import com.example.damflix.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(private val dbHelper: MovieDbHelper) {

    suspend fun getMovies(): List<Movie> = withContext(Dispatchers.IO) {
        val movieList = mutableListOf<Movie>()
        val db = dbHelper.readableDatabase
        val query = "SELECT ${MovieDbHelper.COLUMN_ID}, ${MovieDbHelper.COLUMN_TITLE}, ${MovieDbHelper.COLUMN_DIRECTOR}, ${MovieDbHelper.COLUMN_YEAR}, ${MovieDbHelper.COLUMN_IS_WATCHED}, ${MovieDbHelper.COLUMN_IS_FAVORITE} FROM ${MovieDbHelper.TABLE_MOVIES}"
        val cursor = db.rawQuery(query, null)

        cursor.use { c ->
            val idIndex = c.getColumnIndexOrThrow(MovieDbHelper.COLUMN_ID)
            val titleIndex = c.getColumnIndexOrThrow(MovieDbHelper.COLUMN_TITLE)
            val directorIndex = c.getColumnIndexOrThrow(MovieDbHelper.COLUMN_DIRECTOR)
            val yearIndex = c.getColumnIndexOrThrow(MovieDbHelper.COLUMN_YEAR)
            val isWatchedIndex = c.getColumnIndexOrThrow(MovieDbHelper.COLUMN_IS_WATCHED)
            val isFavoriteIndex = c.getColumnIndexOrThrow(MovieDbHelper.COLUMN_IS_FAVORITE)

            while (c.moveToNext()) {
                val id = c.getInt(idIndex)
                val title = c.getString(titleIndex)
                val director = c.getString(directorIndex)
                val year = c.getString(yearIndex)
                val isWatched = c.getInt(isWatchedIndex) == 1
                val isFavorite = c.getInt(isFavoriteIndex) == 1

                movieList.add(
                    Movie(
                        id = id,
                        title = title,
                        director = director,
                        year = year,
                        isWatched = isWatched,
                        isFavorite = isFavorite
                    )
                )
            }
        }
        movieList
    }

    suspend fun insertMovie(movie: Movie): Long = withContext(Dispatchers.IO) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(MovieDbHelper.COLUMN_TITLE, movie.title)
            put(MovieDbHelper.COLUMN_DIRECTOR, movie.director)
            put(MovieDbHelper.COLUMN_YEAR, movie.year)
            put(MovieDbHelper.COLUMN_IS_WATCHED, if (movie.isWatched) 1 else 0)
            put(MovieDbHelper.COLUMN_IS_FAVORITE, if (movie.isFavorite) 1 else 0)
        }
        db.insert(MovieDbHelper.TABLE_MOVIES, null, values)
    }

    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean): Int = withContext(Dispatchers.IO) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(MovieDbHelper.COLUMN_IS_FAVORITE, if (isFavorite) 1 else 0)
        }
        val whereClause = "${MovieDbHelper.COLUMN_ID} = ?"
        val whereArgs = arrayOf(id.toString())
        db.update(MovieDbHelper.TABLE_MOVIES, values, whereClause, whereArgs)
    }
}
