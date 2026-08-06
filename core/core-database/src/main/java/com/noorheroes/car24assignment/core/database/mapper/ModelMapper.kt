package com.noorheroes.car24assignment.core.database.mapper

import com.noorheroes.car24assignment.core.common.extensions.toMap
import com.noorheroes.car24assignment.core.database.entity.ComponentEntity
import com.noorheroes.car24assignment.core.database.entity.ScreenEntity
import com.noorheroes.car24assignment.core.database.entity.SectionEntity
import com.noorheroes.car24assignment.core.model.domain.*
import com.noorheroes.car24assignment.core.model.json.ActionModel
import com.noorheroes.car24assignment.core.model.json.ComponentModel
import com.noorheroes.car24assignment.core.model.json.ConfigurationModel
import com.noorheroes.car24assignment.core.model.json.ThemeModel
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModelMapper @Inject constructor(
    private val json: Json
) {
    fun toScreen(
        entity: ScreenEntity,
        sections: List<SectionEntity>,
        componentsBySection: Map<String, List<ComponentEntity>>
    ): Screen {
        val configModel = json.decodeFromString<ConfigurationModel>(entity.configurationJson)
        val themeModel = json.decodeFromString<ThemeModel>(entity.themeJson)
        val styleMap = entity.layoutStyleJson?.let { 
            json.decodeFromString<JsonObject>(it).toMap()
        } ?: emptyMap()

        return Screen(
            metadata = Metadata(
                id = entity.screenId,
                name = entity.name,
                schemaVersion = entity.schemaVersion,
                rendererVersion = entity.rendererVersion,
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt,
                description = entity.description
            ),
            configuration = Configuration(
                refreshable = configModel.refreshable,
                scrollable = configModel.scrollable,
                safeArea = configModel.safeArea,
                backgroundColor = configModel.backgroundColor
            ),
            theme = Theme(
                colorPalette = themeModel.colorPalette?.toMap()?.mapValues { it.value.toString() } ?: emptyMap(),
                typography = themeModel.typography?.toMap()?.mapValues { it.value.toString() } ?: emptyMap(),
                spacing = themeModel.spacing?.toMap()?.mapValues { (it.value as? Number)?.toInt() ?: 0 } ?: emptyMap()
            ),
            layout = Layout(
                type = entity.layoutType,
                style = styleMap
            ),
            sections = sections.map { sectionEntity ->
                toSection(sectionEntity, componentsBySection[sectionEntity.sectionId] ?: emptyList())
            }
        )
    }

    fun toSection(entity: SectionEntity, components: List<ComponentEntity>): Section {
        return Section(
            id = entity.sectionId,
            type = entity.type,
            title = entity.title,
            order = entity.displayOrder,
            components = components.map { toComponent(it) },
            visibility = entity.visibility == "VISIBLE"
        )
    }

    fun toComponent(entity: ComponentEntity): Component {
        val model = json.decodeFromString<ComponentModel>(entity.componentJson)
        return Component(
            id = entity.componentId,
            type = entity.componentType,
            properties = model.properties?.toMap() ?: emptyMap(),
            actions = model.actions?.mapValues { toAction(it.value) } ?: emptyMap(),
            children = model.children?.map { toComponentFromModel(it) }
        )
    }

    private fun toComponentFromModel(model: ComponentModel): Component {
        return Component(
            id = model.id,
            type = model.type,
            properties = model.properties?.toMap() ?: emptyMap(),
            actions = model.actions?.mapValues { toAction(it.value) } ?: emptyMap(),
            children = model.children?.map { toComponentFromModel(it) }
        )
    }

    private fun toAction(model: ActionModel): Action {
        return Action(
            type = model.type,
            payload = model.payload?.toMap() ?: emptyMap()
        )
    }
}
