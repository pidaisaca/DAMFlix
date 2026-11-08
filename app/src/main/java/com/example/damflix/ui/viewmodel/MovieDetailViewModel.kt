package com.example.damflix.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.damflix.data.MovieRepository
import com.example.damflix.ui.state.DetailUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun loadMovieDetail(movieId: Int) {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading

            val result = withContext(Dispatchers.IO) {
                delay(1000)
                repository.getMovieById(movieId)
            }

            if (result != null) {
                _uiState.value = DetailUiState.Success(result)
            } else {
                _uiState.value = DetailUiState.Error("Película no encontrada.")
            }
        }
    }
}
