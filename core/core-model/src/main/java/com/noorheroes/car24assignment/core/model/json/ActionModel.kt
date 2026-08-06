package com.noorheroes.car24assignment.core.model.json

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class ActionModel(
    val id: String = "",
    val type: String,
    val target: String? = null,
    val payload: JsonObject? = null,
    val conditions: List<ConditionModel>? = null,
    val priority: Int = 0
)
