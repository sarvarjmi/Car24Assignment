package com.noorheroes.car24assignment.feature.renderer.widget

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.noorheroes.car24assignment.core.model.domain.Component
import com.noorheroes.car24assignment.feature.renderer.engine.RenderComponent
import com.noorheroes.car24assignment.feature.renderer.resolver.StyleResolver

@Composable
fun CTASectionWidget(component: Component) {
    // CTA Section is a container for multiple buttons/CTAs
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(StyleResolver.resolveModifier(component.style))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        component.children?.forEach { child ->
            RenderComponent(child)
        }
    }
}
