package com.example.damflix.ui.state

import com.example.damflix.model.Movie

sealed interface DetailUiState {
    object Loading : DetailUiState
    data class Success(val movie: Movie) : DetailUiState
    data class Error(val message: String) : DetailUiState
}
