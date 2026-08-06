package com.noorheroes.car24assignment.core.model.domain

data class Layout(
    val type: String,
    val style: Map<String, Any?> = emptyMap()
)
