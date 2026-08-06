package com.noorheroes.car24assignment.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noorheroes.car24assignment.core.model.domain.Screen
import com.noorheroes.car24assignment.core.model.usecase.GetScreenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val screen: Screen) : HomeUiState
    data class Error(val message: String) : HomeUiState
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getScreenUseCase: GetScreenUseCase
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = getScreenUseCase("home_screen")
        .map { screen ->
            if (screen != null) HomeUiState.Success(screen)
            else HomeUiState.Error("Screen not found")
        }
        .onStart { emit(HomeUiState.Loading) }
        .catch { e -> emit(HomeUiState.Error(e.message ?: "Unknown error")) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState.Loading
        )
}
