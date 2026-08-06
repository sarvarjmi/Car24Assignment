package com.noorheroes.car24assignment.feature.server.presentation

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
import com.noorheroes.car24assignment.core.ui.component.LoadingView
import com.noorheroes.car24assignment.core.ui.dialog.DialogController
import com.noorheroes.car24assignment.core.ui.dialog.DialogRequest
import com.noorheroes.car24assignment.core.ui.snackbar.SnackbarController
import com.noorheroes.car24assignment.feature.server.editor.PropertyEditor
import kotlinx.coroutines.launch
import kotlinx.serialization.json.jsonPrimitive

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServerScreen(
    viewModel: ServerViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val screens by viewModel.screens.collectAsState()

    var selectedScreenId by remember { mutableStateOf("") }
    var selectedComponentId by remember { mutableStateOf("") }
    
    var activeTab by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Local SDUI Server") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    var showImportDialog by remember { mutableStateOf(false) }
                    IconButton(onClick = { showImportDialog = true }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Import JSON")
                    }
                    
                    IconButton(onClick = { viewModel.undo() }) {
                        Icon(imageVector = Icons.Default.Undo, contentDescription = "Undo")
                    }

                    IconButton(onClick = { viewModel.redo() }) {
                        Icon(imageVector = Icons.Default.Redo, contentDescription = "Redo")
                    }
                    
                    if (showImportDialog) {
                        var importText by remember { mutableStateOf("") }
                        AlertDialog(
                            onDismissRequest = { showImportDialog = false },
                            title = { Text("Import Component JSON") },
                            text = {
                                TextField(
                                    value = importText,
                                    onValueChange = { importText = it },
                                    modifier = Modifier.fillMaxWidth().height(200.dp),
                                    placeholder = { Text("Paste JSON here...") }
                                )
                            },
                            confirmButton = {
                                Button(onClick = {
                                    // Use first part of ID or prompt user. 
                                    // Simplification: we'll try to extract ID from JSON.
                                    viewModel.updateRawJson(importText)
                                    showImportDialog = false
                                }) {
                                    Text("Import")
                                }
                            }
                        )
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
            // 1. Screen / Component Selection
            Text("Select Component to Edit", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            
            // Screen Selector
            Box {
                var expanded by remember { mutableStateOf(false) }
                OutlinedButton(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
                    Text(if (selectedScreenId.isEmpty()) "Select Screen" else selectedScreenId)
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    screens.forEach { screen ->
                        DropdownMenuItem(
                            text = { Text(screen.metadata.name) },
                            onClick = {
                                selectedScreenId = screen.metadata.id
                                expanded = false
                            }
                        )
                    }
                }
            }
            
            // Simplified for assignment: show component list directly if screen selected
            if (selectedScreenId.isNotEmpty()) {
                val screen = screens.find { it.metadata.id == selectedScreenId }
                screen?.sections?.forEach { section ->
                    Text(section.title ?: section.id, style = MaterialTheme.typography.labelSmall)
                    section.components.forEach { comp ->
                        TextButton(onClick = { 
                            selectedComponentId = comp.id
                            viewModel.loadComponent(comp.id)
                        }) {
                            Text(comp.id + " (" + comp.type + ")")
                        }
                    }
                }
                
                // Add Screen Metadata/Config editing option
                TextButton(onClick = { 
                    selectedComponentId = "SCREEN_CONFIG"
                    viewModel.loadScreenConfig(selectedScreenId)
                }) {
                    Text("Edit Screen Configuration", color = MaterialTheme.colorScheme.secondary)
                }
            }

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
                    
                    Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())) {
                        if (activeTab == 0) {
                            // Structured Form
                            state.metadata?.properties?.forEach { prop ->
                                PropertyEditor(
                                    metadata = prop,
                                    value = state.properties[prop.key]?.jsonPrimitive?.content,
                                    onValueChange = { viewModel.updateProperty(prop.key, it) }
                                )
                            } ?: Text("No metadata for ${state.type}. Use JSON tab.")
                        } else {
                            // Raw JSON Editor
                            TextField(
                                value = state.json,
                                onValueChange = { viewModel.updateRawJson(it) },
                                modifier = Modifier.fillMaxSize(),
                                label = { Text("Component JSON") }
                            )
                        }
                    }

                    Row(modifier = Modifier.padding(vertical = 16.dp)) {
                        val scope = rememberCoroutineScope()
                        val context = androidx.compose.ui.platform.LocalContext.current
                        Button(
                            onClick = { 
                                val text = viewModel.prettyPrint(state.json)
                                viewModel.updateRawJson(text)
                                val clipboard = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                                val clip = android.content.ClipData.newPlainText("SDUI Component", text)
                                clipboard.setPrimaryClip(clip)
                                SnackbarController.show("JSON copied to clipboard")
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Copy JSON")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedButton(
                            onClick = { 
                                scope.launch {
                                    DialogController.show(
                                        DialogRequest(
                                            title = "Discard Changes?",
                                            message = "All unsaved edits will be lost.",
                                            confirmLabel = "Discard",
                                            dismissLabel = "Cancel",
                                            onConfirm = { viewModel.loadComponent(state.componentId) }
                                        )
                                    )
                                }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Reset")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = { 
                                if (state.componentId == "SCREEN_CONFIG") {
                                    viewModel.saveScreenConfig(selectedScreenId, state.properties)
                                } else {
                                    viewModel.saveJson(state.componentId, state.json) 
                                }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Save")
                        }
                    }
                }
                is ServerUiState.Success -> {
                    Text("Saved successfully!", color = MaterialTheme.colorScheme.primary)
                    Button(onClick = { viewModel.loadComponent(selectedComponentId) }) {
                        Text("Back to Edit")
                    }
                }
                is ServerUiState.Error -> {
                    Text("Error: ${state.message}", color = MaterialTheme.colorScheme.error)
                }
                else -> {}
            }
        }
    }
}
