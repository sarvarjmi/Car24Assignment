package com.noorheroes.car24assignment.feature.renderer.action

import androidx.compose.runtime.staticCompositionLocalOf
import com.noorheroes.car24assignment.core.model.domain.Action
import com.noorheroes.car24assignment.core.navigation.navigator.AppNavigator
import timber.log.Timber

class ActionDispatcher(
    private val navigator: AppNavigator
) {
    private val handlers = mutableMapOf<String, (Action) -> Unit>()

    init {
        // Register default handlers
        registerHandler("navigate") { action ->
            val route = action.payload["route"] as? String
            if (route != null) {
                navigator.navigate(route)
            } else {
                Timber.e("Navigation action missing 'route' payload")
            }
        }
    }

    fun registerHandler(type: String, handler: (Action) -> Unit) {
        handlers[type] = handler
    }

    fun dispatch(action: Action) {
        val handler = handlers[action.type]
        if (handler != null) {
            handler(action)
        } else {
            Timber.w("No handler registered for action type: ${action.type}")
        }
    }
}

val LocalActionDispatcher = staticCompositionLocalOf<ActionDispatcher> {
    error("No ActionDispatcher provided")
}
