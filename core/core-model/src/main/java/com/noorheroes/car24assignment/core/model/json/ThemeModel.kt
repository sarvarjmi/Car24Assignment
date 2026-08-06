package com.noorheroes.car24assignment.core.model.json

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class ThemeModel(
    val colorPalette: JsonObject? = null,
    val typography: JsonObject? = null,
    val spacing: JsonObject? = null,
    val shape: JsonObject? = null,
    val elevation: JsonObject? = null
)
