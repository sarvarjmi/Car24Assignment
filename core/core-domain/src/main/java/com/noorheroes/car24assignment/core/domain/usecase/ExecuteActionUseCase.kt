package com.noorheroes.car24assignment.core.domain.usecase

import com.noorheroes.car24assignment.core.model.domain.Action
import javax.inject.Inject

class ExecuteActionUseCase @Inject constructor() {
    suspend operator fun invoke(action: Action, dispatcher: (Action) -> Unit) {
        // Business logic for action validation could go here
        dispatcher(action)
    }
}
