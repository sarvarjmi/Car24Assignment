package com.noorheroes.car24assignment.feature.server.metadata

enum class PropertyType {
    TEXT, NUMBER, BOOLEAN, IMAGE_URL, DROPDOWN, ACTION
}

data class PropertyMetadata(
    val key: String,
    val label: String,
    val type: PropertyType,
    val options: List<String> = emptyList(),
    val defaultValue: Any? = null
)

data class WidgetMetadata(
    val type: String,
    val properties: List<PropertyMetadata>
)

object WidgetMetadataRegistry {
    private val metadata = mutableMapOf<String, WidgetMetadata>()

    init {
        register(
            WidgetMetadata(
                type = "header",
                properties = listOf(
                    PropertyMetadata("title", "Title", PropertyType.TEXT),
                    PropertyMetadata("subtitle", "Subtitle", PropertyType.TEXT),
                    PropertyMetadata("imageUrl", "Image URL", PropertyType.IMAGE_URL)
                )
            )
        )
        register(
            WidgetMetadata(
                type = "hero_banner",
                properties = listOf(
                    PropertyMetadata("title", "Title", PropertyType.TEXT),
                    PropertyMetadata("subtitle", "Subtitle", PropertyType.TEXT),
                    PropertyMetadata("imageUrl", "Image URL", PropertyType.IMAGE_URL),
                    PropertyMetadata("ctaText", "CTA Text", PropertyType.TEXT)
                )
            )
        )
        register(
            WidgetMetadata(
                type = "cta",
                properties = listOf(
                    PropertyMetadata("text", "Button Text", PropertyType.TEXT)
                )
            )
        )
        register(
            WidgetMetadata(
                type = "car_card",
                properties = listOf(
                    PropertyMetadata("title", "Car Title", PropertyType.TEXT),
                    PropertyMetadata("price", "Price", PropertyType.TEXT),
                    PropertyMetadata("location", "Location", PropertyType.TEXT),
                    PropertyMetadata("imageUrl", "Image URL", PropertyType.IMAGE_URL)
                )
            )
        )
        register(
            WidgetMetadata(
                type = "search_bar",
                properties = listOf(
                    PropertyMetadata("placeholder", "Placeholder", PropertyType.TEXT)
                )
            )
        )
    }

    fun register(widget: WidgetMetadata) {
        metadata[widget.type] = widget
    }

    fun get(type: String): WidgetMetadata? = metadata[type]
}
