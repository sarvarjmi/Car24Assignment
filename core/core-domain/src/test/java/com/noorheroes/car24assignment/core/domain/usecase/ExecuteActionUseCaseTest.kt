package com.noorheroes.car24assignment.core.domain.usecase

import com.noorheroes.car24assignment.core.model.domain.Action
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ExecuteActionUseCaseTest {

    private val useCase = ExecuteActionUseCase()

    @Test
    fun `given action when invoked then calls dispatcher`() = runTest {
        val action = Action(type = "test")
        var calledAction: Action? = null
        
        useCase(action) { calledAction = it }
        
        assert(calledAction == action)
    }
}
