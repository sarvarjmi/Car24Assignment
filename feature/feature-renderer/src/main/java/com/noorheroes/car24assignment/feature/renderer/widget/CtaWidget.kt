package com.noorheroes.car24assignment.feature.renderer.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.noorheroes.car24assignment.core.model.domain.Component
import com.noorheroes.car24assignment.feature.renderer.action.LocalActionDispatcher
import com.noorheroes.car24assignment.feature.renderer.resolver.StyleResolver

@Composable
fun CtaWidget(component: Component.Cta) {
    val actionDispatcher = LocalActionDispatcher.current

    Button(
        onClick = {
            component.actions["click"]?.let { actionDispatcher.dispatch(it, component) }
        },
        modifier = Modifier
            .fillMaxWidth()
            .then(StyleResolver.resolveModifier(component.style, component.visibility))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text = component.text)
    }
}
