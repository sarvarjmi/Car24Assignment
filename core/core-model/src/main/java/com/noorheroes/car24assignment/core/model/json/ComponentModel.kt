package com.noorheroes.car24assignment.core.model.json

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
enum class VisibilityToken {
    VISIBLE, HIDDEN, GONE
}

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@kotlinx.serialization.json.JsonClassDiscriminator("type")
sealed class ComponentModel {
    abstract val id: String
    abstract val style: StyleModel?
    abstract val actions: Map<String, ActionModel>?
    abstract val children: List<ComponentModel>?
    abstract val visibility: VisibilityToken
    abstract val state: RuntimeStateModel?
}

@Serializable
@SerialName("banner")
data class BannerModel(
    override val id: String,
    override val style: StyleModel? = null,
    override val actions: Map<String, ActionModel>? = null,
    override val children: List<ComponentModel>? = null,
    override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
    override val state: RuntimeStateModel? = null,
    val properties: BannerProperties
) : ComponentModel()

@Serializable
@SerialName("hero_banner")
data class HeroBannerModel(
    override val id: String,
    override val style: StyleModel? = null,
    override val actions: Map<String, ActionModel>? = null,
    override val children: List<ComponentModel>? = null,
    override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
    override val state: RuntimeStateModel? = null,
    val properties: HeroBannerProperties
) : ComponentModel()

@Serializable
@SerialName("search_bar")
data class SearchBarModel(
    override val id: String,
    override val style: StyleModel? = null,
    override val actions: Map<String, ActionModel>? = null,
    override val children: List<ComponentModel>? = null,
    override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
    override val state: RuntimeStateModel? = null,
    val properties: SearchBarProperties
) : ComponentModel()

@Serializable
@SerialName("categories")
data class CategoriesModel(
    override val id: String,
    override val style: StyleModel? = null,
    override val actions: Map<String, ActionModel>? = null,
    override val children: List<ComponentModel>? = null,
    override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
    override val state: RuntimeStateModel? = null,
    val properties: CategoriesProperties
) : ComponentModel()

@Serializable
@SerialName("car_card")
data class CarCardModel(
    override val id: String,
    override val style: StyleModel? = null,
    override val actions: Map<String, ActionModel>? = null,
    override val children: List<ComponentModel>? = null,
    override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
    override val state: RuntimeStateModel? = null,
    val properties: CarCardProperties
) : ComponentModel()

@Serializable
@SerialName("header")
data class HeaderModel(
    override val id: String,
    override val style: StyleModel? = null,
    override val actions: Map<String, ActionModel>? = null,
    override val children: List<ComponentModel>? = null,
    override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
    override val state: RuntimeStateModel? = null,
    val properties: HeaderProperties
) : ComponentModel()

@Serializable
@SerialName("cta")
data class CtaModel(
    override val id: String,
    override val style: StyleModel? = null,
    override val actions: Map<String, ActionModel>? = null,
    override val children: List<ComponentModel>? = null,
    override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
    override val state: RuntimeStateModel? = null,
    val properties: CtaProperties
) : ComponentModel()

@Serializable
@SerialName("footer")
data class FooterModel(
    override val id: String,
    override val style: StyleModel? = null,
    override val actions: Map<String, ActionModel>? = null,
    override val children: List<ComponentModel>? = null,
    override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
    override val state: RuntimeStateModel? = null,
    val properties: FooterProperties
) : ComponentModel()

@Serializable
@SerialName("horizontal_rail")
data class HorizontalRailModel(
    override val id: String,
    override val style: StyleModel? = null,
    override val actions: Map<String, ActionModel>? = null,
    override val children: List<ComponentModel>? = null,
    override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
    override val state: RuntimeStateModel? = null,
    val properties: HorizontalRailProperties
) : ComponentModel()

@Serializable
@SerialName("text")
data class TextModel(
    override val id: String,
    override val style: StyleModel? = null,
    override val actions: Map<String, ActionModel>? = null,
    override val children: List<ComponentModel>? = null,
    override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
    override val state: RuntimeStateModel? = null,
    val properties: TextProperties
) : ComponentModel()

