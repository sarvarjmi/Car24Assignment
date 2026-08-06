package com.noorheroes.car24assignment.core.ui.util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

object ImageResolver {
    @Composable
    fun resolve(imageUrl: String?): Any? {
        if (imageUrl == null) return null
        if (imageUrl.startsWith("http")) return imageUrl
        
        val context = LocalContext.current
        val resourceId = context.resources.getIdentifier(imageUrl, "drawable", context.packageName)
        return if (resourceId != 0) resourceId else imageUrl
    }
}
