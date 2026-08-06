package com.noorheroes.car24assignment.feature.home.presentation

import app.cash.turbine.test
import com.noorheroes.car24assignment.core.model.domain.Screen
import com.noorheroes.car24assignment.core.model.usecase.GetScreenUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val getScreenUseCase = mockk<GetScreenUseCase>()
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewmodel initialized then emits success state`() = runTest {
        val mockScreen = Screen(id = "home", title = "Home", version = 1)
        every { getScreenUseCase("home_screen") } returns flowOf(mockScreen)

        viewModel = HomeViewModel(getScreenUseCase)

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is HomeUiState.Success)
            assertEquals("home", (state as HomeUiState.Success).screen.id)
        }
    }
}
