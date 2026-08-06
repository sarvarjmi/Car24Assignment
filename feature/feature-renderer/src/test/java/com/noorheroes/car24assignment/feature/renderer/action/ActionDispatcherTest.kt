package com.noorheroes.car24assignment.feature.renderer.action

import com.noorheroes.car24assignment.core.model.domain.Action
import com.noorheroes.car24assignment.core.navigation.navigator.AppNavigator
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class ActionDispatcherTest {

    private lateinit var dispatcher: ActionDispatcher
    private val navigator = mockk<AppNavigator>(relaxed = true)

    @Before
    fun setup() {
        dispatcher = ActionDispatcher(navigator)
    }

    @Test
    fun `given navigate action when dispatched then calls navigator`() {
        val action = Action(
            type = "navigate",
            payload = mapOf("route" to "test_route")
        )

        dispatcher.dispatch(action)

        verify { navigator.navigate("test_route") }
    }

    @Test
    fun `given custom handler when action dispatched then calls handler`() {
        var called = false
        dispatcher.registerHandler("custom") { _ -> called = true }

        dispatcher.dispatch(Action(type = "custom"))

        assert(called)
    }
}
