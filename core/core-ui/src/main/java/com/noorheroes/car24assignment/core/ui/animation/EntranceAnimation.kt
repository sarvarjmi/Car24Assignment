package com.noorheroes.car24assignment.core.ui.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun EntranceAnimation(
    modifier: Modifier = Modifier,
    delayMillis: Int = 0,
    durationMillis: Int = 300,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    if (!enabled) {
        content()
        return
    }

    val visibleState = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn(animationSpec = tween(durationMillis = durationMillis, delayMillis = delayMillis)) +
                slideInVertically(
                    initialOffsetY = { it / 4 },
                    animationSpec = tween(durationMillis = durationMillis, delayMillis = delayMillis)
                ),
        modifier = modifier,
        content = { content() }
    )
}
