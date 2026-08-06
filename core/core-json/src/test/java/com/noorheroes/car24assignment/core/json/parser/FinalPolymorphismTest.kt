package com.noorheroes.car24assignment.core.json.parser

import com.noorheroes.car24assignment.core.model.json.ComponentModel
import kotlinx.serialization.json.Json
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class FinalPolymorphismTest {

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        encodeDefaults = true
        isLenient = true
        classDiscriminator = "type"
    }

    @Test
    fun `verify chip_group and children parsing`() {
        val rawJson = """
            {
              "id": "cat_chips",
              "type": "chip_group",
              "children": [
                { "id": "chip_1", "type": "chip", "properties": { "text": "Hatchback" } }
              ]
            }
        """.trimIndent()

        val component = json.decodeFromString<ComponentModel>(rawJson)
        assertNotNull(component)
        assertTrue("Expected ChipGroup subclass", component is ComponentModel.ChipGroup)
        val chipGroup = component as ComponentModel.ChipGroup
        assertNotNull(chipGroup.children)
        assertTrue("Expected 1 child", chipGroup.children?.size == 1)
        assertTrue("Expected child to be Chip", chipGroup.children?.first() is ComponentModel.Chip)
    }
}
