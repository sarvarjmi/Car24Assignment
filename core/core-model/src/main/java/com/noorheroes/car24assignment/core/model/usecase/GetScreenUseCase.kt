package com.noorheroes.car24assignment.core.model.usecase

import com.noorheroes.car24assignment.core.model.domain.Screen
import com.noorheroes.car24assignment.core.model.repository.ScreenRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetScreenUseCase @Inject constructor(
    private val repository: ScreenRepository
) {
    operator fun invoke(screenId: String): Flow<Screen?> {
        return repository.observeScreen(screenId)
    }
}
