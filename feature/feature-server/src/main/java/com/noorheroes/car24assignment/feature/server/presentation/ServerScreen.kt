package com.noorheroes.car24assignment.feature.server.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
    val screens by viewModel.screens.collectAsState()
    
    var componentIdInput by remember { mutableStateOf("hero_banner_1") }
    var jsonInput by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Server Panel") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
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
            Text(text = "Available Screens: ${screens.joinToString { it.metadata.name }}", style = MaterialTheme.typography.labelSmall)
            
            Spacer(modifier = Modifier.height(8.dp))

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
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = { jsonInput = viewModel.prettyPrint(jsonInput) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Pretty Print")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedButton(
                            onClick = { jsonInput = "" },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Reset")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = { viewModel.saveJson(state.componentId, jsonInput) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Save Changes")
                        }
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
                    Button(onClick = { viewModel.loadComponent(componentIdInput) }) {
                        Text("Retry")
                    }
                }
                else -> {
                    Text("Enter Component ID (e.g. hero_banner_1, search_bar_1) and click Load")
                }
            }
        }
    }
}
