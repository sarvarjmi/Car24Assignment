package com.noorheroes.car24assignment.core.model.domain

import com.noorheroes.car24assignment.core.model.json.*

sealed class Component {
    abstract val id: String
    abstract val type: String
    abstract val style: Style?
    abstract val actions: Map<String, Action>
    abstract val children: List<Component>?
    abstract val visibility: Boolean

    data class Banner(
        override val id: String,
        override val style: Style? = null,
        override val actions: Map<String, Action> = emptyMap(),
        override val children: List<Component>? = null,
        override val visibility: Boolean = true,
        val imageUrl: String,
        val title: String? = null,
        val subtitle: String? = null
    ) : Component() {
        override val type: String = "banner"
    }

    data class HeroBanner(
        override val id: String,
        override val style: Style? = null,
        override val actions: Map<String, Action> = emptyMap(),
        override val children: List<Component>? = null,
        override val visibility: Boolean = true,
        val imageUrl: String,
        val title: String? = null,
        val subtitle: String? = null,
        val ctaText: String? = null
    ) : Component() {
        override val type: String = "hero_banner"
    }

    data class SearchBar(
        override val id: String,
        override val style: Style? = null,
        override val actions: Map<String, Action> = emptyMap(),
        override val children: List<Component>? = null,
        override val visibility: Boolean = true,
        val placeholder: String
    ) : Component() {
        override val type: String = "search_bar"
    }

    data class Categories(
        override val id: String,
        override val style: Style? = null,
        override val actions: Map<String, Action> = emptyMap(),
        override val children: List<Component>? = null,
        override val visibility: Boolean = true,
        val items: List<CategoryItem>
    ) : Component() {
        override val type: String = "categories"
    }

    data class CategoryItem(
        val id: String,
        val label: String,
        val icon: String? = null
    )

    data class CarCard(
        override val id: String,
        override val style: Style? = null,
        override val actions: Map<String, Action> = emptyMap(),
        override val children: List<Component>? = null,
        override val visibility: Boolean = true,
        val imageUrl: String,
        val title: String,
        val price: String,
        val location: String
    ) : Component() {
        override val type: String = "car_card"
    }

    data class Header(
        override val id: String,
        override val style: Style? = null,
        override val actions: Map<String, Action> = emptyMap(),
        override val children: List<Component>? = null,
        override val visibility: Boolean = true,
        val title: String,
        val subtitle: String? = null
    ) : Component() {
        override val type: String = "header"
    }

    data class Cta(
        override val id: String,
        override val style: Style? = null,
        override val actions: Map<String, Action> = emptyMap(),
        override val children: List<Component>? = null,
        override val visibility: Boolean = true,
        val text: String
    ) : Component() {
        override val type: String = "cta"
    }

    data class Footer(
        override val id: String,
        override val style: Style? = null,
        override val actions: Map<String, Action> = emptyMap(),
        override val children: List<Component>? = null,
        override val visibility: Boolean = true,
        val text: String
    ) : Component() {
        override val type: String = "footer"
    }

    data class HorizontalRail(
        override val id: String,
        override val style: Style? = null,
        override val actions: Map<String, Action> = emptyMap(),
        override val children: List<Component>? = null,
        override val visibility: Boolean = true,
        val title: String? = null
    ) : Component() {
        override val type: String = "horizontal_rail"
    }

    data class Unknown(
        override val id: String,
        override val type: String,
        override val style: Style? = null,
        override val actions: Map<String, Action> = emptyMap(),
        override val children: List<Component>? = null,
        override val visibility: Boolean = true
    ) : Component()
}
