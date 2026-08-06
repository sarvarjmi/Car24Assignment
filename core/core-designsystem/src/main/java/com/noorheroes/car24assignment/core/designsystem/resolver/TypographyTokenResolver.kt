package com.noorheroes.car24assignment.core.designsystem.resolver

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import com.noorheroes.car24assignment.core.designsystem.theme.ThemeDefaults
import com.noorheroes.car24assignment.core.designsystem.token.TypographyToken

object TypographyTokenResolver {
    @Composable
    fun resolve(token: TypographyToken?): TextStyle {
        val actualToken = token ?: ThemeDefaults.TypographyToken
        return when (actualToken) {
            TypographyToken.DISPLAY_LARGE -> MaterialTheme.typography.displayLarge
            TypographyToken.DISPLAY_MEDIUM -> MaterialTheme.typography.displayMedium
            TypographyToken.DISPLAY_SMALL -> MaterialTheme.typography.displaySmall
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
    }
}
