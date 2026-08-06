package com.noorheroes.car24assignment.feature.renderer.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.noorheroes.car24assignment.core.model.domain.Component
import com.noorheroes.car24assignment.feature.renderer.engine.RenderComponent
import com.noorheroes.car24assignment.feature.renderer.resolver.StyleResolver

@Composable
fun BoxWidget(component: Component) {
    Box(
        modifier = Modifier.then(StyleResolver.resolveModifier(
            style = component.style, 
            visibility = component.visibility
        )),
        contentAlignment = StyleResolver.resolveBoxAlignment(component.style?.alignment)
    ) {
        component.children?.forEach { child ->
            RenderComponent(child)
        }
    }
}
