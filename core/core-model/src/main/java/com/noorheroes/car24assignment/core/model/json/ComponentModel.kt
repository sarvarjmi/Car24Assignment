package com.noorheroes.car24assignment.core.model.json

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class ComponentModel(
    val id: String,
    val type: String,
    val properties: JsonObject? = null,
    val actions: Map<String, ActionModel>? = null,
    val children: List<ComponentModel>? = null
)
