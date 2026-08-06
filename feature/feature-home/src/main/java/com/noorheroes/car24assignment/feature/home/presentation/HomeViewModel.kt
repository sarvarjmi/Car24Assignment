package com.noorheroes.car24assignment.feature.home.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noorheroes.car24assignment.core.model.domain.Screen
import com.noorheroes.car24assignment.core.domain.usecase.GetScreenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val screen: Screen) : HomeUiState
    data class Error(val message: String) : HomeUiState
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getScreenUseCase: GetScreenUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val retryTrigger = MutableSharedFlow<Unit>(replay = 1)
    
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _screenId = MutableStateFlow(savedStateHandle.get<String>("screen_id") ?: "home_screen")
    
    fun setScreenId(id: String) {
        if (_screenId.value != id) {
            _screenId.value = id
            savedStateHandle["screen_id"] = id
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<HomeUiState> = combine(retryTrigger.onStart { emit(Unit) }, _screenId) { _, id ->
        id
    }.flatMapLatest { screenId ->
            getScreenUseCase(screenId)
                .map { screen ->
                    if (screen != null) HomeUiState.Success(screen)
                    else HomeUiState.Error("Screen $screenId not found")
                }
        }
        .onStart { emit(HomeUiState.Loading) }
        .catch { e -> emit(HomeUiState.Error(e.message ?: "Unknown error")) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState.Loading
        )

    fun onRetry() {
        retryTrigger.tryEmit(Unit)
    }

    fun onRefresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            retryTrigger.emit(Unit)
            kotlinx.coroutines.delay(1000) // Simulated delay
            _isRefreshing.value = false
        }
    }
}
