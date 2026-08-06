package com.noorheroes.car24assignment.feature.renderer.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.noorheroes.car24assignment.core.model.domain.Component
import com.noorheroes.car24assignment.feature.renderer.action.LocalActionDispatcher
import com.noorheroes.car24assignment.feature.renderer.resolver.StyleResolver

@Composable
fun BannerWidget(component: Component.Banner) {
    val actionDispatcher = LocalActionDispatcher.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(StyleResolver.resolveModifier(component.style))
            .height(200.dp),
        onClick = {
            component.actions["click"]?.let { action ->
                actionDispatcher.dispatch(action, component)
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = component.imageUrl,
                contentDescription = component.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(8.dp)
            ) {
                component.title?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                }
                component.subtitle?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }
            }
        }
    }
}
