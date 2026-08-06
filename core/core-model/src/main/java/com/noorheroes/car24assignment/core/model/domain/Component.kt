package com.noorheroes.car24assignment.core.model.domain

import com.noorheroes.car24assignment.core.model.json.*

sealed class Component {
    abstract val id: String
    abstract val type: String
    abstract val properties: Map<String, Any?>
    abstract val style: Style?
    abstract val actions: Map<String, Action>
    abstract val children: List<Component>?
    abstract val visibility: VisibilityToken
    abstract val state: RuntimeState?

    data class Banner(
        override val id: String,
        override val style: Style? = null,
        override val actions: Map<String, Action> = emptyMap(),
        override val children: List<Component>? = null,
        override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
        override val state: RuntimeState? = null,
        override val properties: Map<String, Any?> = emptyMap(),
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
        override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
        override val state: RuntimeState? = null,
        override val properties: Map<String, Any?> = emptyMap(),
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
        override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
        override val state: RuntimeState? = null,
        override val properties: Map<String, Any?> = emptyMap(),
        val placeholder: String
    ) : Component() {
        override val type: String = "search_bar"
    }

    data class Categories(
        override val id: String,
        override val style: Style? = null,
        override val actions: Map<String, Action> = emptyMap(),
        override val children: List<Component>? = null,
        override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
        override val state: RuntimeState? = null,
        override val properties: Map<String, Any?> = emptyMap(),
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
        override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
        override val state: RuntimeState? = null,
        override val properties: Map<String, Any?> = emptyMap(),
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
        override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
        override val state: RuntimeState? = null,
        override val properties: Map<String, Any?> = emptyMap(),
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
        override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
        override val state: RuntimeState? = null,
        override val properties: Map<String, Any?> = emptyMap(),
        val text: String
    ) : Component() {
        override val type: String = "cta"
    }

    data class Footer(
        override val id: String,
        override val style: Style? = null,
        override val actions: Map<String, Action> = emptyMap(),
        override val children: List<Component>? = null,
        override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
        override val state: RuntimeState? = null,
        override val properties: Map<String, Any?> = emptyMap(),
        val text: String,
        val copyright: String? = null,
        val version: String? = null
    ) : Component() {
        override val type: String = "footer"
    }

    data class HorizontalRail(
        override val id: String,
        override val style: Style? = null,
        override val actions: Map<String, Action> = emptyMap(),
        override val children: List<Component>? = null,
        override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
        override val state: RuntimeState? = null,
        override val properties: Map<String, Any?> = emptyMap(),
        val title: String? = null
    ) : Component() {
        override val type: String = "horizontal_rail"
    }

    data class Text(
        override val id: String,
        override val style: Style? = null,
        override val actions: Map<String, Action> = emptyMap(),
        override val children: List<Component>? = null,
        override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
        override val state: RuntimeState? = null,
        override val properties: Map<String, Any?> = emptyMap(),
        val text: String
    ) : Component() {
        override val type: String = "text"
    }

    data class Image(
        override val id: String,
        override val style: Style? = null,
        override val actions: Map<String, Action> = emptyMap(),
        override val children: List<Component>? = null,
        override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
        override val state: RuntimeState? = null,
        override val properties: Map<String, Any?> = emptyMap(),
        val url: String
    ) : Component() {
        override val type: String = "image"
    }

    data class Icon(
        override val id: String,
        override val style: Style? = null,
        override val actions: Map<String, Action> = emptyMap(),
        override val children: List<Component>? = null,
        override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
        override val state: RuntimeState? = null,
        override val properties: Map<String, Any?> = emptyMap(),
        val icon: String
    ) : Component() {
        override val type: String = "icon"
    }

    data class Badge(
        override val id: String,
        override val style: Style? = null,
        override val actions: Map<String, Action> = emptyMap(),
        override val children: List<Component>? = null,
        override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
        override val state: RuntimeState? = null,
        override val properties: Map<String, Any?> = emptyMap(),
        val text: String
    ) : Component() {
        override val type: String = "badge"
    }

    data class Button(
        override val id: String,
        override val style: Style? = null,
        override val actions: Map<String, Action> = emptyMap(),
        override val children: List<Component>? = null,
        override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
        override val state: RuntimeState? = null,
        override val properties: Map<String, Any?> = emptyMap(),
        val text: String
    ) : Component() {
        override val type: String = "button"
    }

