package com.noorheroes.car24assignment.feature.renderer.registry

import androidx.compose.runtime.Composable
import com.noorheroes.car24assignment.core.model.domain.Component
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class ComponentRegistryTest {

    private lateinit var registry: ComponentRegistry

    @Before
    fun setup() {
        registry = ComponentRegistry()
    }

    @Test
    fun `given registered widget when retrieved then returns widget`() {
        val type = "test_widget"
        val widget: Widget = { _ -> }
        
        registry.register(type, widget)
        val result = registry.getWidget(type)
        
        assertNotNull(result)
    }

    @Test
    fun `given unknown widget type when retrieved then returns fallback widget`() {
        val result = registry.getWidget("unknown")
        assertNotNull(result)
    }
}
