package com.noorheroes.car24assignment.core.domain.usecase

import com.noorheroes.car24assignment.core.model.repository.ScreenRepository
import javax.inject.Inject

class UpdateScreenConfigUseCase @Inject constructor(
    private val repository: ScreenRepository
) {
    suspend operator fun invoke(screenId: String, name: String, description: String?, configJson: String) {
        repository.updateScreenMetadata(screenId, name, description)
        repository.updateScreenConfig(screenId, configJson)
    }
}
