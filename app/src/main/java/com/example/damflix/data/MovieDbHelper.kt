package com.example.damflix.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.damflix.ui.preview.sampleMovies

class MovieDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "damflix.db"
        const val DATABASE_VERSION = 1

        const val TABLE_MOVIES = "movies"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DIRECTOR = "director"
        const val COLUMN_YEAR = "year"
        const val COLUMN_IS_WATCHED = "is_watched"
        const val COLUMN_IS_FAVORITE = "is_favorite"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_MOVIES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT NOT NULL,
                $COLUMN_DIRECTOR TEXT NOT NULL,
                $COLUMN_YEAR TEXT NOT NULL,
                $COLUMN_IS_WATCHED INTEGER NOT NULL DEFAULT 0,
                $COLUMN_IS_FAVORITE INTEGER NOT NULL DEFAULT 0
            )
        """.trimIndent()
        db.execSQL(createTableQuery)

        sampleMovies.forEach { movie ->
            val values = ContentValues().apply {
                put(COLUMN_TITLE, movie.title)
                put(COLUMN_DIRECTOR, movie.director)
                put(COLUMN_YEAR, movie.year)
                put(COLUMN_IS_WATCHED, if (movie.isWatched) 1 else 0)
                put(COLUMN_IS_FAVORITE, if (movie.isFavorite) 1 else 0)
            }
            db.insert(TABLE_MOVIES, null, values)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MOVIES")
        onCreate(db)
    }
}
