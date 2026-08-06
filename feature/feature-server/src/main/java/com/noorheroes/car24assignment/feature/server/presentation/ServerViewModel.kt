package com.noorheroes.car24assignment.feature.server.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noorheroes.car24assignment.core.domain.usecase.*
import com.noorheroes.car24assignment.core.json.validator.SDUIValidator
import com.noorheroes.car24assignment.core.model.domain.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
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
    private val getScreensUseCase: GetScreensUseCase,
    private val getScreenUseCase: GetScreenUseCase,
    private val getComponentJsonUseCase: GetComponentJsonUseCase,
    private val updateComponentUseCase: UpdateComponentUseCase,
    private val validator: SDUIValidator,
    private val json: Json
) : ViewModel() {

    private val _uiState = MutableStateFlow<ServerUiState>(ServerUiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _screens = MutableStateFlow<List<Screen>>(emptyList())
    val screens = _screens.asStateFlow()

    init {
        loadScreens()
    }

    private fun loadScreens() {
        viewModelScope.launch {
            _screens.value = getScreensUseCase()
        }
    }

    fun getFullHomeScreen() {
        viewModelScope.launch {
            getScreenUseCase("home_screen").collect { screen ->
                if (screen != null) {
                    _uiState.value = ServerUiState.Error("Select a component to edit")
                }
            }
        }
    }

    fun prettyPrint(jsonString: String): String {
        return try {
            val element = json.parseToJsonElement(jsonString)
            json.encodeToString(JsonElement.serializer(), element)
        } catch (e: Exception) {
            jsonString
        }
    }

    fun loadComponent(componentId: String) {
        viewModelScope.launch {
            _uiState.value = ServerUiState.Loading
            val jsonStr = getComponentJsonUseCase(componentId)
            if (jsonStr != null) {
                _uiState.value = ServerUiState.Editing(componentId, jsonStr)
            } else {
                _uiState.value = ServerUiState.Error("Component not found")
            }
        }
    }

    fun saveJson(componentId: String, jsonStr: String) {
        viewModelScope.launch {
            val validationResult = validator.validateComponentJson(jsonStr)
            if (validationResult.isFailure) {
                _uiState.value = ServerUiState.Error("Invalid JSON: ${validationResult.exceptionOrNull()?.message}")
                return@launch
            }

            _uiState.value = ServerUiState.Loading
            updateComponentUseCase(componentId, jsonStr)
            _uiState.value = ServerUiState.Success
        }
    }
}
