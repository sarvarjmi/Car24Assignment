package com.noorheroes.car24assignment.core.domain.usecase

import com.noorheroes.car24assignment.core.model.repository.SeedRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SeedInitialDatabaseUseCaseTest {

    private val repository = mockk<SeedRepository>()
    private val useCase = SeedInitialDatabaseUseCase(repository)

    @Test
    fun `given database not seeded when invoked then calls seedAction`() = runTest {
        coEvery { repository.isDatabaseSeeded() } returns false
        var called = false
        
        useCase { called = true }
        
        assert(called)
    }

    @Test
    fun `given database already seeded when invoked then does not call seedAction`() = runTest {
        coEvery { repository.isDatabaseSeeded() } returns true
        var called = false
        
        useCase { called = true }
        
        assert(!called)
    }
}
