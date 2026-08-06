package com.noorheroes.car24assignment.feature.server.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Redo
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.noorheroes.car24assignment.core.model.domain.Screen
import com.noorheroes.car24assignment.core.ui.component.LoadingView
import com.noorheroes.car24assignment.core.ui.dialog.DialogController
import com.noorheroes.car24assignment.core.ui.dialog.DialogRequest
import com.noorheroes.car24assignment.core.ui.snackbar.SnackbarController
import com.noorheroes.car24assignment.feature.server.editor.PropertyEditor
import kotlinx.coroutines.launch
import kotlinx.serialization.json.jsonPrimitive

@Composable
fun ScreenSelector(
    selectedScreenId: String,
    screens: List<Screen>,
    onScreenSelected: (String) -> Unit
) {
    Text("Select Screen Configuration", style = MaterialTheme.typography.titleMedium)
    Spacer(modifier = Modifier.height(8.dp))
    
    Box {
        var expanded by remember { mutableStateOf(false) }
        OutlinedButton(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
            val label = if (selectedScreenId.isEmpty()) "Select Screen" 
                       else screens.find { it.metadata.id == selectedScreenId }?.metadata?.name ?: selectedScreenId
            Text(label)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            screens.forEach { screen ->
                DropdownMenuItem(
                    text = { Text(screen.metadata.name) },
                    onClick = {
                        onScreenSelected(screen.metadata.id)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServerScreen(
    viewModel: ServerViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val screens by viewModel.screens.collectAsState()
    
    val selectedScreenId by viewModel.selectedScreenId.collectAsState()

    var activeTab by remember { mutableIntStateOf(1) } // Default to JSON tab
    var showExitDialog by remember { mutableStateOf(false) }

    val hasUnsavedChanges = (uiState as? ServerUiState.Editing)?.hasUnsavedChanges ?: false
    
    BackHandler(enabled = hasUnsavedChanges) {
        showExitDialog = true
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Discard Edits?") },
            text = { Text("You have unsaved changes that will be lost.") },
            confirmButton = {
                Button(onClick = { 
                    showExitDialog = false
                    onBack()
                }) { Text("Discard") }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) { Text("Cancel") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Local SDUI Server") },
                navigationIcon = {
                    IconButton(onClick = {
                        if (hasUnsavedChanges) showExitDialog = true else onBack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.undo() },
                        enabled = uiState is ServerUiState.Editing
                    ) {
                        Icon(imageVector = Icons.Default.Undo, contentDescription = "Undo")
                    }

                    IconButton(
                        onClick = { viewModel.redo() },
                        enabled = uiState is ServerUiState.Editing
                    ) {
                        Icon(imageVector = Icons.Default.Redo, contentDescription = "Redo")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            ScreenSelector(
                selectedScreenId = selectedScreenId,
                screens = screens,
                onScreenSelected = { id ->
                    viewModel.setSelectedScreen(id)
                    viewModel.loadFullJson(id)
                }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))

            when (val state = uiState) {
                is ServerUiState.Loading -> LoadingView()
                is ServerUiState.Editing -> {
                    TabRow(selectedTabIndex = activeTab) {
                        Tab(selected = activeTab == 0, onClick = { activeTab = 0 }, text = { Text("Form") })
                        Tab(selected = activeTab == 1, onClick = { activeTab = 1 }, text = { Text("JSON") })
                    }
                    
                    Column(modifier = Modifier.weight(1f)) {
                        if (activeTab == 0) {
                            if (state.isFullScreenJson) {
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                                    Text("Full screen form editing is not supported. Please use the JSON tab to edit the complete hierarchy.")
                                }
                            } else {
                                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                                    state.metadata?.properties?.forEach { prop ->
                                        PropertyEditor(
                                            metadata = prop,
                                            value = state.properties[prop.key]?.jsonPrimitive?.content,
                                            onValueChange = { viewModel.updateProperty(prop.key, it) }
                                        )
                                    } ?: Text("No form metadata for this type. Use JSON tab.")
                                }
                            }
                        } else {
                            TextField(
                                value = state.json,
                                onValueChange = { viewModel.updateRawJson(it) },
                                modifier = Modifier.fillMaxSize(),
                                label = { Text(if (state.isFullScreenJson) "Full Screen JSON" else "Component JSON") }
                            )
                        }
                    }

                    Row(modifier = Modifier.padding(vertical = 16.dp)) {
                        Button(
                            onClick = { viewModel.updateRawJson(viewModel.prettyPrint(state.json)) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Pretty Print")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedButton(
                            onClick = { viewModel.resetScreen(selectedScreenId) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Reset")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = { 
                                viewModel.saveChanges(state.componentId, state.json, state.isFullScreenJson)
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Update")
                        }
                    }
                }
                is ServerUiState.Success -> {
                    Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                        Text("Updated successfully!", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.headlineSmall)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { 
                            if (selectedScreenId.isNotEmpty()) viewModel.loadFullJson(selectedScreenId)
                        }) {
                            Text("Continue Editing")
                        }
                    }
                }
                is ServerUiState.Error -> {
                    Column {
                        Text("Error", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.titleLarge)
                        Text(state.message, color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { 
                            if (selectedScreenId.isNotEmpty()) viewModel.loadFullJson(selectedScreenId)
                            else viewModel.loadScreens()
                        }) {
                            Text("Dismiss")
                        }
                    }
                }
                else -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                        Text("Select a screen configuration from the dropdown above to begin editing.")
                    }
                }
            }
        }
    }
}
