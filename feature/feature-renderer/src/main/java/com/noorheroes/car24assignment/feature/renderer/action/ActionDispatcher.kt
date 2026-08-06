package com.noorheroes.car24assignment.feature.renderer.action

import com.noorheroes.car24assignment.core.model.domain.Action
import timber.log.Timber

class ActionDispatcher {
    private val handlers = mutableMapOf<String, (Action) -> Unit>()

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
