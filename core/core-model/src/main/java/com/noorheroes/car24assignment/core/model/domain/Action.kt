package com.noorheroes.car24assignment.core.model.domain

data class Action(
    val type: String,
    val payload: Map<String, Any?> = emptyMap()
)
