package com.noorheroes.car24assignment.feature.renderer.resolver

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object StyleResolver {
    fun resolvePadding(props: Map<String, Any?>): Modifier {
        val padding = (props["padding"] as? Number)?.toFloat() ?: 0f
        return Modifier.padding(padding.dp)
    }
}
