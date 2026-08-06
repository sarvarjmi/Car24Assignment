package com.noorheroes.car24assignment.core.model.usecase

import com.noorheroes.car24assignment.core.model.repository.ComponentRepository
import javax.inject.Inject

class GetComponentJsonUseCase @Inject constructor(
    private val repository: ComponentRepository
) {
    suspend operator fun invoke(componentId: String): String? {
        return repository.getComponentJson(componentId)
    }
}
