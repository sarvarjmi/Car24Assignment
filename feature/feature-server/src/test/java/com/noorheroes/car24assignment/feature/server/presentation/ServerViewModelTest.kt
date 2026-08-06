package com.noorheroes.car24assignment.feature.server.presentation

import app.cash.turbine.test
import com.noorheroes.car24assignment.core.domain.usecase.*
import com.noorheroes.car24assignment.core.json.validator.SDUIValidator
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ServerViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val getScreensUseCase = mockk<GetScreensUseCase>()
    private val getScreenUseCase = mockk<GetScreenUseCase>()
    private val getComponentJsonUseCase = mockk<GetComponentJsonUseCase>()
    private val updateComponentUseCase = mockk<UpdateComponentUseCase>()
    private val validator = mockk<SDUIValidator>()
    private val json = Json { ignoreUnknownKeys = true }
    private lateinit var viewModel: ServerViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        coEvery { getScreensUseCase() } returns emptyList()
        viewModel = ServerViewModel(
            getScreensUseCase, 
            getScreenUseCase, 
            getComponentJsonUseCase, 
            updateComponentUseCase, 
            validator, 
            json
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given valid component id when loadComponent called then emits editing state`() = runTest {
        val mockJson = """{"id":"1","type":"banner"}"""
        coEvery { getComponentJsonUseCase("1") } returns mockJson

        viewModel.loadComponent("1")

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is ServerUiState.Editing)
            assertEquals(mockJson, (state as ServerUiState.Editing).json)
        }
    }

    @Test
    fun `given invalid json when saveJson called then emits error state`() = runTest {
        every { validator.validateComponentJson(any()) } returns Result.failure(Exception("Invalid"))

        viewModel.saveJson("1", "invalid")

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is ServerUiState.Error)
        }
    }
}
