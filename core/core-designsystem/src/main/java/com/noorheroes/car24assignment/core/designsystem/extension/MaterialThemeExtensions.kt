package com.noorheroes.car24assignment.core.designsystem.extension

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.noorheroes.car24assignment.core.designsystem.theme.AppSpacing
import com.noorheroes.car24assignment.core.designsystem.theme.LocalAppSpacing

val MaterialTheme.appSpacing: AppSpacing
    @Composable
    @ReadOnlyComposable
    get() = LocalAppSpacing.current
