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

@Composable
fun BannerWidget(component: Component) {
    val actionDispatcher = LocalActionDispatcher.current
    val imageUrl = component.properties["imageUrl"] as? String
    val title = component.properties["title"] as? String
    val subtitle = component.properties["subtitle"] as? String

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(200.dp),
        onClick = {
            component.actions["click"]?.let { action ->
                actionDispatcher.dispatch(action)
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = title,
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
                if (title != null) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                }
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }
            }
        }
    }
}
