package com.noorheroes.car24assignment.feature.renderer.resolver

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.noorheroes.car24assignment.core.designsystem.resolver.*
import com.noorheroes.car24assignment.core.designsystem.token.*
import com.noorheroes.car24assignment.core.model.domain.Style
import com.noorheroes.car24assignment.core.model.json.*

object StyleResolver {

    @Composable
    fun resolveModifier(style: Style?): Modifier {
        if (style == null) return Modifier
        
        var modifier: Modifier = Modifier
        
        // 1. Alpha
        style.alpha?.let { modifier = modifier.alpha(it) }
        
        // 2. Elevation (Shadow)
        style.elevation?.let { elevationToken ->
            modifier = modifier.shadow(
                elevation = ElevationTokenResolver.resolve(elevationToken),
                shape = ShapeTokenResolver.resolve(style.shape)
            )
        }

        // 3. Padding
        style.padding?.let { padding ->
            modifier = modifier.padding(
                start = SpacingTokenResolver.resolve(padding.all ?: padding.start),
                top = SpacingTokenResolver.resolve(padding.all ?: padding.top),
                end = SpacingTokenResolver.resolve(padding.all ?: padding.end),
                bottom = SpacingTokenResolver.resolve(padding.all ?: padding.bottom)
            )
        }
        
        // 4. Background
        style.background?.let { bg ->
            val gradient = bg.gradient
            if (gradient != null) {
                modifier = modifier.background(
                    Brush.verticalGradient(
                        colors = gradient.map { ColorTokenResolver.resolve(it) }
                    )
                )
            } else {
                modifier = modifier.background(ColorTokenResolver.resolve(bg.color))
            }
        }

        // 5. Shape (Clip)
        style.shape?.let { shape ->
            modifier = modifier.clip(ShapeTokenResolver.resolve(shape))
        }

        // 6. Border
        style.border?.let { border ->
            modifier = modifier.border(
                width = SpacingTokenResolver.resolve(border.width),
                color = ColorTokenResolver.resolve(border.color),
                shape = ShapeTokenResolver.resolve(border.shape)
            )
        }

        return modifier
    }

    @Composable
    fun resolveTypography(token: TypographyToken?): TextStyle {
        return TypographyTokenResolver.resolve(token)
    }

    @Composable
    fun resolveColor(token: ColorToken?): Color {
        return ColorTokenResolver.resolve(token)
    }

    @Composable
    fun resolveElevation(token: ElevationToken?): Dp {
        return ElevationTokenResolver.resolve(token)
    }

    @Composable
    fun resolveSpacing(token: SpacingToken?): Dp {
        return SpacingTokenResolver.resolve(token)
    }
}
