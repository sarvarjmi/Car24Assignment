package com.noorheroes.car24assignment.core.database.mapper

import com.noorheroes.car24assignment.core.common.extensions.toMap
import com.noorheroes.car24assignment.core.common.extensions.toMapValue
import com.noorheroes.car24assignment.core.common.logging.Logger
import com.noorheroes.car24assignment.core.database.entity.ComponentEntity
import com.noorheroes.car24assignment.core.database.entity.ScreenEntity
import com.noorheroes.car24assignment.core.database.entity.SectionEntity
import com.noorheroes.car24assignment.core.model.domain.*
import com.noorheroes.car24assignment.core.model.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModelMapper @Inject constructor(
    private val json: Json,
    private val logger: Logger
) {
    private val TAG = "ModelMapper"

    fun toScreen(
        entity: ScreenEntity,
        sections: List<SectionEntity>,
        componentsBySection: Map<String, List<ComponentEntity>>
    ): Screen {
        val jsonModel = toScreenModel(entity, sections, componentsBySection)
        
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
                refreshable = jsonModel.configuration.refreshable,
                scrollable = jsonModel.configuration.scrollable,
                safeArea = jsonModel.configuration.safeArea,
                backgroundColor = jsonModel.configuration.backgroundColor
            ),
            theme = Theme(
                colorPalette = jsonModel.theme.colorPalette?.toMap()?.mapValues { it.value.toString() } ?: emptyMap(),
                typography = jsonModel.theme.typography?.toMap()?.mapValues { it.value.toString() } ?: emptyMap(),
                spacing = jsonModel.theme.spacing?.toMap()?.mapValues { (it.value as? Number)?.toInt() ?: 0 } ?: emptyMap()
            ),
            layout = Layout(
                type = entity.layoutType,
                style = jsonModel.layout.style?.toMap() ?: emptyMap()
            ),
            sections = sections.map { sectionEntity ->
                toSection(sectionEntity, componentsBySection[sectionEntity.sectionId] ?: emptyList())
            }
        )
    }

    fun toScreenModel(
        entity: ScreenEntity,
        sections: List<SectionEntity>,
        componentsBySection: Map<String, List<ComponentEntity>>
    ): ScreenModel {
        val configModel = json.decodeFromString<ConfigurationModel>(entity.configurationJson)
        val themeModel = json.decodeFromString<ThemeModel>(entity.themeJson)
        val layoutStyle = entity.layoutStyleJson?.let { 
            try { json.decodeFromString<JsonObject>(it) } catch (e: Exception) { null }
        }

        return ScreenModel(
            metadata = MetadataModel(
                id = entity.screenId,
                name = entity.name,
                schemaVersion = entity.schemaVersion,
                rendererVersion = entity.rendererVersion,
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt,
                description = entity.description
            ),
            configuration = configModel,
            theme = themeModel,
            layout = LayoutModel(
                type = entity.layoutType,
                style = layoutStyle
            ),
            sections = sections.map { sectionEntity ->
                val components = componentsBySection[sectionEntity.sectionId] ?: emptyList()
                SectionModel(
                    id = sectionEntity.sectionId,
                    type = sectionEntity.type,
                    title = sectionEntity.title,
                    order = sectionEntity.displayOrder,
                    visibility = sectionEntity.visibility,
                    components = components.map { 
                        try {
                            json.decodeFromString<ComponentModel>(it.componentJson)
                        } catch (e: Exception) {
                            logger.e(TAG, "Error decoding component JSON for ${it.componentId}: ${e.message}")
                            UnknownModel(id = it.componentId)
                        }
                    }
                )
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
            logger.w(TAG, "Error mapping component ${entity.componentId}, falling back to Unknown: ${e.message}")
            Component.Unknown(id = entity.componentId, type = entity.componentType, properties = emptyMap())
        }
    }

    private fun toComponentFromModel(model: ComponentModel): Component {
        val style = model.style?.let { toStyle(it) }
        val actions = model.actions?.mapValues { toAction(it.value) } ?: emptyMap()
        val children = model.children?.map { toComponentFromModel(it) }
        val visibility = model.visibility
        val state = model.state?.let { toRuntimeState(it) }

        val jsonElement = json.encodeToJsonElement(ComponentModel.serializer(), model)
        val propertiesMap = (jsonElement as? JsonObject)?.get("properties")?.let { 
            if (it is JsonObject) it.toMap() else emptyMap<String, Any?>() 
        } ?: emptyMap()

        return when (model) {
            is BannerModel -> Component.Banner(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap,
                imageUrl = model.properties.imageUrl, title = model.properties.title, subtitle = model.properties.subtitle
            )
            is HeroBannerModel -> Component.HeroBanner(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap,
                imageUrl = model.properties.imageUrl, title = model.properties.title, subtitle = model.properties.subtitle, ctaText = model.properties.ctaText
            )
            is SearchBarModel -> Component.SearchBar(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap,
                placeholder = model.properties.placeholder
            )
            is CategoriesModel -> Component.Categories(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap,
                items = model.properties.items.map { Component.CategoryItem(it.id, it.label, it.icon) }
            )
            is CarCardModel -> Component.CarCard(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap,
                imageUrl = model.properties.imageUrl, title = model.properties.title, price = model.properties.price, location = model.properties.location
            )
            is HeaderModel -> Component.Header(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap,
                title = model.properties.title, subtitle = model.properties.subtitle
            )
            is CtaModel -> Component.Cta(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap,
                text = model.properties.text
            )
            is FooterModel -> Component.Footer(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap,
                text = model.properties.text, copyright = model.properties.copyright, version = model.properties.version
            )
            is HorizontalRailModel -> Component.HorizontalRail(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap,
                title = model.properties.title
            )
            is TextModel -> Component.Text(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap,
                text = model.properties.text
            )
            is ImageModel -> Component.Image(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap,
                url = model.properties.url
            )
            is IconModel -> Component.Icon(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap,
                icon = model.properties.icon
            )
            is BadgeModel -> Component.Badge(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap,
                text = model.properties.text
            )
            is ButtonModel -> Component.Button(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap,
                text = model.properties.text
            )
            is ChipModel -> Component.Chip(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap,
                text = model.properties.text
            )
            is ChipGroupModel -> Component.ChipGroup(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap
            )
            is DividerModel -> Component.Divider(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap
            )
            is SpacerModel -> Component.Spacer(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap
            )
            is ColumnModel -> Component.Column(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap
            )
            is RowModel -> Component.Row(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap
            )
            is BoxModel -> Component.Box(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap
            )
            is CardModel -> Component.Card(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap
            )
            is CtaSectionModel -> Component.CtaSection(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap
            )
            is LazyColumnModel -> Component.LazyColumn(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap
            )
            is LazyRowModel -> Component.LazyRow(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap
            )
            is GridModel -> Component.Grid(
                id = model.id, style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap,
                columns = model.properties.columns
            )
            is UnknownModel -> Component.Unknown(
                id = model.id, type = "unknown", style = style, actions = actions, children = children, visibility = visibility, state = state, properties = propertiesMap
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
            alpha = model.alpha,
            elevation = model.elevation,
            border = model.border,
            width = model.width,
            height = model.height,
            arrangement = model.arrangement,
            alignment = model.alignment,
            layoutParams = model.layoutParams
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
