package com.noorheroes.car24assignment.feature.landing.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noorheroes.car24assignment.core.model.domain.Screen
import com.noorheroes.car24assignment.core.model.usecase.GetScreenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

sealed interface LandingUiState {
    data object Loading : LandingUiState
    data class Success(val screen: Screen) : LandingUiState
    data class Error(val message: String) : LandingUiState
}

@HiltViewModel
class LandingViewModel @Inject constructor(
    private val getScreenUseCase: GetScreenUseCase
) : ViewModel() {

    val uiState: StateFlow<LandingUiState> = getScreenUseCase("landing_screen")
        .map { screen ->
            if (screen != null) LandingUiState.Success(screen)
            else LandingUiState.Error("Landing screen not found")
        }
        .onStart { emit(LandingUiState.Loading) }
        .catch { e -> emit(LandingUiState.Error(e.message ?: "Unknown error")) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = LandingUiState.Loading
        )
}
