package com.noorheroes.car24assignment.core.json.parser

import com.noorheroes.car24assignment.core.model.json.ScreenModel
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Central JSON parsing engine for the SDUI platform.
 * Responsible for deserializing raw JSON strings into type-safe models using Kotlinx Serialization.
 */
@Singleton
class SDUIParser @Inject constructor(
    private val json: Json
) {
    /**
     * Parses a complete screen payload.
     * @param jsonString The raw JSON of the screen.
     * @return A [ScreenModel] hierarchy.
     */
    fun parseScreen(jsonString: String): ScreenModel {
        return json.decodeFromString(ScreenModel.serializer(), jsonString)
    }

    /**
     * Parses a single component payload using polymorphic serialization.
     * @param jsonString The raw JSON of the component.
     * @return A typed [com.noorheroes.car24assignment.core.model.json.ComponentModel] (e.g., Banner, CarCard).
     */
    fun parseComponent(jsonString: String): com.noorheroes.car24assignment.core.model.json.ComponentModel {
        return json.decodeFromString(com.noorheroes.car24assignment.core.model.json.ComponentModel.serializer(), jsonString)
    }
}
