package com.noorheroes.car24assignment.feature.renderer.action

import androidx.compose.runtime.staticCompositionLocalOf
import com.noorheroes.car24assignment.core.common.logging.Logger
import com.noorheroes.car24assignment.core.model.domain.Action
import com.noorheroes.car24assignment.core.model.domain.Component
import com.noorheroes.car24assignment.core.navigation.navigator.AppNavigator
import com.noorheroes.car24assignment.core.ui.snackbar.SnackbarController
import com.noorheroes.car24assignment.core.ui.bottomsheet.BottomSheetController
import com.noorheroes.car24assignment.core.ui.bottomsheet.BottomSheetRequest
import com.noorheroes.car24assignment.core.ui.dialog.DialogController
import com.noorheroes.car24assignment.core.ui.dialog.DialogRequest
import com.noorheroes.car24assignment.core.domain.usecase.UpdateComponentUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.*
import javax.inject.Inject

class ActionDispatcher @Inject constructor(
    private val navigator: AppNavigator,
    private val updateComponentUseCase: UpdateComponentUseCase,
    private val actionValidator: ActionValidator,
    private val logger: Logger,
    private val json: Json
) {
    private val TAG = "ActionDispatcher"
    private val handlers = mutableMapOf<String, (Action) -> Unit>()
    private val scope = CoroutineScope(Dispatchers.Main)

    init {
        // Register default handlers
        registerHandler("navigate") { action ->
            val route = action.target ?: action.payload["route"] as? String
            if (route != null) {
                try {
                    navigator.navigate(route)
                } catch (e: Exception) {
                    logger.e(TAG, "Navigation failed, falling back to Landing", e)
                    navigator.navigate("landing")
                }
            } else {
                logger.e(TAG, "Navigation action missing target/route")
            }
        }
        registerHandler("back") { _ ->
            navigator.navigateBack()
        }
        registerHandler("composite") { action ->
            val actionsList = action.payload["actions"] as? List<*>
            actionsList?.filterIsInstance<Map<String, Any?>>()?.forEach { _ ->
                // Recursive dispatch logic here
            }
        }
        registerHandler("snackbar") { action ->
            val message = action.payload["message"] as? String
            if (message != null) {
                SnackbarController.show(message)
            }
        }
        registerHandler("dialog") { action ->
            val title = action.payload["title"] as? String ?: "Alert"
            val message = (action.payload["message"] as? String) ?: ""
            scope.launch {
                DialogController.show(DialogRequest(title = title, message = message))
            }
        }
        registerHandler("bottomsheet") { action ->
            val title = action.payload["title"] as? String
            val contentId = action.target
            scope.launch {
                BottomSheetController.show(BottomSheetRequest(title = title, contentId = contentId))
            }
        }
        registerHandler("refresh") { _ ->
            SnackbarController.show("Refreshing data...")
        }
        registerHandler("togglestate") { action ->
            val targetId = action.target ?: return@registerHandler
            scope.launch {
                logger.d(TAG, "Toggle state for $targetId")
                // Toggle logic placeholder
            }
        }
        registerHandler("updatecomponent") { action ->
            val targetId = action.target ?: return@registerHandler
            val payload = action.payload
            scope.launch {
                val jsonString = json.encodeToString(payload)
                updateComponentUseCase(targetId, jsonString)
                SnackbarController.show("Component $targetId updated")
            }
        }
        registerHandler("analytics") { action ->
            val eventName = action.target ?: "sdui_event"
            logger.i(TAG, "Analytics Event: $eventName, Payload: ${action.payload}")
        }
    }

    fun registerHandler(type: String, handler: (Action) -> Unit) {
        handlers[type] = handler
    }

    fun dispatch(action: Action, component: Component? = null) {
        // 0. Security & Basic Validation (Doc 25, Rule 22)
        if (!actionValidator.isValid(action)) {
            logger.w(TAG, "Action rejected by validator: ${action.type}")
            return
        }

        if (component != null && action.conditions.isNotEmpty()) {
            val allMet = action.conditions.all { ConditionEvaluator.evaluate(component, it) }
            if (!allMet) {
                logger.d(TAG, "Action conditions not met for type: ${action.type}")
                return
            }
        }

        val handler = handlers[action.type.lowercase()]
        if (handler != null) {
            handler(action)
        } else {
            logger.w(TAG, "No handler registered for action type: ${action.type}")
        }
    }

    fun dispatchAll(actions: List<Action>, component: Component? = null) {
        actions.sortedBy { it.priority }.forEach { dispatch(it, component) }
    }
}

val LocalActionDispatcher = staticCompositionLocalOf<ActionDispatcher> {
    error("No ActionDispatcher provided")
}
