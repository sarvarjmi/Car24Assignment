package com.noorheroes.car24assignment.core.model.domain

data class RuntimeState(
    val enabled: Boolean = true,
    val selected: Boolean = false,
    val expanded: Boolean = false,
    val loading: Boolean = false,
    val checked: Boolean = false
)
