package com.noorheroes.car24assignment.core.ui.dialog

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

data class DialogRequest(
    val title: String,
    val message: String,
    val confirmLabel: String = "OK",
    val dismissLabel: String? = null,
    val onConfirm: () -> Unit = {},
    val onDismiss: () -> Unit = {}
)

object DialogController {
    private val _requests = MutableSharedFlow<DialogRequest>()
    val requests = _requests.asSharedFlow()

    suspend fun show(request: DialogRequest) {
        _requests.emit(request)
    }
}
