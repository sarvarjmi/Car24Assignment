package com.noorheroes.car24assignment.feature.renderer.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.noorheroes.car24assignment.core.model.domain.Component
import com.noorheroes.car24assignment.feature.renderer.engine.RenderComponent
import com.noorheroes.car24assignment.feature.renderer.resolver.StyleResolver

@Composable
fun ColumnWidget(component: Component) {
    Column(
        modifier = Modifier.then(StyleResolver.resolveModifier(
            style = component.style, 
            visibility = component.visibility
        )),
        verticalArrangement = StyleResolver.resolveVerticalArrangement(component.style?.arrangement?.vertical),
        horizontalAlignment = StyleResolver.resolveHorizontalAlignment(component.style?.alignment?.horizontal)
    ) {
        component.children?.forEach { child ->
            RenderComponent(child)
        }
    }
}

@Composable
fun RowWidget(component: Component) {
    Row(
        modifier = Modifier.then(StyleResolver.resolveModifier(
            style = component.style, 
            visibility = component.visibility
        )),
        horizontalArrangement = StyleResolver.resolveHorizontalArrangement(component.style?.arrangement?.horizontal),
        verticalAlignment = StyleResolver.resolveVerticalAlignment(component.style?.alignment?.vertical)
    ) {
        component.children?.forEach { child ->
            RenderComponent(child)
        }
    }
}
