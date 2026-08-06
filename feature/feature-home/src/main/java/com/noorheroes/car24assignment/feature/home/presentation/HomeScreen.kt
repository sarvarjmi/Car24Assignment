package com.noorheroes.car24assignment.feature.home.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.noorheroes.car24assignment.core.ui.component.ErrorView
import com.noorheroes.car24assignment.core.ui.component.LoadingView
import com.noorheroes.car24assignment.core.ui.empty.EmptyView
import com.noorheroes.car24assignment.feature.renderer.action.ActionDispatcher
import com.noorheroes.car24assignment.feature.renderer.engine.SDUIRenderer
import com.noorheroes.car24assignment.feature.renderer.registry.ComponentRegistry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    registry: ComponentRegistry,
    actionDispatcher: ActionDispatcher,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = if (uiState is HomeUiState.Success) {
                            (uiState as HomeUiState.Success).screen.metadata.name
                        } else {
                            "Cars24"
                        }
                    ) 
                },
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
        val modifier = Modifier.padding(padding)
        when (val state = uiState) {
            is HomeUiState.Loading -> LoadingView(modifier)
            is HomeUiState.Success -> {
                if (state.screen.sections.isEmpty()) {
                    EmptyView(modifier = modifier)
                } else {
                    if (state.screen.configuration.refreshable) {
                        PullToRefreshBox(
                            isRefreshing = isRefreshing,
                            onRefresh = { viewModel.onRefresh() },
                            modifier = modifier
                        ) {
                            SDUIRenderer(
                                screen = state.screen,
                                registry = registry,
                                actionDispatcher = actionDispatcher
                            )
                        }
                    } else {
                        SDUIRenderer(
                            screen = state.screen,
                            registry = registry,
                            actionDispatcher = actionDispatcher,
                            modifier = modifier
                        )
                    }
                }
            }
            is HomeUiState.Error -> {
                ErrorView(
                    message = state.message,
                    onRetry = { viewModel.onRetry() },
                    modifier = modifier
                )
            }
        }
    }
}
