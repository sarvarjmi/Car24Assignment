package com.noorheroes.car24assignment.core.json.parser

import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
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
                "id": "home_screen",
                "title": "Home",
                "version": 1,
                "components": [
                    {
                        "id": "banner_1",
                        "type": "banner",
                        "properties": { "imageUrl": "url" }
                    }
                ]
            }
        """.trimIndent()

        val result = parser.parseScreen(jsonString)
        assertEquals("home_screen", result.id)
        assertEquals("Home", result.title)
        assertEquals(1, result.components.size)
        assertEquals("banner_1", result.components[0].id)
    }

    @Test
    fun `given valid component json when parsed then returns component model`() {
        val jsonString = """
            {
                "id": "comp_1",
                "type": "text",
                "properties": { "text": "Hello" }
            }
        """.trimIndent()

        val result = parser.parseComponent(jsonString)
        assertEquals("comp_1", result.id)
        assertEquals("text", result.type)
        assertNotNull(result.properties)
    }
}
