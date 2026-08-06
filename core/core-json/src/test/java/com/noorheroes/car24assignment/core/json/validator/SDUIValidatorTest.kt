package com.noorheroes.car24assignment.core.json.validator

import kotlinx.serialization.json.Json
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

class SDUIValidatorTest {

    private lateinit var validator: SDUIValidator
    private val json = Json { ignoreUnknownKeys = true }

    @Before
    fun setup() {
        validator = SDUIValidator(json)
    }

    @Test
    fun `given valid component json when validated then returns success`() {
        val jsonString = """
            {
                "id": "test_id",
                "type": "banner",
                "properties": { "title": "Hello" }
            }
        """.trimIndent()

        val result = validator.validateComponentJson(jsonString)
        assertTrue(result.isSuccess)
    }

    @Test
    fun `given json missing id when validated then returns failure`() {
        val jsonString = """
            {
                "type": "banner",
                "properties": { "title": "Hello" }
            }
        """.trimIndent()

        val result = validator.validateComponentJson(jsonString)
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("id") == true)
    }

    @Test
    fun `given json missing type when validated then returns failure`() {
        val jsonString = """
            {
                "id": "test_id",
                "properties": { "title": "Hello" }
            }
        """.trimIndent()

        val result = validator.validateComponentJson(jsonString)
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("type") == true)
    }

    @Test
    fun `given invalid json string when validated then returns failure`() {
        val jsonString = "invalid json"

        val result = validator.validateComponentJson(jsonString)
        assertFalse(result.isSuccess)
    }
}
