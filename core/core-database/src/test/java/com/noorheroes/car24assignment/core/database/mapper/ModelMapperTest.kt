package com.noorheroes.car24assignment.core.database.mapper

import com.noorheroes.car24assignment.core.database.entity.ComponentEntity
import com.noorheroes.car24assignment.core.database.entity.ScreenEntity
import com.noorheroes.car24assignment.core.database.entity.SectionEntity
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ModelMapperTest {

    private lateinit var mapper: ModelMapper
    private val json = Json { ignoreUnknownKeys = true }

    @Before
    fun setup() {
        mapper = ModelMapper(json)
    }

    @Test
    fun `given entity data when mapped to screen then returns domain screen`() {
        val screenEntity = ScreenEntity(
            screenId = "home",
            name = "Home",
            description = null,
            schemaVersion = 1,
            rendererVersion = 1,
            themeId = null,
            configurationId = null,
            createdAt = 0,
            updatedAt = 0,
            isActive = true
        )
        val sectionEntities = listOf(
            SectionEntity(
                sectionId = "sec_1",
                screenId = "home",
                type = "default",
                title = "Section 1",
                displayOrder = 0,
                visibility = true,
                updatedAt = 0
            )
        )
        val componentJson = """{"id":"comp_1","type":"banner","properties":{"title":"Welcome"}}"""
        val componentEntities = mapOf(
            "sec_1" to listOf(
                ComponentEntity(
                    componentId = "comp_1",
                    sectionId = "sec_1",
                    componentType = "banner",
                    componentJson = componentJson,
                    displayOrder = 0,
                    version = 1,
                    updatedAt = 0
                )
            )
        )

        val result = mapper.toScreen(screenEntity, sectionEntities, componentEntities)

        assertEquals("home", result.id)
        assertEquals("Home", result.title)
        assertEquals(1, result.sections.size)
        assertEquals("Section 1", result.sections[0].title)
        assertEquals(1, result.sections[0].components.size)
        assertEquals("comp_1", result.sections[0].components[0].id)
        assertEquals("Welcome", result.sections[0].components[0].properties["title"])
    }
}
