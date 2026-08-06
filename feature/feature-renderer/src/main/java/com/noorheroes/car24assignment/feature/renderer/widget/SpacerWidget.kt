package com.noorheroes.car24assignment.feature.renderer.widget

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.noorheroes.car24assignment.core.model.domain.Component

@Composable
fun SpacerWidget(component: Component) {
    val height = (component.properties["height"] as? String)?.replace("dp", "")?.toIntOrNull() ?: 0
    val width = (component.properties["width"] as? String)?.replace("dp", "")?.toIntOrNull() ?: 0
    
    Spacer(
        modifier = Modifier
            .height(height.dp)
            .width(width.dp)
    )
}
