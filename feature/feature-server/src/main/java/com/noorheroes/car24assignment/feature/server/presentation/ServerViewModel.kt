package com.noorheroes.car24assignment.feature.server.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noorheroes.car24assignment.core.domain.usecase.*
import com.noorheroes.car24assignment.core.json.validator.SDUIValidator
import com.noorheroes.car24assignment.core.model.domain.Screen
import com.noorheroes.car24assignment.feature.server.metadata.WidgetMetadata
import com.noorheroes.car24assignment.feature.server.metadata.WidgetMetadataRegistry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import javax.inject.Inject

sealed interface ServerUiState {
    data object Idle : ServerUiState
    data object Loading : ServerUiState
    data class Editing(
        val componentId: String,
        val type: String,
        val properties: Map<String, JsonElement>,
        val json: String,
        val metadata: WidgetMetadata? = null
    ) : ServerUiState
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

    fun loadComponent(componentId: String) {
        viewModelScope.launch {
            _uiState.value = ServerUiState.Loading
            val jsonStr = getComponentJsonUseCase(componentId)
            if (jsonStr != null) {
                val element = json.parseToJsonElement(jsonStr).jsonObject
                val type = element["type"]?.jsonPrimitive?.content ?: "unknown"
                val properties = element["properties"]?.jsonObject ?: emptyMap<String, JsonElement>()
                val metadata = WidgetMetadataRegistry.get(type)
                
                _uiState.value = ServerUiState.Editing(
                    componentId = componentId,
                    type = type,
                    properties = properties,
                    json = jsonStr,
                    metadata = metadata
                )
            } else {
                _uiState.value = ServerUiState.Error("Component not found")
            }
        }
    }

    fun updateProperty(key: String, value: Any) {
        val currentState = _uiState.value
        if (currentState is ServerUiState.Editing) {
            val newProperties = currentState.properties.toMutableMap()
            newProperties[key] = when (value) {
                is String -> JsonPrimitive(value)
                is Number -> JsonPrimitive(value)
                is Boolean -> JsonPrimitive(value)
                else -> JsonPrimitive(value.toString())
            }
            
            val originalJson = json.parseToJsonElement(currentState.json).jsonObject.toMutableMap()
            originalJson["properties"] = JsonObject(newProperties)
            val newJson = json.encodeToString(JsonObject(originalJson))
            
            _uiState.value = currentState.copy(
                properties = newProperties,
                json = newJson
            )
        }
    }

    fun updateRawJson(jsonStr: String) {
        val currentState = _uiState.value
        if (currentState is ServerUiState.Editing) {
            try {
                val element = json.parseToJsonElement(jsonStr).jsonObject
                val properties = element["properties"]?.jsonObject ?: emptyMap<String, JsonElement>()
                
                _uiState.value = currentState.copy(
                    json = jsonStr,
                    properties = properties
                )
            } catch (e: Exception) {
                _uiState.value = currentState.copy(json = jsonStr)
            }
        }
    }

    fun prettyPrint(jsonString: String): String {
        return try {
            val element = json.parseToJsonElement(jsonString)
            val prettyJson = Json { prettyPrint = true }
            prettyJson.encodeToString(JsonElement.serializer(), element)
        } catch (e: Exception) {
            jsonString
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
