package com.noorheroes.car24assignment.core.database.mapper

import com.noorheroes.car24assignment.core.common.extensions.toMap
import com.noorheroes.car24assignment.core.common.extensions.toMapValue
import com.noorheroes.car24assignment.core.database.entity.ComponentEntity
import com.noorheroes.car24assignment.core.database.entity.ScreenEntity
import com.noorheroes.car24assignment.core.database.entity.SectionEntity
import com.noorheroes.car24assignment.core.model.domain.*
import com.noorheroes.car24assignment.core.model.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
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
        return try {
            val model = json.decodeFromString<ComponentModel>(entity.componentJson)
            toComponentFromModel(model)
        } catch (e: Exception) {
            Component.Unknown(id = entity.componentId, type = entity.componentType, properties = emptyMap())
        }
    }

    private fun toComponentFromModel(model: ComponentModel): Component {
        val style = model.style?.let { toStyle(it) }
        val actions = model.actions?.mapValues { toAction(it.value) } ?: emptyMap()
        val children = model.children?.map { toComponentFromModel(it) }
        val visibility = model.visibility == "VISIBLE"
        val state = model.state?.let { toRuntimeState(it) }

        val propertiesMap = json.encodeToJsonElement(ComponentModel.serializer(), model)
            .jsonObject["properties"]?.jsonObject?.toMap() ?: emptyMap()

        return when (model) {
            is ComponentModel.Banner -> Component.Banner(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap,
                imageUrl = model.properties.imageUrl, title = model.properties.title, subtitle = model.properties.subtitle
            )
            is ComponentModel.HeroBanner -> Component.HeroBanner(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap,
                imageUrl = model.properties.imageUrl, title = model.properties.title, subtitle = model.properties.subtitle, ctaText = model.properties.ctaText
            )
            is ComponentModel.SearchBar -> Component.SearchBar(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap,
                placeholder = model.properties.placeholder
            )
            is ComponentModel.Categories -> Component.Categories(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap,
                items = model.properties.items.map { Component.CategoryItem(it.id, it.label, it.icon) }
            )
            is ComponentModel.CarCard -> Component.CarCard(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap,
                imageUrl = model.properties.imageUrl, title = model.properties.title, price = model.properties.price, location = model.properties.location
            )
            is ComponentModel.Header -> Component.Header(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap,
                title = model.properties.title, subtitle = model.properties.subtitle
            )
            is ComponentModel.Cta -> Component.Cta(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap,
                text = model.properties.text
            )
            is ComponentModel.Footer -> Component.Footer(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap,
                text = model.properties.text
            )
            is ComponentModel.HorizontalRail -> Component.HorizontalRail(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap,
                title = model.properties.title
            )
        }
    }

    private fun toRuntimeState(model: RuntimeStateModel): RuntimeState {
        return RuntimeState(
            enabled = model.enabled,
            selected = model.selected,
            expanded = model.expanded,
            loading = model.loading,
            checked = model.checked
        )
    }

    private fun toStyle(model: StyleModel): Style {
        return Style(
            padding = model.padding,
            margin = model.margin,
            background = model.background,
            typography = model.typography,
            shape = model.shape,
            alpha = model.alpha
        )
    }

    private fun toAction(model: ActionModel): Action {
        return Action(
            id = model.id,
            type = model.type,
            target = model.target,
            payload = model.payload?.toMap() ?: emptyMap(),
            conditions = model.conditions?.map { toCondition(it) } ?: emptyList(),
            priority = model.priority
        )
    }

    private fun toCondition(model: ConditionModel): Condition {
        return Condition(
            field = model.field,
            operator = model.operator,
            value = model.value?.toMapValue()
        )
    }
}
