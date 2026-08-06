package com.noorheroes.car24assignment.core.database.repository

import com.noorheroes.car24assignment.core.database.dao.ScreenDao
import com.noorheroes.car24assignment.core.database.dao.SectionDao
import com.noorheroes.car24assignment.core.database.dao.ComponentDao
import com.noorheroes.car24assignment.core.database.mapper.ModelMapper
import com.noorheroes.car24assignment.core.model.domain.Screen
import com.noorheroes.car24assignment.core.model.repository.ScreenRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScreenRepositoryImpl @Inject constructor(
    private val screenDao: ScreenDao,
    private val sectionDao: SectionDao,
    private val componentDao: ComponentDao,
    private val mapper: ModelMapper
) : ScreenRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeScreen(screenId: String): Flow<Screen?> {
        return screenDao.getScreenById(screenId).flatMapLatest { screenEntity ->
            if (screenEntity == null) return@flatMapLatest flowOf(null)

            sectionDao.getSectionsByScreenId(screenId).flatMapLatest { sections ->
                val sectionFlows = sections.map { section ->
                    componentDao.getComponentsBySectionId(section.sectionId).map { components ->
                        section to components
                    }
                }
                
                if (sectionFlows.isEmpty()) {
                    return@flatMapLatest flowOf(mapper.toScreen(screenEntity, emptyList(), emptyMap()))
                }

                combine(sectionFlows) { sectionPairs ->
                    val sectionEntities = sectionPairs.map { it.first }
                    val componentsBySection = sectionPairs.associate { it.first.sectionId to it.second }
                    mapper.toScreen(screenEntity, sectionEntities, componentsBySection)
                }
            }
        }
    }

    override suspend fun getScreen(screenId: String): Screen? {
        // Implementation for non-flow fetch if needed
        return null // For now
    }

    override suspend fun saveScreen(screen: Screen) {
        // Implementation for saving screen hierarchy if needed
    }
}
