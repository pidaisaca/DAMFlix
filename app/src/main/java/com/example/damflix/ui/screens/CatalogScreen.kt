package com.example.damflix.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.damflix.model.Movie
import com.example.damflix.ui.components.MovieCard
import com.example.damflix.ui.preview.CatalogPreviewProvider
import com.example.damflix.ui.viewmodel.MovieViewModel

@Composable
fun CatalogScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieViewModel = viewModel(),
) {
    val movies by viewModel.movies.collectAsStateWithLifecycle()

    CatalogContent(
        movies = movies,
        onToggleFavorite = { movieId ->
            viewModel.toggleFavorite(movieId)
        },
        modifier = modifier,
    )
}

@Composable
fun CatalogContent(
    movies: List<Movie>,
    onToggleFavorite: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            items(
                items = movies,
                key = { it.id },
            ) { movie ->
                MovieCard(
                    movie = movie,
                    onToggleFavorite = onToggleFavorite,
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Catalog - Preview Provider")
@Composable
fun CatalogPreview(
    @PreviewParameter(CatalogPreviewProvider::class) movieList: List<Movie>,
) {
    MaterialTheme {
        CatalogContent(
            movies = movieList,
            onToggleFavorite = {},
        )
    }
}
