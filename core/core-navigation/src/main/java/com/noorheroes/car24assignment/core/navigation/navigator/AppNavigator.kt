package com.noorheroes.car24assignment.core.navigation.navigator

import com.noorheroes.car24assignment.core.navigation.NavigationEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNavigator @Inject constructor() {
    private val _navigationEvents = MutableSharedFlow<NavigationEvent>(extraBufferCapacity = 1)
    val navigationEvents = _navigationEvents.asSharedFlow()

    fun navigate(route: String) {
        _navigationEvents.tryEmit(NavigationEvent.Navigate(route))
    }

    fun navigateBack() {
        _navigationEvents.tryEmit(NavigationEvent.NavigateBack)
    }
}
