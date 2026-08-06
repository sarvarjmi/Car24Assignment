package com.noorheroes.car24assignment.feature.renderer.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.noorheroes.car24assignment.core.model.domain.Component
import com.noorheroes.car24assignment.feature.renderer.engine.RenderComponent
import com.noorheroes.car24assignment.feature.renderer.resolver.StyleResolver

@Composable
fun LazyRowWidget(component: Component) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .then(StyleResolver.resolveModifier(component.style, component.visibility))
    ) {
        items(component.children ?: emptyList()) { child ->
            RenderComponent(child)
        }
    }
}