@Serializable
@SerialName("image")
data class ImageModel(
    override val id: String,
    override val style: StyleModel? = null,
    override val actions: Map<String, ActionModel>? = null,
    override val children: List<ComponentModel>? = null,
    override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
    override val state: RuntimeStateModel? = null,
    val properties: ImageProperties
) : ComponentModel()

@Serializable
@SerialName("icon")
data class IconModel(
    override val id: String,
    override val style: StyleModel? = null,
    override val actions: Map<String, ActionModel>? = null,
    override val children: List<ComponentModel>? = null,
    override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
    override val state: RuntimeStateModel? = null,
    val properties: IconProperties
) : ComponentModel()

@Serializable
@SerialName("badge")
data class BadgeModel(
    override val id: String,
    override val style: StyleModel? = null,
    override val actions: Map<String, ActionModel>? = null,
    override val children: List<ComponentModel>? = null,
    override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
    override val state: RuntimeStateModel? = null,
    val properties: BadgeProperties
) : ComponentModel()

@Serializable
@SerialName("button")
data class ButtonModel(
    override val id: String,
    override val style: StyleModel? = null,
    override val actions: Map<String, ActionModel>? = null,
    override val children: List<ComponentModel>? = null,
    override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
    override val state: RuntimeStateModel? = null,
    val properties: ButtonProperties
) : ComponentModel()

@Serializable
@SerialName("chip")
data class ChipModel(
    override val id: String,
    override val style: StyleModel? = null,
    override val actions: Map<String, ActionModel>? = null,
    override val children: List<ComponentModel>? = null,
    override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
    override val state: RuntimeStateModel? = null,
    val properties: ChipProperties
) : ComponentModel()

@Serializable
@SerialName("chip_group")
data class ChipGroupModel(
    override val id: String,
    override val style: StyleModel? = null,
    override val actions: Map<String, ActionModel>? = null,
    override val children: List<ComponentModel>? = null,
    override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
    override val state: RuntimeStateModel? = null,
    val properties: JsonObject? = null
) : ComponentModel()

@Serializable
@SerialName("divider")
data class DividerModel(
    override val id: String,
    override val style: StyleModel? = null,
    override val actions: Map<String, ActionModel>? = null,
    override val children: List<ComponentModel>? = null,
    override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
    override val state: RuntimeStateModel? = null,
    val properties: DividerProperties = DividerProperties()
) : ComponentModel()

@Serializable
@SerialName("spacer")
data class SpacerModel(
    override val id: String,
    override val style: StyleModel? = null,
    override val actions: Map<String, ActionModel>? = null,
    override val children: List<ComponentModel>? = null,
    override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
    override val state: RuntimeStateModel? = null,
    val properties: SpacerProperties
) : ComponentModel()

@Serializable
@SerialName("column")
data class ColumnModel(
    override val id: String,
    override val style: StyleModel? = null,
    override val actions: Map<String, ActionModel>? = null,
    override val children: List<ComponentModel>? = null,
    override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
    override val state: RuntimeStateModel? = null,
    val properties: JsonObject? = null
) : ComponentModel()

@Serializable
@SerialName("row")
data class RowModel(
    override val id: String,
    override val style: StyleModel? = null,
    override val actions: Map<String, ActionModel>? = null,
    override val children: List<ComponentModel>? = null,
    override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
    override val state: RuntimeStateModel? = null,
    val properties: JsonObject? = null
) : ComponentModel()

@Serializable
@SerialName("box")
data class BoxModel(
    override val id: String,
    override val style: StyleModel? = null,
    override val actions: Map<String, ActionModel>? = null,
    override val children: List<ComponentModel>? = null,
    override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
    override val state: RuntimeStateModel? = null,
    val properties: JsonObject? = null
) : ComponentModel()

