package com.noorheroes.car24assignment.feature.renderer.widget

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.noorheroes.car24assignment.core.designsystem.resolver.ColorTokenResolver
import com.noorheroes.car24assignment.core.designsystem.resolver.SpacingTokenResolver
import com.noorheroes.car24assignment.core.designsystem.token.ColorToken
import com.noorheroes.car24assignment.core.designsystem.token.SpacingToken
import com.noorheroes.car24assignment.core.model.domain.Component
import com.noorheroes.car24assignment.feature.renderer.resolver.StyleResolver

@Composable
fun DividerWidget(component: Component) {
    val thicknessName = component.properties["thickness"] as? String
    val colorName = component.properties["color"] as? String
    
    val thicknessToken = try { thicknessName?.let { SpacingToken.valueOf(it.uppercase()) } } catch (e: Exception) { null }
    val colorToken = try { colorName?.let { ColorToken.valueOf(it.uppercase()) } } catch (e: Exception) { null }

    HorizontalDivider(
        modifier = Modifier.then(StyleResolver.resolveModifier(component.style)),
        thickness = thicknessToken?.let { SpacingTokenResolver.resolve(it) } ?: SpacingTokenResolver.resolve(SpacingToken.XXS),
        color = colorToken?.let { ColorTokenResolver.resolve(it) } ?: MaterialTheme.colorScheme.outlineVariant
    )
}
