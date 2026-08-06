package com.noorheroes.car24assignment.core.model.domain

data class Theme(
    val colorPalette: Map<String, String> = emptyMap(),
    val typography: Map<String, String> = emptyMap(),
    val spacing: Map<String, Int> = emptyMap()
)
