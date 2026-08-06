package com.noorheroes.car24assignment.core.model.domain

data class Action(
    val id: String = "",
    val type: String,
    val target: String? = null,
    val payload: Map<String, Any?> = emptyMap(),
    val conditions: List<Condition> = emptyList(),
    val priority: Int = 0
)
