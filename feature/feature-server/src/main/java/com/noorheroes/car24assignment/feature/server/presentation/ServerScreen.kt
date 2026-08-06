package com.noorheroes.car24assignment.feature.server.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.noorheroes.car24assignment.core.ui.component.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServerScreen(
    viewModel: ServerViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var componentIdInput by remember { mutableStateOf("banner_1") }
    var jsonInput by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Server Panel") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        // Back icon
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Row {
                TextField(
                    value = componentIdInput,
                    onValueChange = { componentIdInput = it },
                    label = { Text("Component ID") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { viewModel.loadComponent(componentIdInput) }) {
                    Text("Load")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (val state = uiState) {
                is ServerUiState.Loading -> LoadingView()
                is ServerUiState.Editing -> {
                    // Initialize local input once when loaded
                    LaunchedEffect(state.componentId) {
                        jsonInput = state.json
                    }

                    TextField(
                        value = jsonInput,
                        onValueChange = { jsonInput = it },
                        label = { Text("JSON Payload") },
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        minLines = 10
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.saveJson(state.componentId, jsonInput) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Save Changes")
                    }
                }
                is ServerUiState.Success -> {
                    Text("Changes saved successfully!", color = MaterialTheme.colorScheme.primary)
                    Button(onClick = { viewModel.loadComponent(componentIdInput) }) {
                        Text("Edit Again")
                    }
                }
                is ServerUiState.Error -> {
                    Text("Error: ${state.message}", color = MaterialTheme.colorScheme.error)
                }
                else -> {
                    Text("Enter Component ID and click Load")
                }
            }
        }
    }
}
