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
        register(
            WidgetMetadata(
                type = "banner",
                properties = listOf(
                    PropertyMetadata("title", "Title", PropertyType.TEXT),
                    PropertyMetadata("subtitle", "Subtitle", PropertyType.TEXT),
                    PropertyMetadata("imageUrl", "Image URL", PropertyType.IMAGE_URL)
                )
            )
        )
        register(
            WidgetMetadata(
                type = "divider",
                properties = listOf(
                    PropertyMetadata("thickness", "Thickness (SpacingToken)", PropertyType.DROPDOWN, options = listOf("NONE", "XXS", "XS", "SMALL")),
                    PropertyMetadata("color", "Color (ColorToken)", PropertyType.DROPDOWN, options = listOf("OUTLINE", "PRIMARY", "SECONDARY"))
                )
            )
        )
        register(
            WidgetMetadata(
                type = "chip",
                properties = listOf(
                    PropertyMetadata("text", "Text", PropertyType.TEXT)
                )
            )
        )
        register(
            WidgetMetadata(
                type = "text",
                properties = listOf(
                    PropertyMetadata("text", "Content", PropertyType.TEXT),
                    PropertyMetadata("typography", "Typography", PropertyType.DROPDOWN, options = listOf("TITLE_LARGE", "BODY_MEDIUM", "LABEL_SMALL")),
                    PropertyMetadata("color", "Color", PropertyType.DROPDOWN, options = listOf("PRIMARY", "ON_SURFACE", "ERROR")),
                    PropertyMetadata("textAlign", "Alignment", PropertyType.DROPDOWN, options = listOf("START", "CENTER", "END"))
                )
            )
        )
        register(
            WidgetMetadata(
                type = "image",
                properties = listOf(
                    PropertyMetadata("url", "Image URL", PropertyType.IMAGE_URL),
                    PropertyMetadata("contentScale", "Scale", PropertyType.DROPDOWN, options = listOf("CROP", "FIT", "FILL"))
                )
            )
        )
        register(
            WidgetMetadata(
                type = "button",
                properties = listOf(
                    PropertyMetadata("text", "Button Text", PropertyType.TEXT)
                )
            )
        )
        register(
            WidgetMetadata(
                type = "badge",
                properties = listOf(
                    PropertyMetadata("text", "Badge Text", PropertyType.TEXT)
                )
            )
        )
        register(
            WidgetMetadata(
                type = "footer",
                properties = listOf(
                    PropertyMetadata("text", "Footer Text", PropertyType.TEXT)
                )
            )
        )
        register(
            WidgetMetadata(
                type = "icon",
                properties = listOf(
                    PropertyMetadata("icon", "Icon (IconToken)", PropertyType.DROPDOWN, options = listOf("CAR", "SEARCH", "HOME", "SERVER", "SELL", "FINANCE", "PROFILE")),
                    PropertyMetadata("tint", "Tint (ColorToken)", PropertyType.DROPDOWN, options = listOf("PRIMARY", "SECONDARY", "ON_SURFACE", "BLACK", "WHITE"))
                )
            )
        )
        register(
            WidgetMetadata(
                type = "spacer",
                properties = listOf(
                    PropertyMetadata("height", "Height (dp)", PropertyType.TEXT, defaultValue = "16dp"),
                    PropertyMetadata("width", "Width (dp)", PropertyType.TEXT, defaultValue = "0dp")
                )
            )
        )
        register(
            WidgetMetadata(
                type = "screen_config",
                properties = listOf(
                    PropertyMetadata("name", "Screen Name", PropertyType.TEXT),
                    PropertyMetadata("description", "Description", PropertyType.TEXT),
                    PropertyMetadata("refreshable", "Pull to Refresh", PropertyType.BOOLEAN),
                    PropertyMetadata("scrollable", "Scrollable", PropertyType.BOOLEAN),
                    PropertyMetadata("safeArea", "Safe Area", PropertyType.BOOLEAN)
                )
            )
        )
        register(
            WidgetMetadata(
                type = "grid",
                properties = listOf(
                    PropertyMetadata("columns", "Columns", PropertyType.NUMBER, defaultValue = 2)
                )
            )
        )
        register(
            WidgetMetadata(
                type = "card",
                properties = emptyList()
            )
        )
        register(
            WidgetMetadata(
                type = "cta_section",
                properties = emptyList()
            )
        )
        register(
            WidgetMetadata(
                type = "column",
                properties = emptyList()
            )
        )
        register(
            WidgetMetadata(
                type = "row",
                properties = emptyList()
            )
        )
        register(
            WidgetMetadata(
                type = "box",
                properties = emptyList()
            )
        )
    }

    fun register(widget: WidgetMetadata) {
        metadata[widget.type] = widget
    }

    fun get(type: String): WidgetMetadata? = metadata[type]
}
