package com.noorheroes.car24assignment.core.model.domain

data class Metadata(
    val id: String,
    val name: String,
    val schemaVersion: String,
    val rendererVersion: String,
    val createdAt: Long,
    val updatedAt: Long,
    val description: String? = null
)
