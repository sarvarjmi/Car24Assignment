package com.noorheroes.car24assignment.core.model.repository

import com.noorheroes.car24assignment.core.model.domain.Component
import kotlinx.coroutines.flow.Flow

interface ComponentRepository {
    fun observeComponent(componentId: String): Flow<Component?>
    suspend fun getComponentJson(componentId: String): String?
    suspend fun updateComponentJson(componentId: String, json: String)
}
