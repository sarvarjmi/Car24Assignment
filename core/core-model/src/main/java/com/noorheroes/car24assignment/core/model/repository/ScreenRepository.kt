package com.noorheroes.car24assignment.core.model.repository

import com.noorheroes.car24assignment.core.model.domain.Screen
import kotlinx.coroutines.flow.Flow

/**
 * Repository for handling Screen-level SDUI data.
 * Provides streams for observing full screen hierarchies from persistence.
 */
interface ScreenRepository {
    /**
     * Returns a [Flow] that emits the [Screen] model whenever the underlying data changes.
     */
    fun observeScreen(screenId: String): Flow<Screen?>
    
    suspend fun getScreens(): List<Screen>

    suspend fun getScreen(screenId: String): Screen?
    suspend fun saveScreen(screen: Screen)
    suspend fun updateScreenMetadata(screenId: String, name: String, description: String?)
    suspend fun updateScreenConfig(screenId: String, configJson: String)
}
