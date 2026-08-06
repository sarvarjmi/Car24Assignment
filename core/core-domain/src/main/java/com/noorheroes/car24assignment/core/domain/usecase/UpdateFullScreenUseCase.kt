package com.noorheroes.car24assignment.core.domain.usecase

import com.noorheroes.car24assignment.core.model.json.ScreenModel
import com.noorheroes.car24assignment.core.model.repository.ScreenRepository
import javax.inject.Inject

class UpdateFullScreenUseCase @Inject constructor(
    private val repository: ScreenRepository
) {
    suspend operator fun invoke(model: ScreenModel) {
        repository.saveScreenModel(model)
    }
}
