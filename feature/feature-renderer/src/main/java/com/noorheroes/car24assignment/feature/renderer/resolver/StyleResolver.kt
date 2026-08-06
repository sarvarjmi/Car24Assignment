package com.noorheroes.car24assignment.feature.renderer.resolver

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    fun resolveModifier(style: Style?, visibility: VisibilityToken = VisibilityToken.VISIBLE): Modifier {
        if (style == null && visibility == VisibilityToken.VISIBLE) return Modifier
        
        var modifier: Modifier = Modifier
        
        // 1. Visibility (HIDDEN takes space but not visible)
        if (visibility == VisibilityToken.HIDDEN) {
            modifier = modifier.alpha(0f)
        } else {
            style?.alpha?.let { modifier = modifier.alpha(it) }
        }
        
        if (style == null) return modifier
        
        // 2. Margin (External Padding)
        style.margin?.let { margin ->
            modifier = modifier.padding(
                start = SpacingTokenResolver.resolve(margin.all ?: margin.start),
                top = SpacingTokenResolver.resolve(margin.all ?: margin.top),
                end = SpacingTokenResolver.resolve(margin.all ?: margin.end),
                bottom = SpacingTokenResolver.resolve(margin.all ?: margin.bottom)
            )
        }
        
        // 3. Elevation (Shadow)
        style.elevation?.let { elevationToken ->
            modifier = modifier.shadow(
                elevation = ElevationTokenResolver.resolve(elevationToken),
                shape = ShapeTokenResolver.resolve(style.shape)
            )
        }

        // 4. Background
        style.background?.let { bg ->
            val gradient = bg.gradient
            if (gradient != null) {
                modifier = modifier.background(
                    Brush.verticalGradient(
                        colors = gradient.map { ColorTokenResolver.resolve(it) }
                    ),
                    shape = ShapeTokenResolver.resolve(style.shape)
                )
            } else {
                modifier = modifier.background(
                    color = ColorTokenResolver.resolve(bg.color),
                    shape = ShapeTokenResolver.resolve(style.shape)
                )
            }
        }
        
        // 5. Border
        style.border?.let { border ->
            modifier = modifier.border(
                width = SpacingTokenResolver.resolve(border.width),
                color = ColorTokenResolver.resolve(border.color),
                shape = ShapeTokenResolver.resolve(border.shape)
            )
        }

        // 6. Padding (Internal Padding)
        style.padding?.let { padding ->
            modifier = modifier.padding(
                start = SpacingTokenResolver.resolve(padding.all ?: padding.start),
                top = SpacingTokenResolver.resolve(padding.all ?: padding.top),
                end = SpacingTokenResolver.resolve(padding.all ?: padding.end),
                bottom = SpacingTokenResolver.resolve(padding.all ?: padding.bottom)
            )
        }

        // 7. Width & Height
        style.width?.let { modifier = modifier.width(SizeTokenResolver.resolve(it)) }
        style.height?.let { modifier = modifier.height(SizeTokenResolver.resolve(it)) }

        // 8. Layout Params
        style.layoutParams?.let { params ->
            if (params.fillMaxWidth == true) modifier = modifier.fillMaxWidth()
            if (params.fillMaxHeight == true) modifier = modifier.fillMaxHeight()
        }

        // 9. Clip (Shape)
        style.shape?.let { shape ->
            modifier = modifier.clip(ShapeTokenResolver.resolve(shape))
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

    fun resolveVerticalArrangement(token: ArrangementToken?): Arrangement.Vertical {
        return when (token) {
            ArrangementToken.START -> Arrangement.Top
            ArrangementToken.END -> Arrangement.Bottom
            ArrangementToken.CENTER -> Arrangement.Center
            ArrangementToken.SPACE_BETWEEN -> Arrangement.SpaceBetween
            ArrangementToken.SPACE_AROUND -> Arrangement.SpaceAround
            ArrangementToken.SPACE_EVENLY -> Arrangement.SpaceEvenly
            null -> Arrangement.Top
        }
    }

    fun resolveHorizontalArrangement(token: ArrangementToken?): Arrangement.Horizontal {
        return when (token) {
            ArrangementToken.START -> Arrangement.Start
            ArrangementToken.END -> Arrangement.End
            ArrangementToken.CENTER -> Arrangement.Center
            ArrangementToken.SPACE_BETWEEN -> Arrangement.SpaceBetween
            ArrangementToken.SPACE_AROUND -> Arrangement.SpaceAround
            ArrangementToken.SPACE_EVENLY -> Arrangement.SpaceEvenly
            null -> Arrangement.Start
        }
    }

    fun resolveHorizontalAlignment(token: AlignmentToken?): Alignment.Horizontal {
        return when (token) {
            AlignmentToken.START -> Alignment.Start
            AlignmentToken.CENTER -> Alignment.CenterHorizontally
            AlignmentToken.END -> Alignment.End
            else -> Alignment.Start
        }
    }

    fun resolveVerticalAlignment(token: AlignmentToken?): Alignment.Vertical {
        return when (token) {
            AlignmentToken.TOP -> Alignment.Top
            AlignmentToken.CENTER -> Alignment.CenterVertically
            AlignmentToken.BOTTOM -> Alignment.Bottom
            else -> Alignment.Top
        }
    }

    fun resolveBoxAlignment(alignment: AlignmentModel?): Alignment {
        val h = resolveHorizontalAlignment(alignment?.horizontal)
        val v = resolveVerticalAlignment(alignment?.vertical)
        
        return when {
            h == Alignment.Start && v == Alignment.Top -> Alignment.TopStart
            h == Alignment.CenterHorizontally && v == Alignment.Top -> Alignment.TopCenter
            h == Alignment.End && v == Alignment.Top -> Alignment.TopEnd
            h == Alignment.Start && v == Alignment.CenterVertically -> Alignment.CenterStart
            h == Alignment.CenterHorizontally && v == Alignment.CenterVertically -> Alignment.Center
            h == Alignment.End && v == Alignment.CenterVertically -> Alignment.CenterEnd
            h == Alignment.Start && v == Alignment.Bottom -> Alignment.BottomStart
            h == Alignment.CenterHorizontally && v == Alignment.Bottom -> Alignment.BottomCenter
            h == Alignment.End && v == Alignment.Bottom -> Alignment.BottomEnd
            else -> Alignment.TopStart
        }
    }
}