@Serializable
@SerialName("card")
data class CardModel(
    override val id: String,
    override val style: StyleModel? = null,
    override val actions: Map<String, ActionModel>? = null,
    override val children: List<ComponentModel>? = null,
    override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
    override val state: RuntimeStateModel? = null,
    val properties: JsonObject? = null
) : ComponentModel()

@Serializable
@SerialName("cta_section")
data class CtaSectionModel(
    override val id: String,
    override val style: StyleModel? = null,
    override val actions: Map<String, ActionModel>? = null,
    override val children: List<ComponentModel>? = null,
    override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
    override val state: RuntimeStateModel? = null,
    val properties: JsonObject? = null
) : ComponentModel()

@Serializable
@SerialName("lazy_column")
data class LazyColumnModel(
    override val id: String,
    override val style: StyleModel? = null,
    override val actions: Map<String, ActionModel>? = null,
    override val children: List<ComponentModel>? = null,
    override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
    override val state: RuntimeStateModel? = null,
    val properties: JsonObject? = null
) : ComponentModel()

@Serializable
@SerialName("lazy_row")
data class LazyRowModel(
    override val id: String,
    override val style: StyleModel? = null,
    override val actions: Map<String, ActionModel>? = null,
    override val children: List<ComponentModel>? = null,
    override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
    override val state: RuntimeStateModel? = null,
    val properties: JsonObject? = null
) : ComponentModel()

@Serializable
@SerialName("grid")
data class GridModel(
    override val id: String,
    override val style: StyleModel? = null,
    override val actions: Map<String, ActionModel>? = null,
    override val children: List<ComponentModel>? = null,
    override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
    override val state: RuntimeStateModel? = null,
    val properties: GridProperties
) : ComponentModel()

@Serializable
@SerialName("unknown")
data class UnknownModel(
    override val id: String,
    override val style: StyleModel? = null,
    override val actions: Map<String, ActionModel>? = null,
    override val children: List<ComponentModel>? = null,
    override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
    override val state: RuntimeStateModel? = null,
    val properties: JsonObject? = null
) : ComponentModel()

@Serializable
data class BannerProperties(
    val imageUrl: String,
    val title: String? = null,
    val subtitle: String? = null
)

@Serializable
data class HeroBannerProperties(
    val imageUrl: String,
    val title: String? = null,
    val subtitle: String? = null,
    val ctaText: String? = null
)

@Serializable
data class SearchBarProperties(
    val placeholder: String = "Search..."
)

@Serializable
data class CategoriesProperties(
    val items: List<CategoryItemModel>
)

@Serializable
data class CategoryItemModel(
    val id: String,
    val label: String,
    val icon: String? = null
)

@Serializable
data class CarCardProperties(
    val imageUrl: String,
    val title: String,
    val price: String,
    val location: String,
    val badges: List<String>? = null,
    val fuel: String? = null,
    val transmission: String? = null
)

@Serializable
data class HeaderProperties(
    val title: String,
    val subtitle: String? = null
)

@Serializable
data class CtaProperties(
    val text: String
)

@Serializable
data class FooterProperties(
    val text: String,
    val copyright: String? = null,
    val version: String? = null
)

@Serializable
data class HorizontalRailProperties(
    val title: String? = null
)

@Serializable
data class TextProperties(
    val text: String,
    val typography: String? = null,
    val color: String? = null,
    val maxLines: Int? = null,
    val textAlign: String? = null
)

@Serializable
data class ImageProperties(
    val url: String,
    val contentScale: String? = null
)

@Serializable
data class IconProperties(
    val icon: String,
    val tint: String? = null
)

@Serializable
data class BadgeProperties(
    val text: String
)

@Serializable
data class ButtonProperties(
    val text: String
)

@Serializable
data class ChipProperties(
    val text: String
)

@Serializable
data class DividerProperties(
    val thickness: String? = null,
    val color: String? = null
)

@Serializable
data class SpacerProperties(
    val height: String? = null,
    val width: String? = null
)

@Serializable
data class GridProperties(
    val columns: Int = 2
)
