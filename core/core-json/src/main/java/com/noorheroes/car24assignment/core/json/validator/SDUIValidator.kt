package com.noorheroes.car24assignment.core.json.validator

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SDUIValidator @Inject constructor(
    private val json: Json
) {
    fun validateComponentJson(jsonString: String): Result<Unit> {
        return try {
            val element = json.parseToJsonElement(jsonString)
            val jsonObject = element.jsonObject
            
            // Basic required fields for a component
            if (!jsonObject.containsKey("id")) return Result.failure(Exception("Missing 'id' field"))
            if (!jsonObject.containsKey("type")) return Result.failure(Exception("Missing 'type' field"))
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
