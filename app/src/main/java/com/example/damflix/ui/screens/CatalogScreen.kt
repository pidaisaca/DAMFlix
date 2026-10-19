package com.example.damflix.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.damflix.R
import com.example.damflix.model.Movie
import com.example.damflix.ui.components.MovieCard
import com.example.damflix.ui.preview.CatalogPreviewProvider
import com.example.damflix.ui.preview.sampleMovies

@Composable
fun CatalogScreen(modifier: Modifier = Modifier) {
    val movies = remember { mutableStateListOf(*sampleMovies.toTypedArray()) }

    CatalogContent(
        movies = movies,
        onToggleFavorite = { movieId ->
            val index = movies.indexOfFirst { it.id == movieId }
            if (index != -1) {
                val currentMovie = movies[index]
                movies[index] = currentMovie.copy(isFavorite = !currentMovie.isFavorite)
            }
        },
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogContent(
    movies: List<Movie>,
    onToggleFavorite: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.title_catalog)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
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
