package com.noorheroes.car24assignment.core.model.json

import kotlinx.serialization.Serializable

@Serializable
data class ScreenModel(
    val metadata: MetadataModel,
    val configuration: ConfigurationModel = ConfigurationModel(),
    val theme: ThemeModel = ThemeModel(),
    val layout: LayoutModel,
    val sections: List<SectionModel>
)
