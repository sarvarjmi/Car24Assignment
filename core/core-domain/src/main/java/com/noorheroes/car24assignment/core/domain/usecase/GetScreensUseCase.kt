package com.noorheroes.car24assignment.core.domain.usecase

import com.noorheroes.car24assignment.core.model.domain.Screen
import com.noorheroes.car24assignment.core.model.repository.ScreenRepository
import javax.inject.Inject

class GetScreensUseCase @Inject constructor(
    private val repository: ScreenRepository
) {
    suspend operator fun invoke(): List<Screen> {
        return repository.getScreens()
    }
}
