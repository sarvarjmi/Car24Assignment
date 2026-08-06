package com.noorheroes.car24assignment.core.model.json

import kotlinx.serialization.Serializable

@Serializable
data class SectionModel(
    val id: String,
    val type: String,
    val title: String? = null,
    val order: Int,
    val visibility: String = "VISIBLE",
    val components: List<ComponentModel> = emptyList()
)
