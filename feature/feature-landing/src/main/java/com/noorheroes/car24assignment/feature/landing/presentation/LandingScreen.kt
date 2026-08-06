package com.noorheroes.car24assignment.feature.landing.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.noorheroes.car24assignment.core.ui.component.ErrorView
import com.noorheroes.car24assignment.core.ui.component.LoadingView
import com.noorheroes.car24assignment.feature.renderer.action.ActionDispatcher
import com.noorheroes.car24assignment.feature.renderer.engine.SDUIRenderer
import com.noorheroes.car24assignment.feature.renderer.registry.ComponentRegistry

@Composable
fun LandingScreen(
    viewModel: LandingViewModel,
    registry: ComponentRegistry,
    actionDispatcher: ActionDispatcher
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold { padding ->
        val modifier = Modifier.padding(padding)
        when (val state = uiState) {
            is LandingUiState.Loading -> LoadingView(modifier)
            is LandingUiState.Success -> {
                SDUIRenderer(
                    screen = state.screen,
                    registry = registry,
                    actionDispatcher = actionDispatcher,
                    modifier = modifier
                )
            }
            is LandingUiState.Error -> {
                ErrorView(
                    message = state.message,
                    onRetry = { /* Retry logic */ },
                    modifier = modifier
                )
            }
        }
    }
}
