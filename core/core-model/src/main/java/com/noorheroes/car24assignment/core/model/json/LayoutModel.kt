package com.noorheroes.car24assignment.core.model.json

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class LayoutModel(
    val type: String,
    val style: JsonObject? = null
)
