package com.noorheroes.car24assignment.feature.renderer.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.noorheroes.car24assignment.core.model.domain.Component
import com.noorheroes.car24assignment.feature.renderer.engine.RenderComponent
import com.noorheroes.car24assignment.feature.renderer.resolver.StyleResolver

@Composable
fun HorizontalRailWidget(component: Component.HorizontalRail) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(StyleResolver.resolveModifier(component.style))
            .padding(vertical = 8.dp)
    ) {
        component.title?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
        LazyRow(
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(component.children ?: emptyList()) { child ->
                RenderComponent(child)
            }
        }
    }
}
