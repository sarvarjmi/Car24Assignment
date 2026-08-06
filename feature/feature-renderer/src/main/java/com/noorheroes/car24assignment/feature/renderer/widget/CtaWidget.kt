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

@Composable
fun CtaWidget(component: Component) {
    val actionDispatcher = LocalActionDispatcher.current
    val text = component.properties["text"] as? String ?: "Click Me"

    Button(
        onClick = {
            component.actions["click"]?.let { actionDispatcher.dispatch(it) }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text = text)
    }
}
