package com.noorheroes.car24assignment.core.designsystem.resolver

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import com.noorheroes.car24assignment.core.designsystem.theme.AppTheme
import com.noorheroes.car24assignment.core.designsystem.theme.ThemeDefaults
import com.noorheroes.car24assignment.core.designsystem.token.SpacingToken

object SpacingTokenResolver {
    @Composable
    fun resolve(token: SpacingToken?): Dp {
        val actualToken = token ?: ThemeDefaults.SpacingToken
        return when (actualToken) {
            SpacingToken.NONE -> AppTheme.spacing.none
            SpacingToken.XXS -> AppTheme.spacing.xxs
            SpacingToken.XS -> AppTheme.spacing.xs
            SpacingToken.SMALL -> AppTheme.spacing.small
            SpacingToken.MEDIUM -> AppTheme.spacing.medium
            SpacingToken.LARGE -> AppTheme.spacing.large
            SpacingToken.XL -> AppTheme.spacing.xl
            SpacingToken.XXL -> AppTheme.spacing.xxl
        }
    }
}
