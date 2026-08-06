package com.noorheroes.car24assignment.core.database.mapper

import com.noorheroes.car24assignment.core.common.extensions.toMap
import com.noorheroes.car24assignment.core.database.entity.ComponentEntity
import com.noorheroes.car24assignment.core.database.entity.ScreenEntity
import com.noorheroes.car24assignment.core.database.entity.SectionEntity
import com.noorheroes.car24assignment.core.model.domain.Action
import com.noorheroes.car24assignment.core.model.domain.Component
import com.noorheroes.car24assignment.core.model.domain.Screen
import com.noorheroes.car24assignment.core.model.domain.Section
import com.noorheroes.car24assignment.core.model.json.ActionModel
import com.noorheroes.car24assignment.core.model.json.ComponentModel
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
        return Screen(
            id = entity.screenId,
            title = entity.name,
            version = entity.schemaVersion,
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
            components = components.map { toComponent(it) },
            visibility = entity.visibility
        )
    }

    fun toComponent(entity: ComponentEntity): Component {
        val model = json.decodeFromString<ComponentModel>(entity.componentJson)
        return Component(
            id = entity.componentId,
            type = entity.componentType,
            properties = model.properties?.toMap() ?: emptyMap(),
            actions = model.actions?.mapValues { toAction(it.value) } ?: emptyMap(),
            children = model.children?.map { childModel ->
                // This is a bit tricky since children in ComponentModel are DTOs
                // We'll recursively map them
                toComponentFromModel(childModel)
            }
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
