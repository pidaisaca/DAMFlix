package com.example.damflix.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.damflix.model.Movie

val sampleMovies = listOf(
    Movie(id = 1, title = "Inception", director = "Christopher Nolan", year = "2010", isWatched = true, isFavorite = true),
    Movie(id = 2, title = "Interstellar", director = "Christopher Nolan", year = "2014", isWatched = true, isFavorite = false),
    Movie(id = 3, title = "The Dark Knight", director = "Christopher Nolan", year = "2008", isWatched = true, isFavorite = true),
    Movie(id = 4, title = "Pulp Fiction", director = "Quentin Tarantino", year = "1994", isWatched = false, isFavorite = false),
    Movie(id = 5, title = "Matrix", director = "Lana & Lilly Wachowski", year = "1999", isWatched = true, isFavorite = false),
    Movie(id = 6, title = "Avatar", director = "James Cameron", year = "2009", isWatched = false, isFavorite = false),
)

class CatalogPreviewProvider : PreviewParameterProvider<List<Movie>> {
    override val values: Sequence<List<Movie>> = sequenceOf(
        sampleMovies,
        listOf(
            Movie(id = 1, title = "Avatar", director = "James Cameron", year = "2009", isWatched = true, isFavorite = true),
        ),
        emptyList(),
    )
}
