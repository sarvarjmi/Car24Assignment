package com.noorheroes.car24assignment.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.noorheroes.car24assignment.core.navigation.NavigationEvent
import com.noorheroes.car24assignment.core.navigation.Screen
import com.noorheroes.car24assignment.core.navigation.navigator.AppNavigator
import com.noorheroes.car24assignment.core.ui.scaffold.AppScaffold
import com.noorheroes.car24assignment.core.ui.snackbar.SnackbarController
import com.noorheroes.car24assignment.core.ui.dialog.AppDialog
import com.noorheroes.car24assignment.core.ui.bottomsheet.AppBottomSheet
import com.noorheroes.car24assignment.feature.home.presentation.HomeScreen
import com.noorheroes.car24assignment.feature.landing.presentation.LandingScreen
import com.noorheroes.car24assignment.feature.renderer.action.ActionDispatcher
import com.noorheroes.car24assignment.feature.renderer.registry.ComponentRegistry
import com.noorheroes.car24assignment.feature.server.presentation.ServerScreen
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    navigator: AppNavigator,
    registry: ComponentRegistry,
    actionDispatcher: ActionDispatcher,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        navigator.navigationEvents.collect { event ->
            when (event) {
                is NavigationEvent.Navigate -> navController.navigate(event.route)
                is NavigationEvent.NavigateBack -> navController.popBackStack()
            }
        }
    }

    LaunchedEffect(Unit) {
        SnackbarController.observe(snackbarHostState)
    }

    AppScaffold(
        modifier = modifier,
        snackbarHostState = snackbarHostState
    ) { padding ->
        AppDialog()
        AppBottomSheet()
        NavHost(
            navController = navController,
            startDestination = Screen.Landing.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Screen.Landing.route) {
                LandingScreen(
                    viewModel = hiltViewModel(),
                    registry = registry,
                    actionDispatcher = actionDispatcher
                )
            }
            composable(Screen.Home.route) {
                HomeScreen(
                    viewModel = hiltViewModel(),
                    registry = registry,
                    actionDispatcher = actionDispatcher,
                    onBack = { navigator.navigateBack() }
                )
            }
            composable(Screen.Server.route) {
                ServerScreen(
                    viewModel = hiltViewModel(),
                    onBack = { navigator.navigateBack() }
                )
            }
        }
    }
}
