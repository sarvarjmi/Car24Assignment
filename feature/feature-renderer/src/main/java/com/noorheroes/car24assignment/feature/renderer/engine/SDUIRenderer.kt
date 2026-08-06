package com.noorheroes.car24assignment.feature.renderer.engine

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.noorheroes.car24assignment.core.model.domain.Component
import com.noorheroes.car24assignment.core.model.domain.Screen
import com.noorheroes.car24assignment.core.model.domain.Section
import com.noorheroes.car24assignment.core.model.json.VisibilityToken
import com.noorheroes.car24assignment.core.ui.animation.EntranceAnimation
import com.noorheroes.car24assignment.core.ui.empty.EmptyView
import com.noorheroes.car24assignment.feature.renderer.action.ActionDispatcher
import com.noorheroes.car24assignment.feature.renderer.action.LocalActionDispatcher
import com.noorheroes.car24assignment.feature.renderer.registry.ComponentRegistry
import com.noorheroes.car24assignment.feature.renderer.registry.LocalComponentRegistry

/**
 * The main entry point for the SDUI rendering engine.
 * Takes a [Screen] domain model and recursively renders its sections and components.
 * 
 * @param screen The screen hierarchy to render.
 * @param registry The component registry mapping types to widget implementations.
 * @param actionDispatcher The dispatcher for handling dynamic user actions.
 * @param modifier The modifier to apply to the root container.
 */
@Composable
fun SDUIRenderer(
    screen: Screen,
    registry: ComponentRegistry,
    actionDispatcher: ActionDispatcher,
    modifier: Modifier = Modifier
) {
    androidx.compose.runtime.CompositionLocalProvider(
        LocalComponentRegistry provides registry,
        LocalActionDispatcher provides actionDispatcher
    ) {
        if (screen.sections.isEmpty()) {
            EmptyView(modifier = modifier)
        } else if (!screen.configuration.scrollable) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .then(if (screen.configuration.safeArea) Modifier.statusBarsPadding() else Modifier)
            ) {
                screen.sections.forEachIndexed { sIndex, section ->
                    if (section.visibility) {
                        section.components.forEachIndexed { cIndex, component ->
                            if (component.visibility != VisibilityToken.GONE) {
                                EntranceAnimation(delayMillis = (sIndex * 200) + (cIndex * 100)) {
                                    RenderComponent(component)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .then(if (screen.configuration.safeArea) Modifier.statusBarsPadding() else Modifier)
            ) {
                screen.sections.forEachIndexed { sIndex, section ->
                    if (section.visibility) {
                        itemsIndexed(section.components, key = { _, it -> it.id }) { cIndex, component ->
                            if (component.visibility != VisibilityToken.GONE) {
                                EntranceAnimation(delayMillis = (sIndex * 200) + (cIndex * 100)) {
                                    RenderComponent(component)
                                }
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
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
    Box(modifier = Modifier.animateContentSize()) {
        widget(component)
    }
}
