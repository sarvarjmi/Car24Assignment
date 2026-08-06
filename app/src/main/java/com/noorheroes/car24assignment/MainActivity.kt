package com.noorheroes.car24assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.noorheroes.car24assignment.core.designsystem.theme.AppTheme
import com.noorheroes.car24assignment.core.navigation.navigator.AppNavigator
import com.noorheroes.car24assignment.feature.renderer.action.ActionDispatcher
import com.noorheroes.car24assignment.feature.renderer.registry.ComponentRegistry
import com.noorheroes.car24assignment.navigation.AppNavHost
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: AppNavigator

    @Inject
    lateinit var registry: ComponentRegistry

    @Inject
    lateinit var actionDispatcher: ActionDispatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                AppNavHost(
                    navigator = navigator,
                    registry = registry,
                    actionDispatcher = actionDispatcher,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
