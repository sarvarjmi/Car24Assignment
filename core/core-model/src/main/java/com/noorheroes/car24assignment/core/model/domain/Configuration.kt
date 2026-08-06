package com.noorheroes.car24assignment.core.model.domain

data class Configuration(
    val refreshable: Boolean = true,
    val scrollable: Boolean = true,
    val safeArea: Boolean = true,
    val backgroundColor: String? = null
)
