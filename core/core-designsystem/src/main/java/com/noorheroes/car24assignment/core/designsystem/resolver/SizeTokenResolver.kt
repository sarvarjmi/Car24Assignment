package com.noorheroes.car24assignment.core.designsystem.resolver

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.noorheroes.car24assignment.core.designsystem.token.SizeToken

object SizeTokenResolver {
    @Composable
    fun resolve(token: SizeToken): Dp {
        return when (token) {
            SizeToken.ICON_SMALL -> 16.dp
            SizeToken.ICON_MEDIUM -> 24.dp
            SizeToken.ICON_LARGE -> 32.dp
            SizeToken.TOUCH_TARGET_MIN -> 48.dp
            SizeToken.BUTTON_MIN_HEIGHT -> 40.dp
            SizeToken.AVATAR_SMALL -> 40.dp
            SizeToken.AVATAR_MEDIUM -> 56.dp
        }
    }
}
