package com.noorheroes.car24assignment.feature.renderer.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.noorheroes.car24assignment.core.model.domain.Component
import com.noorheroes.car24assignment.feature.renderer.engine.RenderComponent
import com.noorheroes.car24assignment.feature.renderer.resolver.StyleResolver

@Composable
fun ChipGroupWidget(component: Component) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(StyleResolver.resolveModifier(component.style)),
        horizontalArrangement = Arrangement.spacedBy(StyleResolver.resolveSpacing(com.noorheroes.car24assignment.core.designsystem.token.SpacingToken.SMALL))
    ) {
        component.children?.forEach { child ->
            RenderComponent(child)
        }
    }
}
