package com.noorheroes.car24assignment.core.model.json

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
enum class ConditionOperator {
    EQUALS, NOT_EQUALS, GREATER_THAN, LESS_THAN, IN, NOT_IN, EXISTS
}

@Serializable
data class ConditionModel(
    val field: String,
    val operator: ConditionOperator,
    val value: JsonElement? = null
)
