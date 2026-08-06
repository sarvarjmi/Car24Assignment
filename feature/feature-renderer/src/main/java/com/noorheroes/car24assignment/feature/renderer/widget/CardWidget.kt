package com.noorheroes.car24assignment.feature.renderer.widget

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.noorheroes.car24assignment.core.model.domain.Component
import com.noorheroes.car24assignment.feature.renderer.engine.RenderComponent
import com.noorheroes.car24assignment.feature.renderer.resolver.StyleResolver

@Composable
fun CardWidget(component: Component) {
    Card(
        modifier = Modifier
            .then(StyleResolver.resolveModifier(component.style)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = StyleResolver.resolveElevation(component.style?.elevation)
        )
    ) {
        component.children?.forEach { child ->
            RenderComponent(child)
        }
    }
}
