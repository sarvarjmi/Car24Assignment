package com.noorheroes.car24assignment.feature.renderer.widget

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.noorheroes.car24assignment.core.model.domain.Component
import com.noorheroes.car24assignment.feature.renderer.action.LocalActionDispatcher
import com.noorheroes.car24assignment.feature.renderer.resolver.StyleResolver

@Composable
fun ButtonWidget(component: Component) {
    val text = component.properties["text"] as? String ?: ""
    val actionDispatcher = LocalActionDispatcher.current

    Button(
        onClick = {
            component.actions["click"]?.let { actionDispatcher.dispatch(it, component) }
        },
        modifier = Modifier.then(StyleResolver.resolveModifier(component.style, component.visibility))
    ) {
        Text(text = text)
    }
}
