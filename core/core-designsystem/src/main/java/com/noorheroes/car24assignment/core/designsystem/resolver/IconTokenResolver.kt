package com.noorheroes.car24assignment.core.designsystem.resolver

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.noorheroes.car24assignment.core.designsystem.token.IconToken

object IconTokenResolver {
    @Composable
    fun resolve(token: IconToken): ImageVector {
        return when (token) {
            IconToken.BACK -> Icons.AutoMirrored.Filled.ArrowBack
            IconToken.FORWARD -> Icons.AutoMirrored.Filled.ArrowForward
            IconToken.HOME -> Icons.Default.Home
            IconToken.SERVER -> Icons.Default.Settings
            IconToken.SEARCH -> Icons.Default.Search
            IconToken.EDIT -> Icons.Default.Edit
            IconToken.SAVE -> Icons.Default.Done
            IconToken.REFRESH -> Icons.Default.Refresh
            IconToken.CLOSE -> Icons.Default.Close
            IconToken.INFO -> Icons.Default.Info
            IconToken.WARNING -> Icons.Default.Warning
            IconToken.ERROR -> Icons.Default.Error
            IconToken.SUCCESS -> Icons.Default.CheckCircle
            IconToken.EXPAND_MORE -> Icons.Default.KeyboardArrowDown
            IconToken.COPY -> Icons.Default.ContentCopy
            IconToken.PREVIEW -> Icons.Default.Visibility
            IconToken.CAR -> Icons.Default.DirectionsCar
            IconToken.SELL -> Icons.Default.Sell
            IconToken.FINANCE -> Icons.Default.Payments
            IconToken.PROFILE -> Icons.Default.Person
            IconToken.NOTIFICATION -> Icons.Default.Notifications
            IconToken.MENU -> Icons.Default.Menu
            IconToken.FILTER -> Icons.Default.FilterList
        }
    }
}
