package com.noorheroes.car24assignment.core.model.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
sealed class ComponentModel {
    abstract val id: String
    abstract val componentType: String
    abstract val style: StyleModel?
    abstract val actions: Map<String, ActionModel>?
    abstract val children: List<ComponentModel>?
    abstract val visibility: String
    abstract val state: RuntimeStateModel?

    @Serializable
    @SerialName("banner")
    data class Banner(
        override val id: String,
        override val style: StyleModel? = null,
        override val actions: Map<String, ActionModel>? = null,
        override val children: List<ComponentModel>? = null,
        override val visibility: String = "VISIBLE",
        override val state: RuntimeStateModel? = null,
        val properties: BannerProperties
    ) : ComponentModel() {
        override val componentType: String get() = "banner"
    }

    @Serializable
    @SerialName("hero_banner")
    data class HeroBanner(
        override val id: String,
        override val style: StyleModel? = null,
        override val actions: Map<String, ActionModel>? = null,
        override val children: List<ComponentModel>? = null,
        override val visibility: String = "VISIBLE",
        override val state: RuntimeStateModel? = null,
        val properties: HeroBannerProperties
    ) : ComponentModel() {
        override val componentType: String get() = "hero_banner"
    }

    @Serializable
    @SerialName("search_bar")
    data class SearchBar(
        override val id: String,
        override val style: StyleModel? = null,
        override val actions: Map<String, ActionModel>? = null,
        override val children: List<ComponentModel>? = null,
        override val visibility: String = "VISIBLE",
        override val state: RuntimeStateModel? = null,
        val properties: SearchBarProperties
    ) : ComponentModel() {
        override val componentType: String get() = "search_bar"
    }

    @Serializable
    @SerialName("categories")
    data class Categories(
        override val id: String,
        override val style: StyleModel? = null,
        override val actions: Map<String, ActionModel>? = null,
        override val children: List<ComponentModel>? = null,
        override val visibility: String = "VISIBLE",
        override val state: RuntimeStateModel? = null,
        val properties: CategoriesProperties
    ) : ComponentModel() {
        override val componentType: String get() = "categories"
    }

    @Serializable
    @SerialName("car_card")
    data class CarCard(
        override val id: String,
        override val style: StyleModel? = null,
        override val actions: Map<String, ActionModel>? = null,
        override val children: List<ComponentModel>? = null,
        override val visibility: String = "VISIBLE",
        override val state: RuntimeStateModel? = null,
        val properties: CarCardProperties
    ) : ComponentModel() {
        override val componentType: String get() = "car_card"
    }

    @Serializable
    @SerialName("header")
    data class Header(
        override val id: String,
        override val style: StyleModel? = null,
        override val actions: Map<String, ActionModel>? = null,
        override val children: List<ComponentModel>? = null,
        override val visibility: String = "VISIBLE",
        override val state: RuntimeStateModel? = null,
        val properties: HeaderProperties
    ) : ComponentModel() {
        override val componentType: String get() = "header"
    }

    @Serializable
    @SerialName("cta")
    data class Cta(
        override val id: String,
        override val style: StyleModel? = null,
        override val actions: Map<String, ActionModel>? = null,
        override val children: List<ComponentModel>? = null,
        override val visibility: String = "VISIBLE",
        override val state: RuntimeStateModel? = null,
        val properties: CtaProperties
    ) : ComponentModel() {
        override val componentType: String get() = "cta"
    }

    @Serializable
    @SerialName("footer")
    data class Footer(
        override val id: String,
        override val style: StyleModel? = null,
        override val actions: Map<String, ActionModel>? = null,
        override val children: List<ComponentModel>? = null,
        override val visibility: String = "VISIBLE",
        override val state: RuntimeStateModel? = null,
        val properties: FooterProperties
    ) : ComponentModel() {
        override val componentType: String get() = "footer"
    }

    @Serializable
    @SerialName("horizontal_rail")
    data class HorizontalRail(
        override val id: String,
        override val style: StyleModel? = null,
        override val actions: Map<String, ActionModel>? = null,
        override val children: List<ComponentModel>? = null,
        override val visibility: String = "VISIBLE",
        override val state: RuntimeStateModel? = null,
        val properties: HorizontalRailProperties
    ) : ComponentModel() {
        override val componentType: String get() = "horizontal_rail"
    }
    
    // Add a generic one for unknowns if needed, or handle in JSON
}

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
    val location: String
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
    val text: String
)

@Serializable
data class HorizontalRailProperties(
    val title: String? = null
)
