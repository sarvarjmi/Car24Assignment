package com.noorheroes.car24assignment.core.domain.usecase

import com.noorheroes.car24assignment.core.model.repository.SeedRepository
import javax.inject.Inject

class SeedInitialDatabaseUseCase @Inject constructor(
    private val seedRepository: SeedRepository
) {
    suspend operator fun invoke(seedAction: suspend () -> Unit) {
        if (!seedRepository.isDatabaseSeeded()) {
            seedAction()
        }
    }
}
