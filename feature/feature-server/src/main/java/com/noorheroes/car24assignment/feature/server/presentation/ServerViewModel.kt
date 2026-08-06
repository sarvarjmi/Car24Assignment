package com.noorheroes.car24assignment.feature.server.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noorheroes.car24assignment.core.domain.usecase.*
import com.noorheroes.car24assignment.core.json.validator.SDUIValidator
import com.noorheroes.car24assignment.core.model.domain.Screen
import com.noorheroes.car24assignment.core.model.json.ScreenModel
import com.noorheroes.car24assignment.core.model.repository.ScreenRepository
import com.noorheroes.car24assignment.feature.server.metadata.WidgetMetadata
import com.noorheroes.car24assignment.feature.server.metadata.WidgetMetadataRegistry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
        val metadata: WidgetMetadata? = null,
        val hasUnsavedChanges: Boolean = false,
        val isFullScreenJson: Boolean = false
    ) : ServerUiState
    data object Success : ServerUiState
    data class Error(val message: String) : ServerUiState
}

@HiltViewModel
class ServerViewModel @Inject constructor(
    private val getScreensUseCase: GetScreensUseCase,
    private val screenRepository: ScreenRepository,
    private val getComponentJsonUseCase: GetComponentJsonUseCase,
    private val updateComponentUseCase: UpdateComponentUseCase,
    private val updateFullScreenUseCase: UpdateFullScreenUseCase,
    private val validator: SDUIValidator,
    private val json: Json,
    private val savedStateHandle: SavedStateHandle,
    @dagger.hilt.android.qualifiers.ApplicationContext private val context: android.content.Context
) : ViewModel() {

    private val _uiState = MutableStateFlow<ServerUiState>(ServerUiState.Idle)
    val uiState: StateFlow<ServerUiState> = _uiState.asStateFlow()

    private val undoStack = java.util.Stack<String>()
    private val redoStack = java.util.Stack<String>()

    private val _screens = MutableStateFlow<List<Screen>>(emptyList())
    val screens: StateFlow<List<Screen>> = _screens.asStateFlow()
    
    val selectedScreenId = savedStateHandle.getStateFlow("selected_screen_id", "")
    val selectedComponentId = savedStateHandle.getStateFlow("selected_component_id", "")

    init {
        loadScreens()
    }

    fun setSelectedScreen(id: String) {
        savedStateHandle["selected_screen_id"] = id
    }

    fun loadScreens() {
        viewModelScope.launch {
            _screens.value = getScreensUseCase()
        }
    }

    fun loadFullJson(screenId: String) {
        viewModelScope.launch {
            _uiState.value = ServerUiState.Loading
            undoStack.clear()
            redoStack.clear()
            savedStateHandle["selected_component_id"] = "FULL_SCREEN"
            
            val jsonStr = screenRepository.getScreenJson(screenId)
            if (jsonStr != null) {
                val prettyJson = prettyPrint(jsonStr)
                _uiState.value = ServerUiState.Editing(
                    componentId = screenId,
                    type = "screen",
                    properties = emptyMap(),
                    json = prettyJson,
                    isFullScreenJson = true
                )
            } else {
                _uiState.value = ServerUiState.Error("Screen not found")
            }
        }
    }

    fun updateProperty(key: String, value: Any) {
        val currentState = _uiState.value
        if (currentState is ServerUiState.Editing) {
            undoStack.push(currentState.json)
            redoStack.clear()
            val newProperties = currentState.properties.toMutableMap()
            newProperties[key] = when (value) {
                is String -> JsonPrimitive(value)
                is Number -> JsonPrimitive(value)
                is Boolean -> JsonPrimitive(value)
                else -> JsonPrimitive(value.toString())
            }
            
            val originalJson = try {
                val element = json.parseToJsonElement(currentState.json)
                (element as? JsonObject)?.toMutableMap() ?: mutableMapOf<String, JsonElement>()
            } catch (e: Exception) {
                mutableMapOf<String, JsonElement>()
            }
            originalJson["properties"] = JsonObject(newProperties)
            val newJson = json.encodeToString(JsonObject(originalJson))
            
            _uiState.value = currentState.copy(
                properties = newProperties,
                json = newJson,
                hasUnsavedChanges = true
            )
        }
    }

    fun loadComponent(componentId: String) {
        savedStateHandle["selected_component_id"] = componentId
        viewModelScope.launch {
            _uiState.value = ServerUiState.Loading
            undoStack.clear()
            redoStack.clear()
            val jsonStr = getComponentJsonUseCase(componentId)
            if (jsonStr != null) {
                val prettyJson = prettyPrint(jsonStr)
                try {
                    val jsonElement = json.parseToJsonElement(jsonStr)
                    val element = jsonElement as? JsonObject ?: throw Exception("Not an object")
                    val type = element["type"]?.let { if (it is JsonPrimitive) it.content else null } ?: "unknown"
                    val propertiesElement = element["properties"]
                    val properties = (if (propertiesElement is JsonObject) propertiesElement else emptyMap<String, JsonElement>())
                    val metadata = WidgetMetadataRegistry.get(type)
                    
                    _uiState.value = ServerUiState.Editing(
                        componentId = componentId,
                        type = type,
                        properties = properties,
                        json = prettyJson,
                        metadata = metadata,
                        hasUnsavedChanges = false
                    )
                } catch (e: Exception) {
                    _uiState.value = ServerUiState.Error("Failed to parse component JSON")
                }
            } else {
                _uiState.value = ServerUiState.Error("Component not found")
            }
        }
    }

    fun updateRawJson(jsonStr: String) {
        val currentState = _uiState.value
        if (currentState is ServerUiState.Editing) {
            undoStack.push(currentState.json)
            redoStack.clear()
            try {
                if (currentState.isFullScreenJson) {
                    _uiState.value = currentState.copy(json = jsonStr, hasUnsavedChanges = true)
                } else {
                    val jsonElement = json.parseToJsonElement(jsonStr)
                    val element = jsonElement as? JsonObject ?: throw Exception("Not an object")
                    val propertiesElement = element["properties"]
                    val properties = (if (propertiesElement is JsonObject) propertiesElement else emptyMap<String, JsonElement>())
                    _uiState.value = currentState.copy(json = jsonStr, properties = properties, hasUnsavedChanges = true)
                }
            } catch (e: Exception) {
                _uiState.value = currentState.copy(json = jsonStr, hasUnsavedChanges = true)
            }
        }
    }

    fun resetScreen(screenId: String) {
        viewModelScope.launch {
            _uiState.value = ServerUiState.Loading
            try {
                val assetName = when (screenId) {
                    "home_screen" -> "home.json"
                    "landing_screen" -> "landing.json"
                    "deals_screen" -> "deals.json"
                    "profile_screen" -> "profile.json"
                    else -> "home.json"
                }
                val jsonString = context.assets.open(assetName).bufferedReader().use { it.readText() }
                screenRepository.resetScreen(screenId, jsonString)
                _uiState.value = ServerUiState.Success
                loadScreens()
            } catch (e: Exception) {
                _uiState.value = ServerUiState.Error("Failed to reset: ${e.message}")
            }
        }
    }

    fun saveChanges(componentId: String, jsonStr: String, isFullScreen: Boolean) {
        viewModelScope.launch {
            if (isFullScreen) {
                try {
                    val validationResult = validator.validateScreenJson(jsonStr)
                    if (validationResult.isFailure) {
                        _uiState.value = ServerUiState.Error("Invalid JSON format. Please make sure the JSON is valid before updating.\n${validationResult.exceptionOrNull()?.message}")
                        return@launch
                    }
                    val model = json.decodeFromString<ScreenModel>(jsonStr)
                    updateFullScreenUseCase(model)
                    _uiState.value = ServerUiState.Success
                } catch (e: Exception) {
                    _uiState.value = ServerUiState.Error("Invalid JSON format. Please make sure the JSON is valid before updating.")
                }
            } else {
                val validationResult = validator.validateComponentJson(jsonStr)
                if (validationResult.isFailure) {
                    _uiState.value = ServerUiState.Error("Invalid JSON format. Please make sure the JSON is valid before updating.")
                    return@launch
                }
                _uiState.value = ServerUiState.Loading
                updateComponentUseCase(componentId, jsonStr)
                _uiState.value = ServerUiState.Success
            }
        }
    }

    fun undo() {
        if (undoStack.isNotEmpty()) {
            val previousJson = undoStack.pop()
            val currentState = _uiState.value
            if (currentState is ServerUiState.Editing) {
                redoStack.push(currentState.json)
                _uiState.value = currentState.copy(json = previousJson)
            }
        }
    }

    fun redo() {
        if (redoStack.isNotEmpty()) {
            val nextJson = redoStack.pop()
            val currentState = _uiState.value
            if (currentState is ServerUiState.Editing) {
                undoStack.push(currentState.json)
                _uiState.value = currentState.copy(json = nextJson)
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
}
