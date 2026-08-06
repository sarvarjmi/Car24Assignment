package com.noorheroes.car24assignment.feature.landing.presentation

import androidx.lifecycle.ViewModel
import com.noorheroes.car24assignment.core.navigation.Screen
import com.noorheroes.car24assignment.core.navigation.navigator.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    private val navigator: AppNavigator
) : ViewModel() {

    fun onOpenHomeClicked() {
        navigator.navigate(Screen.Home.route)
    }

    fun onOpenServerPanelClicked() {
        navigator.navigate(Screen.Server.route)
    }
}
