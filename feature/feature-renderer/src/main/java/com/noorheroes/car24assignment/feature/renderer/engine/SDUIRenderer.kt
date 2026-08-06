package com.noorheroes.car24assignment.feature.renderer.engine

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.noorheroes.car24assignment.core.model.domain.Component
import com.noorheroes.car24assignment.core.model.domain.Screen
import com.noorheroes.car24assignment.core.model.domain.Section
import com.noorheroes.car24assignment.feature.renderer.registry.ComponentRegistry
import com.noorheroes.car24assignment.feature.renderer.registry.LocalComponentRegistry

@Composable
fun SDUIRenderer(
    screen: Screen,
    registry: ComponentRegistry,
    modifier: Modifier = Modifier
) {
    androidx.compose.runtime.CompositionLocalProvider(LocalComponentRegistry provides registry) {
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            screen.sections.forEach { section ->
                if (section.visibility) {
                    items(section.components, key = { it.id }) { component ->
                        RenderComponent(component)
                    }

                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun RenderComponent(
    component: Component
) {
    val registry = LocalComponentRegistry.current
    val widget = registry.getWidget(component.type)
    widget(component)
}
