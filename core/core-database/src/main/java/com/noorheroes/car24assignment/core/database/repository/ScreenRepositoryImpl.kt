package com.noorheroes.car24assignment.core.database.repository

import com.noorheroes.car24assignment.core.database.dao.ComponentDao
import com.noorheroes.car24assignment.core.database.dao.ScreenDao
import com.noorheroes.car24assignment.core.database.dao.SectionDao
import com.noorheroes.car24assignment.core.database.database.SDUIDatabase
import com.noorheroes.car24assignment.core.database.entity.ComponentEntity
import com.noorheroes.car24assignment.core.database.entity.ScreenEntity
import com.noorheroes.car24assignment.core.database.entity.SectionEntity
import com.noorheroes.car24assignment.core.database.mapper.ModelMapper
import com.noorheroes.car24assignment.core.model.domain.Screen
import com.noorheroes.car24assignment.core.model.json.*
import com.noorheroes.car24assignment.core.model.repository.ScreenRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import androidx.room.withTransaction
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScreenRepositoryImpl @Inject constructor(
    private val database: SDUIDatabase,
    private val screenDao: ScreenDao,
    private val sectionDao: SectionDao,
    private val componentDao: ComponentDao,
    private val mapper: ModelMapper,
    private val json: Json
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

    override suspend fun getScreens(): List<Screen> {
        val entities = screenDao.getAllScreens()
        return entities.map { entity ->
            mapper.toScreen(entity, emptyList(), emptyMap())
        }
    }

    override suspend fun getScreen(screenId: String): Screen? {
        return null 
    }

    override suspend fun getScreenJson(screenId: String): String? {
        val screenEntity = screenDao.getScreenByIdSync(screenId) ?: return null
        val sectionEntities = sectionDao.getSectionsByScreenIdSync(screenId)
        val componentsBySection = sectionEntities.associate { 
            it.sectionId to componentDao.getComponentsBySectionIdSync(it.sectionId)
        }
        
        val screenModel = mapper.toScreenModel(screenEntity, sectionEntities, componentsBySection)
        return json.encodeToString(ScreenModel.serializer(), screenModel)
    }

    override suspend fun saveScreen(screen: Screen) {
        // Not used for now
    }

    override suspend fun saveScreenModel(model: ScreenModel) {
        val now = System.currentTimeMillis()
        
        database.withTransaction {
            // 1. Clear existing data for this screen to ensure a clean state
            componentDao.deleteComponentsByScreenId(model.metadata.id)
            sectionDao.deleteSectionsByScreenId(model.metadata.id)

            // 2. Insert Screen
            screenDao.insertScreen(
                ScreenEntity(
                    screenId = model.metadata.id,
                    name = model.metadata.name,
                    description = model.metadata.description,
                    schemaVersion = model.metadata.schemaVersion,
                    rendererVersion = model.metadata.rendererVersion,
                    configurationJson = json.encodeToJsonElement(model.configuration).toString(),
                    themeJson = json.encodeToJsonElement(model.theme).toString(),
                    layoutType = model.layout.type,
                    layoutStyleJson = model.layout.style?.toString(),
                    createdAt = model.metadata.createdAt,
                    updatedAt = now,
                    isActive = true
                )
            )

            // 3. Insert Sections & Components
            model.sections.forEach { section ->
                sectionDao.insertSections(
                    listOf(
                        SectionEntity(
                            sectionId = section.id,
                            screenId = model.metadata.id,
                            type = section.type,
                            title = section.title,
                            displayOrder = section.order,
                            visibility = section.visibility,
                            updatedAt = now
                        )
                    )
                )

                val componentEntities = section.components.mapIndexed { index, component ->
                    val componentType = when (component) {
                        is BannerModel -> "banner"
                        is HeroBannerModel -> "hero_banner"
                        is SearchBarModel -> "search_bar"
                        is CategoriesModel -> "categories"
                        is CarCardModel -> "car_card"
                        is HeaderModel -> "header"
                        is CtaModel -> "cta"
                        is FooterModel -> "footer"
                        is HorizontalRailModel -> "horizontal_rail"
                        is TextModel -> "text"
                        is ImageModel -> "image"
                        is IconModel -> "icon"
                        is BadgeModel -> "badge"
                        is ButtonModel -> "button"
                        is ChipModel -> "chip"
                        is ChipGroupModel -> "chip_group"
                        is DividerModel -> "divider"
                        is SpacerModel -> "spacer"
                        is ColumnModel -> "column"
                        is RowModel -> "row"
                        is BoxModel -> "box"
                        is CardModel -> "card"
                        is CtaSectionModel -> "cta_section"
                        is LazyColumnModel -> "lazy_column"
                        is LazyRowModel -> "lazy_row"
                        is GridModel -> "grid"
                        is UnknownModel -> "unknown"
                    }
                    ComponentEntity(
                        componentId = component.id,
                        sectionId = section.id,
                        componentType = componentType,
                        componentJson = json.encodeToJsonElement(ComponentModel.serializer(), component).toString(),
                        displayOrder = index,
                        version = 1,
                        updatedAt = now
                    )
                }
                componentDao.insertComponents(componentEntities)
            }
        }
    }

    override suspend fun updateScreenMetadata(screenId: String, name: String, description: String?) {
        screenDao.updateScreenMetadata(screenId, name, description)
    }

    override suspend fun updateScreenConfig(screenId: String, configJson: String) {
        screenDao.updateScreenConfig(screenId, configJson)
    }

    override suspend fun resetScreen(screenId: String, assetJson: String) {
        val model = json.decodeFromString<ScreenModel>(assetJson)
        saveScreenModel(model)
    }
}
