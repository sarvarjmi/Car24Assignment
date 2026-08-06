package com.noorheroes.car24assignment.core.model.json

import kotlinx.serialization.Serializable

@Serializable
data class RuntimeStateModel(
    val enabled: Boolean = true,
    val selected: Boolean = false,
    val expanded: Boolean = false,
    val loading: Boolean = false,
    val checked: Boolean = false
)
