package com.noorheroes.car24assignment.feature.home.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.noorheroes.car24assignment.core.ui.component.ErrorView
import com.noorheroes.car24assignment.core.ui.component.LoadingView
import com.noorheroes.car24assignment.feature.renderer.engine.SDUIRenderer
import com.noorheroes.car24assignment.feature.renderer.registry.ComponentRegistry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    registry: ComponentRegistry,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Cars24 Home") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        // Icon would go here
                    }
                }
            )
        }
    ) { padding ->
        val modifier = Modifier.padding(padding)
        when (val state = uiState) {
            is HomeUiState.Loading -> LoadingView(modifier)
            is HomeUiState.Success -> {
                SDUIRenderer(
                    screen = state.screen,
                    registry = registry,
                    modifier = modifier
                )
            }
            is HomeUiState.Error -> {
                ErrorView(
                    message = state.message,
                    onRetry = { /* Refresh logic */ },
                    modifier = modifier
                )
            }
        }
    }
}
