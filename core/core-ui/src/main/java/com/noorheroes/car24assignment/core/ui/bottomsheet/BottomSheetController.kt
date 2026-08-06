package com.noorheroes.car24assignment.core.ui.bottomsheet

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

data class BottomSheetRequest(
    val title: String? = null,
    val contentId: String? = null // For dynamic SDUI content
)

object BottomSheetController {
    private val _requests = MutableSharedFlow<BottomSheetRequest?>()
    val requests = _requests.asSharedFlow()

    suspend fun show(request: BottomSheetRequest) {
        _requests.emit(request)
    }

    suspend fun dismiss() {
        _requests.emit(null)
    }
}
