package com.noorheroes.car24assignment.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.noorheroes.car24assignment.core.navigation.NavigationEvent
import com.noorheroes.car24assignment.core.navigation.Screen
import com.noorheroes.car24assignment.core.navigation.navigator.AppNavigator
import com.noorheroes.car24assignment.feature.home.presentation.HomeScreen
import com.noorheroes.car24assignment.feature.landing.presentation.LandingScreen
import com.noorheroes.car24assignment.feature.renderer.action.ActionDispatcher
import com.noorheroes.car24assignment.feature.renderer.registry.ComponentRegistry
import com.noorheroes.car24assignment.feature.server.presentation.ServerScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    navigator: AppNavigator,
    registry: ComponentRegistry,
    actionDispatcher: ActionDispatcher,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        navigator.navigationEvents.collect { event ->
            when (event) {
                is NavigationEvent.Navigate -> navController.navigate(event.route)
                is NavigationEvent.NavigateBack -> navController.popBackStack()
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Landing.route,
        modifier = modifier
    ) {
        composable(Screen.Landing.route) {
            LandingScreen(viewModel = hiltViewModel())
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
