package com.noorheroes.car24assignment.startup

import com.noorheroes.car24assignment.core.database.seeder.InitialSeeder
import com.noorheroes.car24assignment.core.domain.usecase.SeedInitialDatabaseUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StartupManager @Inject constructor(
    private val seedInitialDatabaseUseCase: SeedInitialDatabaseUseCase,
    private val initialSeeder: InitialSeeder
) {
    private val startupScope = CoroutineScope(Dispatchers.IO)
    
    private val _isInitialized = MutableStateFlow<Result<Boolean>?>(null)
    val isInitialized = _isInitialized.asStateFlow()

    fun onAppStart() {
        startupScope.launch {
            try {
                seedInitialDatabaseUseCase {
                    initialSeeder.seedIfNeeded()
                }
                _isInitialized.value = Result.success(true)
            } catch (e: Exception) {
                _isInitialized.value = Result.failure(e)
            }
        }
    }
}
