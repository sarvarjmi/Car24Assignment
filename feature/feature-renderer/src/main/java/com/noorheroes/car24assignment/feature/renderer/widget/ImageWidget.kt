package com.noorheroes.car24assignment.feature.renderer.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.noorheroes.car24assignment.core.model.domain.Component
import com.noorheroes.car24assignment.core.ui.util.ImageResolver
import com.noorheroes.car24assignment.feature.renderer.resolver.StyleResolver

@Composable
fun ImageWidget(component: Component) {
    val url = component.properties["url"] as? String ?: return
    val contentScaleName = component.properties["contentScale"] as? String
    
    val contentScale = when (contentScaleName?.lowercase()) {
        "crop" -> ContentScale.Crop
        "fit" -> ContentScale.Fit
        "fill" -> ContentScale.FillBounds
        else -> ContentScale.Fit
    }

    AsyncImage(
        model = ImageResolver.resolve(url),
        contentDescription = null,
        modifier = Modifier.then(StyleResolver.resolveModifier(component.style, component.visibility)),
        contentScale = contentScale
    )
}
