package com.noorheroes.car24assignment.core.navigation

sealed class Screen(val route: String) {
    data object Entry : Screen("entry")
    data object Home : Screen("home_screen_route")
    data object Landing : Screen("landing_screen_route")
    data object Deals : Screen("deals_screen_route")
    data object Profile : Screen("profile_screen_route")
    data object Server : Screen("server")
}
