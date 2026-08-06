package com.noorheroes.car24assignment.feature.renderer.widget

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.noorheroes.car24assignment.core.model.domain.Component
import com.noorheroes.car24assignment.feature.renderer.engine.RenderComponent
import com.noorheroes.car24assignment.feature.renderer.resolver.StyleResolver

@Composable
fun GridWidget(component: Component) {
    val columns = (component.properties["columns"] as? Number)?.toInt() ?: 2
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(StyleResolver.resolveModifier(component.style, component.visibility))
    ) {
        val children = component.children ?: emptyList()
        children.chunked(columns).forEach { rowChildren ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowChildren.forEach { child ->
                    Box(modifier = Modifier.weight(1f)) {
                        RenderComponent(child)
                    }
                }
                // Fill empty slots in the last row
                if (rowChildren.size < columns) {
                    repeat(columns - rowChildren.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
