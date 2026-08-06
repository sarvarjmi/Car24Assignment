package com.noorheroes.car24assignment.feature.renderer.resolver

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.noorheroes.car24assignment.core.model.domain.Style
import com.noorheroes.car24assignment.core.model.json.*

object StyleResolver {

    @Composable
    fun resolveModifier(style: Style?): Modifier {
        if (style == null) return Modifier
        
        var modifier: Modifier = Modifier
        
        // 1. Alpha
        style.alpha?.let { modifier = modifier.alpha(it) }
        
        // 2. Padding
        style.padding?.let { padding ->
            modifier = modifier.padding(
                start = resolveSpacing(padding.all ?: padding.start),
                top = resolveSpacing(padding.all ?: padding.top),
                end = resolveSpacing(padding.all ?: padding.end),
                bottom = resolveSpacing(padding.all ?: padding.bottom)
            )
        }
        
        // 3. Background
        style.background?.let { bg ->
            modifier = modifier.background(resolveColor(bg.color))
        }

        // 4. Shape (Clip)
        style.shape?.let { shape ->
            modifier = modifier.clip(resolveShape(shape))
        }

        return modifier
    }

    fun resolveSpacing(token: SpacingToken): Dp = when (token) {
        SpacingToken.NONE -> 0.dp
        SpacingToken.XS -> 4.dp
        SpacingToken.S -> 8.dp
        SpacingToken.M -> 12.dp
        SpacingToken.L -> 16.dp
        SpacingToken.XL -> 24.dp
        SpacingToken.XXL -> 32.dp
    }

    @Composable
    fun resolveColor(token: ColorToken): Color = when (token) {
        ColorToken.PRIMARY -> MaterialTheme.colorScheme.primary
        ColorToken.SECONDARY -> MaterialTheme.colorScheme.secondary
        ColorToken.SURFACE -> MaterialTheme.colorScheme.surface
        ColorToken.BACKGROUND -> MaterialTheme.colorScheme.background
        ColorToken.ERROR -> MaterialTheme.colorScheme.error
        ColorToken.OUTLINE -> MaterialTheme.colorScheme.outline
        ColorToken.ON_SURFACE -> MaterialTheme.colorScheme.onSurface
        ColorToken.ON_PRIMARY -> MaterialTheme.colorScheme.onPrimary
        ColorToken.ON_BACKGROUND -> MaterialTheme.colorScheme.onBackground
        ColorToken.TRANSPARENT -> Color.Transparent
        ColorToken.BLACK -> Color.Black
        ColorToken.WHITE -> Color.White
    }

    @Composable
    fun resolveTypography(token: TypographyToken): TextStyle = when (token) {
        TypographyToken.DISPLAY_LARGE -> MaterialTheme.typography.displayLarge
        TypographyToken.DISPLAY_MEDIUM -> MaterialTheme.typography.displayMedium
        TypographyToken.HEADLINE_LARGE -> MaterialTheme.typography.headlineLarge
        TypographyToken.HEADLINE_MEDIUM -> MaterialTheme.typography.headlineMedium
        TypographyToken.HEADLINE_SMALL -> MaterialTheme.typography.headlineSmall
        TypographyToken.TITLE_LARGE -> MaterialTheme.typography.titleLarge
        TypographyToken.TITLE_MEDIUM -> MaterialTheme.typography.titleMedium
        TypographyToken.TITLE_SMALL -> MaterialTheme.typography.titleSmall
        TypographyToken.BODY_LARGE -> MaterialTheme.typography.bodyLarge
        TypographyToken.BODY_MEDIUM -> MaterialTheme.typography.bodyMedium
        TypographyToken.BODY_SMALL -> MaterialTheme.typography.bodySmall
        TypographyToken.LABEL_LARGE -> MaterialTheme.typography.labelLarge
        TypographyToken.LABEL_MEDIUM -> MaterialTheme.typography.labelMedium
        TypographyToken.LABEL_SMALL -> MaterialTheme.typography.labelSmall
    }

    @Composable
    fun resolveShape(token: ShapeToken) = when (token) {
        ShapeToken.NONE -> androidx.compose.ui.graphics.RectangleShape
        ShapeToken.XS -> MaterialTheme.shapes.extraSmall
        ShapeToken.S -> MaterialTheme.shapes.small
        ShapeToken.M -> MaterialTheme.shapes.medium
        ShapeToken.L -> MaterialTheme.shapes.large
        ShapeToken.XL -> MaterialTheme.shapes.extraLarge
        ShapeToken.FULL -> androidx.compose.foundation.shape.CircleShape
    }
}
