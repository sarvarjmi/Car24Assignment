package com.noorheroes.car24assignment.feature.renderer.fallback

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.noorheroes.car24assignment.core.model.domain.Component

@Composable
fun FallbackWidget(component: Component) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.LightGray.copy(alpha = 0.2f))
            .border(1.dp, Color.Red.copy(alpha = 0.5f))
            .padding(16.dp)
    ) {
        Text(
            text = "Unknown Component: ${component.type}",
            style = MaterialTheme.typography.labelLarge,
            color = Color.Red
        )
        Text(
            text = "ID: ${component.id}",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
