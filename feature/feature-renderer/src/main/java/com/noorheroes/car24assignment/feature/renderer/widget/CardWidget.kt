package com.noorheroes.car24assignment.feature.renderer.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.noorheroes.car24assignment.core.designsystem.resolver.SpacingTokenResolver
import com.noorheroes.car24assignment.core.model.domain.Component
import com.noorheroes.car24assignment.feature.renderer.engine.RenderComponent
import com.noorheroes.car24assignment.feature.renderer.resolver.StyleResolver

@Composable
fun CardWidget(component: Component) {
    val backgroundColor = component.style?.background?.color?.let { 
        StyleResolver.resolveColor(it)
    } ?: MaterialTheme.colorScheme.surface

    Card(
        modifier = Modifier
            .then(StyleResolver.resolveModifier(
                style = component.style, 
                visibility = component.visibility,
                includePadding = false // Apply padding to internal container
            )),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = StyleResolver.resolveElevation(component.style?.elevation)
        ),
        shape = com.noorheroes.car24assignment.core.designsystem.resolver.ShapeTokenResolver.resolve(component.style?.shape)
    ) {
        val padding = component.style?.padding
        val internalModifier = if (padding != null) {
            Modifier.padding(
                start = SpacingTokenResolver.resolve(padding.all ?: padding.start),
                top = SpacingTokenResolver.resolve(padding.all ?: padding.top),
                end = SpacingTokenResolver.resolve(padding.all ?: padding.end),
                bottom = SpacingTokenResolver.resolve(padding.all ?: padding.bottom)
            )
        } else Modifier

        Column(modifier = internalModifier) {
            component.children?.forEach { child ->
                RenderComponent(child)
            }
        }
    }
}
