package com.noorheroes.car24assignment.core.ui.snackbar

import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

data class SnackbarMessage(
    val message: String,
    val actionLabel: String? = null,
    val withDismissAction: Boolean = false,
    val onAction: (() -> Unit)? = null
)

object SnackbarController {
    private val scope = CoroutineScope(Dispatchers.Main.immediate)
    private val _messages = MutableSharedFlow<SnackbarMessage>()
    val messages = _messages.asSharedFlow()

    fun show(message: String, actionLabel: String? = null) {
        scope.launch {
            _messages.emit(SnackbarMessage(message, actionLabel))
        }
    }

    suspend fun observe(snackbarHostState: SnackbarHostState) {
        messages.collect { msg ->
            snackbarHostState.showSnackbar(
                message = msg.message,
                actionLabel = msg.actionLabel,
                withDismissAction = msg.withDismissAction
            )
        }
    }
}
