package com.noorheroes.car24assignment.feature.renderer.action

import androidx.compose.runtime.staticCompositionLocalOf
import com.noorheroes.car24assignment.core.model.domain.Action
import com.noorheroes.car24assignment.core.model.domain.Component
import com.noorheroes.car24assignment.core.model.domain.RuntimeState
import com.noorheroes.car24assignment.core.navigation.navigator.AppNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class ActionDispatcher(
    private val navigator: AppNavigator,
    private val onUpdateComponent: (String, String) -> Unit = { _, _ -> }
) {
    private val handlers = mutableMapOf<String, (Action) -> Unit>()
    private val scope = CoroutineScope(Dispatchers.Main)

    init {
        // Register default handlers
        registerHandler("navigate") { action ->
            val route = action.target ?: action.payload["route"] as? String
            if (route != null) {
                navigator.navigate(route)
            } else {
                Timber.e("Navigation action missing target/route")
            }
        }
        registerHandler("back") { _ ->
            navigator.navigateBack()
        }
    }

    fun registerHandler(type: String, handler: (Action) -> Unit) {
        handlers[type] = handler
    }

    fun dispatch(action: Action, component: Component? = null) {
        // 1. Evaluate conditions if component context is provided
        if (component != null && action.conditions.isNotEmpty()) {
            val allMet = action.conditions.all { ConditionEvaluator.evaluate(component, it) }
            if (!allMet) {
                Timber.d("Action conditions not met for type: ${action.type}")
                return
            }
        }

        val handler = handlers[action.type.lowercase()]
        if (handler != null) {
            handler(action)
        } else {
            Timber.w("No handler registered for action type: ${action.type}")
        }
    }

    fun dispatchAll(actions: List<Action>, component: Component? = null) {
        actions.sortedBy { it.priority }.forEach { dispatch(it, component) }
    }
}

val LocalActionDispatcher = staticCompositionLocalOf<ActionDispatcher> {
    error("No ActionDispatcher provided")
}
