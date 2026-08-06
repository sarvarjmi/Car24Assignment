package com.noorheroes.car24assignment.feature.home.presentation

import app.cash.turbine.test
import com.noorheroes.car24assignment.core.domain.usecase.GetScreenUseCase
import com.noorheroes.car24assignment.core.model.domain.Layout
import com.noorheroes.car24assignment.core.model.domain.Metadata
import com.noorheroes.car24assignment.core.model.domain.Screen
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
        val mockScreen = Screen(
            metadata = Metadata(id = "home", name = "Home", schemaVersion = "1.0.0", rendererVersion = "1.0.0", createdAt = 0, updatedAt = 0),
            layout = Layout(type = "LazyColumn")
        )
        every { getScreenUseCase("home_screen") } returns flowOf(mockScreen)

        viewModel = HomeViewModel(getScreenUseCase)

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is HomeUiState.Success)
            assertEquals("home", (state as HomeUiState.Success).screen.metadata.id)
        }
    }
}
