package com.noorheroes.car24assignment.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.noorheroes.car24assignment.core.navigation.NavigationEvent
import com.noorheroes.car24assignment.core.navigation.Screen
import com.noorheroes.car24assignment.core.navigation.navigator.AppNavigator
import com.noorheroes.car24assignment.core.ui.component.ErrorView
import com.noorheroes.car24assignment.core.ui.component.LoadingView
import com.noorheroes.car24assignment.core.ui.scaffold.AppScaffold
import com.noorheroes.car24assignment.core.ui.snackbar.SnackbarController
import com.noorheroes.car24assignment.core.ui.dialog.AppDialog
import com.noorheroes.car24assignment.core.ui.bottomsheet.AppBottomSheet
import com.noorheroes.car24assignment.feature.home.presentation.HomeScreen
import com.noorheroes.car24assignment.feature.home.presentation.HomeViewModel
import com.noorheroes.car24assignment.feature.renderer.action.ActionDispatcher
import com.noorheroes.car24assignment.feature.renderer.registry.ComponentRegistry
import com.noorheroes.car24assignment.feature.server.presentation.ServerScreen
import com.noorheroes.car24assignment.startup.StartupManager
import com.noorheroes.car24assignment.ui.EntryScreen
import androidx.compose.material3.ExperimentalMaterial3Api

sealed class BottomNavItem(val screen: Screen, val title: String, val icon: ImageVector) {
    data object Home : BottomNavItem(Screen.Home, "Home", Icons.Default.Home)
    data object Landing : BottomNavItem(Screen.Landing, "Landing", Icons.Default.Info)
    data object Deals : BottomNavItem(Screen.Deals, "Deals", Icons.Default.LocalOffer)
    data object Profile : BottomNavItem(Screen.Profile, "Profile", Icons.Default.Person)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    navigator: AppNavigator,
    registry: ComponentRegistry,
    actionDispatcher: ActionDispatcher,
    startupManager: StartupManager,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val initResult by startupManager.isInitialized.collectAsState()

    LaunchedEffect(Unit) {
        navigator.navigationEvents.collect { event ->
            when (event) {
                is NavigationEvent.Navigate -> {
                    navController.navigate(event.route) {
                        if (event.route.endsWith("_route")) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
                is NavigationEvent.NavigateBack -> {
                    if (!navController.popBackStack()) {
                        navController.navigate(Screen.Entry.route) {
                            popUpTo(0)
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        SnackbarController.observe(snackbarHostState)
    }

    val bottomNavItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Landing,
        BottomNavItem.Deals,
        BottomNavItem.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    AppScaffold(
        modifier = modifier,
        snackbarHostState = snackbarHostState,
        bottomBar = {
            val showBottomNav = currentDestination?.route?.endsWith("_route") == true
            if (showBottomNav) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        val isSelected = currentDestination?.hierarchy?.any { it.route == item.screen.route } == true
                        
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.title) },
                            label = { Text(item.title) },
                            selected = isSelected,
                            onClick = {
                                navigator.navigate(item.screen.route)
                            }
                        )
                    }
                }
            }
        }
    ) { padding ->
        val innerModifier = Modifier.padding(padding)
        
        when {
            initResult == null -> LoadingView(innerModifier, "Initializing Application...")
            initResult?.isFailure == true -> ErrorView(
                message = initResult?.exceptionOrNull()?.message ?: "Critical Startup Failure",
                onRetry = { startupManager.onAppStart() },
                modifier = innerModifier
            )
            else -> {
                AppDialog()
                AppBottomSheet()
                NavHost(
                    navController = navController,
                    startDestination = Screen.Entry.route,
                    modifier = innerModifier
                ) {
                    composable(Screen.Entry.route) {
                        EntryScreen(
                            onNavigateToApp = { navigator.navigate(Screen.Landing.route) },
                            onNavigateToServer = { navigator.navigate(Screen.Server.route) }
                        )
                    }
                    
                    composable(Screen.Home.route) {
                        val viewModel: HomeViewModel = hiltViewModel(key = "home_screen")
                        LaunchedEffect(Unit) { viewModel.setScreenId("home_screen") }
                        HomeScreen(
                            viewModel = viewModel,
                            registry = registry,
                            actionDispatcher = actionDispatcher,
                            onBack = { navigator.navigateBack() }
                        )
                    }
                    
                    composable(Screen.Landing.route) {
                        val viewModel: HomeViewModel = hiltViewModel(key = "landing_screen")
                        LaunchedEffect(Unit) { viewModel.setScreenId("landing_screen") }
                        HomeScreen(
                            viewModel = viewModel,
                            registry = registry,
                            actionDispatcher = actionDispatcher,
                            onBack = { navigator.navigateBack() }
                        )
                    }
                    
                    composable(Screen.Deals.route) {
                        val viewModel: HomeViewModel = hiltViewModel(key = "deals_screen")
                        LaunchedEffect(Unit) { viewModel.setScreenId("deals_screen") }
                        HomeScreen(
                            viewModel = viewModel,
                            registry = registry,
                            actionDispatcher = actionDispatcher,
                            onBack = { navigator.navigateBack() }
                        )
                    }
                    
                    composable(Screen.Profile.route) {
                        val viewModel: HomeViewModel = hiltViewModel(key = "profile_screen")
                        LaunchedEffect(Unit) { viewModel.setScreenId("profile_screen") }
                        HomeScreen(
                            viewModel = viewModel,
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
    }
}
