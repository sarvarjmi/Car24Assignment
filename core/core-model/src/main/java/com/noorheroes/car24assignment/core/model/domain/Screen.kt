package com.noorheroes.car24assignment.core.model.domain

data class Screen(
    val id: String,
    val title: String,
    val version: Int,
    val sections: List<Section> = emptyList()
)
