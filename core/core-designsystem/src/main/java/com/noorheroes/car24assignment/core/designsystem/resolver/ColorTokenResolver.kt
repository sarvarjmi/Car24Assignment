package com.noorheroes.car24assignment.core.designsystem.resolver

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.noorheroes.car24assignment.core.designsystem.theme.*
import com.noorheroes.car24assignment.core.designsystem.token.ColorToken

object ColorTokenResolver {
    @Composable
    fun resolve(token: ColorToken?): Color {
        val actualToken = token ?: ThemeDefaults.ColorToken
        return when (actualToken) {
            ColorToken.PRIMARY -> MaterialTheme.colorScheme.primary
            ColorToken.ON_PRIMARY -> MaterialTheme.colorScheme.onPrimary
            ColorToken.PRIMARY_CONTAINER -> MaterialTheme.colorScheme.primaryContainer
            ColorToken.ON_PRIMARY_CONTAINER -> MaterialTheme.colorScheme.onPrimaryContainer
            ColorToken.SECONDARY -> MaterialTheme.colorScheme.secondary
            ColorToken.ON_SECONDARY -> MaterialTheme.colorScheme.onSecondary
            ColorToken.SECONDARY_CONTAINER -> MaterialTheme.colorScheme.secondaryContainer
            ColorToken.ON_SECONDARY_CONTAINER -> MaterialTheme.colorScheme.onSecondaryContainer
            ColorToken.TERTIARY -> MaterialTheme.colorScheme.tertiary
            ColorToken.ON_TERTIARY -> MaterialTheme.colorScheme.onTertiary
            ColorToken.TERTIARY_CONTAINER -> MaterialTheme.colorScheme.tertiaryContainer
            ColorToken.ON_TERTIARY_CONTAINER -> MaterialTheme.colorScheme.onTertiaryContainer
            ColorToken.BACKGROUND -> MaterialTheme.colorScheme.background
            ColorToken.ON_BACKGROUND -> MaterialTheme.colorScheme.onBackground
            ColorToken.SURFACE -> MaterialTheme.colorScheme.surface
            ColorToken.ON_SURFACE -> MaterialTheme.colorScheme.onSurface
            ColorToken.SURFACE_VARIANT -> MaterialTheme.colorScheme.surfaceVariant
            ColorToken.ON_SURFACE_VARIANT -> MaterialTheme.colorScheme.onSurfaceVariant
            ColorToken.OUTLINE -> MaterialTheme.colorScheme.outline
            ColorToken.OUTLINE_VARIANT -> MaterialTheme.colorScheme.outlineVariant
            ColorToken.ERROR -> MaterialTheme.colorScheme.error
            ColorToken.ON_ERROR -> MaterialTheme.colorScheme.onError
            ColorToken.ERROR_CONTAINER -> MaterialTheme.colorScheme.errorContainer
            ColorToken.ON_ERROR_CONTAINER -> MaterialTheme.colorScheme.onErrorContainer
            ColorToken.SUCCESS -> SuccessColor
            ColorToken.ON_SUCCESS -> OnSuccessColor
            ColorToken.WARNING -> WarningColor
            ColorToken.ON_WARNING -> OnWarningColor
            ColorToken.INFO -> InfoColor
            ColorToken.ON_INFO -> OnInfoColor
            ColorToken.TRANSPARENT -> Color.Transparent
            ColorToken.BLACK -> Color.Black
            ColorToken.WHITE -> Color.White
        }
    }
}
