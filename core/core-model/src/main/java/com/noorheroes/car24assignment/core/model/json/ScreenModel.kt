package com.noorheroes.car24assignment.core.model.json

import kotlinx.serialization.Serializable

@Serializable
data class ScreenModel(
    val id: String,
    val title: String,
    val version: Int,
    val components: List<ComponentModel>
)
