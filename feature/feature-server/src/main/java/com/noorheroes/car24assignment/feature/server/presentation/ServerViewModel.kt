package com.noorheroes.car24assignment.feature.server.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noorheroes.car24assignment.core.model.usecase.GetComponentJsonUseCase
import com.noorheroes.car24assignment.core.model.usecase.UpdateComponentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ServerUiState {
    data object Idle : ServerUiState
    data object Loading : ServerUiState
    data class Editing(val componentId: String, val json: String) : ServerUiState
    data object Success : ServerUiState
    data class Error(val message: String) : ServerUiState
}

@HiltViewModel
class ServerViewModel @Inject constructor(
    private val getComponentJsonUseCase: GetComponentJsonUseCase,
    private val updateComponentUseCase: UpdateComponentUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ServerUiState>(ServerUiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun loadComponent(componentId: String) {
        viewModelScope.launch {
            _uiState.value = ServerUiState.Loading
            val json = getComponentJsonUseCase(componentId)
            if (json != null) {
                _uiState.value = ServerUiState.Editing(componentId, json)
            } else {
                _uiState.value = ServerUiState.Error("Component not found")
            }
        }
    }

    fun saveJson(componentId: String, json: String) {
        viewModelScope.launch {
            _uiState.value = ServerUiState.Loading
            updateComponentUseCase(componentId, json)
            _uiState.value = ServerUiState.Success
        }
    }
}
