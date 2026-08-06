package com.noorheroes.car24assignment.feature.renderer.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import com.noorheroes.car24assignment.core.model.domain.Component
import com.noorheroes.car24assignment.feature.renderer.engine.RenderComponent

@Composable
fun ColumnWidget(component: Component) {
    Column {
        component.children?.forEach { child ->
            RenderComponent(child)
        }
    }
}

@Composable
fun RowWidget(component: Component) {
    Row {
        component.children?.forEach { child ->
            RenderComponent(child)
        }
    }
}
