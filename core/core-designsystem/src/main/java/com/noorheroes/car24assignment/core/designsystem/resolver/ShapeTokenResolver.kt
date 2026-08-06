package com.noorheroes.car24assignment.core.designsystem.resolver

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import com.noorheroes.car24assignment.core.designsystem.theme.ThemeDefaults
import com.noorheroes.car24assignment.core.designsystem.token.ShapeToken

object ShapeTokenResolver {
    @Composable
    fun resolve(token: ShapeToken?): Shape {
        val actualToken = token ?: ThemeDefaults.ShapeToken
        return when (actualToken) {
            ShapeToken.NONE -> RectangleShape
            ShapeToken.EXTRA_SMALL -> MaterialTheme.shapes.extraSmall
            ShapeToken.SMALL -> MaterialTheme.shapes.small
            ShapeToken.MEDIUM -> MaterialTheme.shapes.medium
            ShapeToken.LARGE -> MaterialTheme.shapes.large
            ShapeToken.EXTRA_LARGE -> MaterialTheme.shapes.extraLarge
            ShapeToken.FULL -> CircleShape
        }
    }
}
