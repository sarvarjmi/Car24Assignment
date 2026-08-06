package com.noorheroes.car24assignment.core.model.domain

data class Component(
    val id: String,
    val type: String,
    val properties: Map<String, Any?> = emptyMap(),
    val actions: Map<String, Action> = emptyMap(),
    val children: List<Component>? = null
)
