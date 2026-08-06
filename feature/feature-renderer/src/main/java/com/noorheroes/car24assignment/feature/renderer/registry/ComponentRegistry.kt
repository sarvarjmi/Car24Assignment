package com.noorheroes.car24assignment.feature.renderer.registry

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import com.noorheroes.car24assignment.core.model.domain.Component
import com.noorheroes.car24assignment.feature.renderer.fallback.FallbackWidget

typealias Widget = @Composable (Component) -> Unit

class ComponentRegistry {
    private val registry = mutableMapOf<String, Widget>()

    fun register(type: String, widget: Widget) {
        registry[type] = widget
    }

    fun getWidget(type: String): Widget {
        return registry[type] ?: { component -> FallbackWidget(component) }
    }
}

val LocalComponentRegistry = staticCompositionLocalOf<ComponentRegistry> {
    error("No ComponentRegistry provided")
}
