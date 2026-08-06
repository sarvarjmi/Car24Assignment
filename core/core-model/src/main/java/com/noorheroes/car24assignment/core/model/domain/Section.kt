package com.noorheroes.car24assignment.core.model.domain

data class Section(
    val id: String,
    val type: String,
    val title: String? = null,
    val components: List<Component> = emptyList(),
    val visibility: Boolean = true
)
