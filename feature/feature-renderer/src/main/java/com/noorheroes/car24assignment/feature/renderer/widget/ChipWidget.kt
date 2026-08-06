package com.noorheroes.car24assignment.feature.renderer.widget

import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.noorheroes.car24assignment.core.model.domain.Component
import com.noorheroes.car24assignment.feature.renderer.action.LocalActionDispatcher
import com.noorheroes.car24assignment.feature.renderer.resolver.StyleResolver

@Composable
fun ChipWidget(component: Component) {
    val text = component.properties["text"] as? String ?: ""
    val selected = component.state?.selected ?: false
    val enabled = component.state?.enabled ?: true
    val actionDispatcher = LocalActionDispatcher.current

    FilterChip(
        selected = selected,
        onClick = {
            component.actions["click"]?.let { actionDispatcher.dispatch(it, component) }
        },
        label = { Text(text = text) },
        modifier = Modifier.then(StyleResolver.resolveModifier(component.style)),
        enabled = enabled
    )
}
