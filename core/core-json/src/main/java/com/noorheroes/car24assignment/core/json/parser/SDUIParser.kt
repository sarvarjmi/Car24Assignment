package com.noorheroes.car24assignment.core.json.parser

import com.noorheroes.car24assignment.core.model.json.ScreenModel
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SDUIParser @Inject constructor(
    private val json: Json
) {
    fun parseScreen(jsonString: String): ScreenModel {
        return json.decodeFromString(ScreenModel.serializer(), jsonString)
    }

    fun parseComponent(jsonString: String): com.noorheroes.car24assignment.core.model.json.ComponentModel {
        return json.decodeFromString(com.noorheroes.car24assignment.core.model.json.ComponentModel.serializer(), jsonString)
    }
}
