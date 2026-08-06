package com.noorheroes.car24assignment.feature.renderer.registry

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import com.noorheroes.car24assignment.core.model.domain.Component
import com.noorheroes.car24assignment.feature.renderer.fallback.FallbackWidget

typealias Widget = @Composable (Component) -> Unit

/**
 * A central registry that maps component types (e.g., "banner") to their Composable renderers.
 * This registry is provided to the [SDUIRenderer] via CompositionLocal.
 */
class ComponentRegistry {
    private val registry = mutableMapOf<String, Widget>()

    /**
     * Registers a new [Widget] for a specific component [type].
     */
    fun register(type: String, widget: Widget) {
        registry[type] = widget
    }

    /**
     * Retrieves the [Widget] for a [type]. Returns a [FallbackWidget] if not found.
     */
    fun getWidget(type: String): Widget {
        return registry[type] ?: { component -> FallbackWidget(component) }
    }
}

val LocalComponentRegistry = staticCompositionLocalOf<ComponentRegistry> {
    error("No ComponentRegistry provided")
}
