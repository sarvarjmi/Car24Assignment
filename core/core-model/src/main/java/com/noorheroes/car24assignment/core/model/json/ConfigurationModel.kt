package com.noorheroes.car24assignment.core.model.json

import kotlinx.serialization.Serializable

@Serializable
data class ConfigurationModel(
    val refreshable: Boolean = true,
    val cacheable: Boolean = true,
    val scrollable: Boolean = true,
    val orientation: String = "portrait",
    val safeArea: Boolean = true,
    val backgroundColor: String? = null,
    val statusBarColor: String? = null,
    val navigationBarColor: String? = null,
    val animationEnabled: Boolean = true
)
