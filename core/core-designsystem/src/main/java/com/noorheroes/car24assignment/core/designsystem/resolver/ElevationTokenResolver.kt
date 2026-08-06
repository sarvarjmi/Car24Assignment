package com.noorheroes.car24assignment.core.designsystem.resolver

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.noorheroes.car24assignment.core.designsystem.theme.ThemeDefaults
import com.noorheroes.car24assignment.core.designsystem.token.ElevationToken

object ElevationTokenResolver {
    @Composable
    fun resolve(token: ElevationToken?): Dp {
        val actualToken = token ?: ThemeDefaults.ElevationToken
        return when (actualToken) {
            ElevationToken.LEVEL_0 -> 0.dp
            ElevationToken.LEVEL_1 -> 1.dp
            ElevationToken.LEVEL_2 -> 3.dp
            ElevationToken.LEVEL_3 -> 6.dp
            ElevationToken.LEVEL_4 -> 8.dp
            ElevationToken.LEVEL_5 -> 12.dp
        }
    }
}
