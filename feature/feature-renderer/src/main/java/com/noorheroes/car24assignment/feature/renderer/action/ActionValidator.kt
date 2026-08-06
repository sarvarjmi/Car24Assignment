package com.noorheroes.car24assignment.feature.renderer.action

import com.noorheroes.car24assignment.core.model.domain.Action
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActionValidator @Inject constructor() {

    private val allowedTypes = setOf(
        "navigate", "back", "snackbar", "dialog", "refresh", 
        "bottomsheet", "togglestate", "updatecomponent", "analytics", "composite"
    )

    fun isValid(action: Action): Boolean {
        // 1. Basic type check
        if (!allowedTypes.contains(action.type.lowercase())) return false
        
        // 2. Security Check (Doc 25, Rule 22)
        // Ensure payload doesn't contain suspicious strings (placeholder for real security logic)
        action.payload.values.forEach { value ->
            if (value is String) {
                if (value.contains("DROP TABLE") || value.contains("exec(") || value.contains("java.lang")) {
                    return false
                }
            }
        }
        
        return true
    }
}