    data class Chip(
        override val id: String,
        override val style: Style? = null,
        override val actions: Map<String, Action> = emptyMap(),
        override val children: List<Component>? = null,
        override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
        override val state: RuntimeState? = null,
        override val properties: Map<String, Any?> = emptyMap(),
        val text: String
    ) : Component() {
        override val type: String = "chip"
    }

    data class ChipGroup(
        override val id: String,
        override val style: Style? = null,
        override val actions: Map<String, Action> = emptyMap(),
        override val children: List<Component>? = null,
        override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
        override val state: RuntimeState? = null,
        override val properties: Map<String, Any?> = emptyMap()
    ) : Component() {
        override val type: String = "chip_group"
    }

    data class Divider(
        override val id: String,
        override val style: Style? = null,
        override val actions: Map<String, Action> = emptyMap(),
        override val children: List<Component>? = null,
        override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
        override val state: RuntimeState? = null,
        override val properties: Map<String, Any?> = emptyMap()
    ) : Component() {
        override val type: String = "divider"
    }

    data class Spacer(
        override val id: String,
        override val style: Style? = null,
        override val actions: Map<String, Action> = emptyMap(),
        override val children: List<Component>? = null,
        override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
        override val state: RuntimeState? = null,
        override val properties: Map<String, Any?> = emptyMap()
    ) : Component() {
        override val type: String = "spacer"
    }

    data class Column(
        override val id: String,
        override val style: Style? = null,
        override val actions: Map<String, Action> = emptyMap(),
        override val children: List<Component>? = null,
        override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
        override val state: RuntimeState? = null,
        override val properties: Map<String, Any?> = emptyMap()
    ) : Component() {
        override val type: String = "column"
    }

    data class Row(
        override val id: String,
        override val style: Style? = null,
        override val actions: Map<String, Action> = emptyMap(),
        override val children: List<Component>? = null,
        override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
        override val state: RuntimeState? = null,
        override val properties: Map<String, Any?> = emptyMap()
    ) : Component() {
        override val type: String = "row"
    }

    data class Box(
        override val id: String,
        override val style: Style? = null,
        override val actions: Map<String, Action> = emptyMap(),
        override val children: List<Component>? = null,
        override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
        override val state: RuntimeState? = null,
        override val properties: Map<String, Any?> = emptyMap()
    ) : Component() {
        override val type: String = "box"
    }

    data class Card(
        override val id: String,
        override val style: Style? = null,
        override val actions: Map<String, Action> = emptyMap(),
        override val children: List<Component>? = null,
        override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
        override val state: RuntimeState? = null,
        override val properties: Map<String, Any?> = emptyMap()
    ) : Component() {
        override val type: String = "card"
    }

    data class CtaSection(
        override val id: String,
        override val style: Style? = null,
        override val actions: Map<String, Action> = emptyMap(),
        override val children: List<Component>? = null,
        override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
        override val state: RuntimeState? = null,
        override val properties: Map<String, Any?> = emptyMap()
    ) : Component() {
        override val type: String = "cta_section"
    }

    data class LazyColumn(
        override val id: String,
        override val style: Style? = null,
        override val actions: Map<String, Action> = emptyMap(),
        override val children: List<Component>? = null,
        override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
        override val state: RuntimeState? = null,
        override val properties: Map<String, Any?> = emptyMap()
    ) : Component() {
        override val type: String = "lazy_column"
    }

    data class LazyRow(
        override val id: String,
        override val style: Style? = null,
        override val actions: Map<String, Action> = emptyMap(),
        override val children: List<Component>? = null,
        override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
        override val state: RuntimeState? = null,
        override val properties: Map<String, Any?> = emptyMap()
    ) : Component() {
        override val type: String = "lazy_row"
    }

    data class Grid(
        override val id: String,
        override val style: Style? = null,
        override val actions: Map<String, Action> = emptyMap(),
        override val children: List<Component>? = null,
        override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
        override val state: RuntimeState? = null,
        override val properties: Map<String, Any?> = emptyMap(),
        val columns: Int
    ) : Component() {
        override val type: String = "grid"
    }

    data class Unknown(
        override val id: String,
        override val type: String,
        override val style: Style? = null,
        override val actions: Map<String, Action> = emptyMap(),
        override val children: List<Component>? = null,
        override val visibility: VisibilityToken = VisibilityToken.VISIBLE,
        override val state: RuntimeState? = null,
        override val properties: Map<String, Any?> = emptyMap()
    ) : Component()
}
