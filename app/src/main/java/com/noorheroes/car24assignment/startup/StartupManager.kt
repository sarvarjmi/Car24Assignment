package com.noorheroes.car24assignment.startup

import com.noorheroes.car24assignment.core.database.seeder.InitialSeeder
import com.noorheroes.car24assignment.core.domain.usecase.SeedInitialDatabaseUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StartupManager @Inject constructor(
    private val seedInitialDatabaseUseCase: SeedInitialDatabaseUseCase,
    private val initialSeeder: InitialSeeder
) {
    private val startupScope = CoroutineScope(Dispatchers.IO)

    fun onAppStart() {
        startupScope.launch {
            seedInitialDatabaseUseCase {
                initialSeeder.seedIfNeeded()
            }
        }
    }
}
