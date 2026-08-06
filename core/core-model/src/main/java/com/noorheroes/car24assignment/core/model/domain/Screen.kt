package com.noorheroes.car24assignment.core.model.domain

data class Screen(
    val metadata: Metadata,
    val configuration: Configuration = Configuration(),
    val theme: Theme = Theme(),
    val layout: Layout,
    val sections: List<Section> = emptyList()
)
