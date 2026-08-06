package com.noorheroes.car24assignment.feature.renderer.widget

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.noorheroes.car24assignment.core.designsystem.resolver.ColorTokenResolver
import com.noorheroes.car24assignment.core.designsystem.resolver.IconTokenResolver
import com.noorheroes.car24assignment.core.designsystem.token.ColorToken
import com.noorheroes.car24assignment.core.designsystem.token.IconToken
import com.noorheroes.car24assignment.core.model.domain.Component
import com.noorheroes.car24assignment.feature.renderer.resolver.StyleResolver

@Composable
fun IconWidget(component: Component) {
    val iconName = component.properties["icon"] as? String ?: return
    val tintName = component.properties["tint"] as? String
    
    val iconToken = try { IconToken.valueOf(iconName.uppercase()) } catch (e: Exception) { null } ?: return
    val tintToken = try { tintName?.let { ColorToken.valueOf(it.uppercase()) } } catch (e: Exception) { null }

    Icon(
        imageVector = IconTokenResolver.resolve(iconToken),
        contentDescription = null,
        modifier = Modifier.then(StyleResolver.resolveModifier(component.style, component.visibility)),
        tint = if (tintToken != null) ColorTokenResolver.resolve(tintToken) else Color.Unspecified
    )
}
