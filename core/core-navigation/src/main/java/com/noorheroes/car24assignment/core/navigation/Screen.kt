package com.noorheroes.car24assignment.core.navigation

sealed class Screen(val route: String) {
    data object Landing : Screen("landing")
    data object Home : Screen("home")
    data object Server : Screen("server")
}
