package com.noorheroes.car24assignment.core.model.repository

import com.noorheroes.car24assignment.core.model.domain.Screen
import kotlinx.coroutines.flow.Flow

interface ScreenRepository {
    fun observeScreen(screenId: String): Flow<Screen?>
    suspend fun getScreen(screenId: String): Screen?
    suspend fun saveScreen(screen: Screen)
}
