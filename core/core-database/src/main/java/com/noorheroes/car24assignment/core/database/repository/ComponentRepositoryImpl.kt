package com.noorheroes.car24assignment.core.database.repository

import com.noorheroes.car24assignment.core.database.dao.ComponentDao
import com.noorheroes.car24assignment.core.database.mapper.ModelMapper
import com.noorheroes.car24assignment.core.model.domain.Component
import com.noorheroes.car24assignment.core.model.repository.ComponentRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComponentRepositoryImpl @Inject constructor(
    private val componentDao: ComponentDao,
    private val mapper: ModelMapper
) : ComponentRepository {

    override fun observeComponent(componentId: String): Flow<Component?> {
        return componentDao.getComponentById(componentId).map { entity ->
            entity?.let { mapper.toComponent(it) }
        }
    }

    override suspend fun getComponentJson(componentId: String): String? {
        return componentDao.getComponentJsonById(componentId)
    }

    override suspend fun updateComponentJson(componentId: String, json: String) {
        componentDao.updateComponentJson(componentId, json, System.currentTimeMillis())
    }
}
