package com.noorheroes.car24assignment.core.model.json

import kotlinx.serialization.Serializable

@Serializable
data class MetadataModel(
    val id: String,
    val name: String,
    val schemaVersion: String,
    val rendererVersion: String,
    val createdAt: Long,
    val updatedAt: Long,
    val description: String? = null
)
