package com.noorheroes.car24assignment.core.navigation

sealed interface NavigationEvent {
    data class Navigate(val route: String) : NavigationEvent
    data object NavigateBack : NavigationEvent
}
