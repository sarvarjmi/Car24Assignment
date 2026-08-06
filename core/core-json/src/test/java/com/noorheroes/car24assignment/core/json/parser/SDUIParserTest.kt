package com.noorheroes.car24assignment.core.json.parser

import com.noorheroes.car24assignment.core.model.json.ComponentModel
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SDUIParserTest {

    private lateinit var parser: SDUIParser
    private val json = Json { ignoreUnknownKeys = true }

    @Before
    fun setup() {
        parser = SDUIParser(json)
    }

    @Test
    fun `given valid screen json when parsed then returns screen model`() {
        val jsonString = """
            {
                "metadata": {
                    "id": "home_screen",
                    "name": "Home",
                    "schemaVersion": "1.0.0",
                    "rendererVersion": "1.0.0",
                    "createdAt": 0,
                    "updatedAt": 0
                },
                "layout": { "type": "LazyColumn" },
                "sections": [
                    {
                        "id": "sec_1",
                        "type": "default",
                        "order": 1,
                        "components": [
                            {
                                "id": "banner_1",
                                "type": "banner",
                                "properties": { "imageUrl": "url" }
                            }
                        ]
                    }
                ]
            }
        """.trimIndent()

        val result = parser.parseScreen(jsonString)
        assertEquals("home_screen", result.metadata.id)
        assertEquals("Home", result.metadata.name)
        assertEquals(1, result.sections.size)
        assertEquals("banner_1", result.sections[0].components[0].id)
    }

    @Test
    fun `given valid component json when parsed then returns component model`() {
        val jsonString = """
            {
                "id": "comp_1",
                "type": "cta",
                "properties": { "text": "Hello" }
            }
        """.trimIndent()

        val result = parser.parseComponent(jsonString)
        assertEquals("comp_1", result.id)
        assertEquals("cta", result.componentType)
        assertTrue(result is ComponentModel.Cta)
        assertEquals("Hello", (result as ComponentModel.Cta).properties.text)
    }
}
