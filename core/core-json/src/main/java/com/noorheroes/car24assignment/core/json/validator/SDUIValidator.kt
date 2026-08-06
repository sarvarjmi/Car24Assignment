package com.noorheroes.car24assignment.core.json.validator

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SDUIValidator @Inject constructor() {
    fun validateJson(jsonString: String): Boolean {
        // Basic syntax validation (can use Json.parseToJsonElement)
        return try {
            kotlinx.serialization.json.Json.parseToJsonElement(jsonString)
            true
        } catch (e: Exception) {
            false
        }
    }
}
