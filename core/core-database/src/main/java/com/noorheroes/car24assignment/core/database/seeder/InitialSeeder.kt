package com.noorheroes.car24assignment.core.database.seeder

import android.content.Context
import com.noorheroes.car24assignment.core.common.logging.Logger
import com.noorheroes.car24assignment.core.database.dao.*
import com.noorheroes.car24assignment.core.database.entity.*
import com.noorheroes.car24assignment.core.json.validator.SDUIValidator
import com.noorheroes.car24assignment.core.model.json.ScreenModel
import com.noorheroes.car24assignment.core.model.repository.ScreenRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InitialSeeder @Inject constructor(
    @ApplicationContext private val context: Context,
    private val screenRepository: ScreenRepository,
    private val seedHistoryDao: SeedHistoryDao,
    private val validator: SDUIValidator,
    private val logger: Logger,
    private val json: Json
) {
    private val TAG = "InitialSeeder"

    suspend fun seedIfNeeded() {
        val lastSeed = seedHistoryDao.getLastCompletedSeed()
        val currentSeedVersion = 1 // Force re-seed with highest version
        if (lastSeed != null && lastSeed.completed && lastSeed.seedVersion >= currentSeedVersion) {
            logger.d(TAG, "Database already seeded with version ${lastSeed.seedVersion}. Skipping.")
            return
        }

        try {
            logger.d(TAG, "Starting database seeding v$currentSeedVersion...")
            seedScreen("landing.json")
            seedScreen("home.json")
            seedScreen("deals.json")
            seedScreen("profile.json")

            seedHistoryDao.insertSeedHistory(
                SeedHistoryEntity(
                    seedVersion = currentSeedVersion,
                    seedTime = System.currentTimeMillis(),
                    completed = true,
                    checksum = null
                )
            )
            logger.d(TAG, "Database seeded successfully v$currentSeedVersion.")
        } catch (e: Exception) {
            logger.e(TAG, "Error seeding database v$currentSeedVersion", e)
        }
    }

    private suspend fun seedScreen(assetName: String) {
        val jsonString = context.assets.open(assetName).bufferedReader().use { it.readText() }
        
        // Validate before seeding
        val validationResult = validator.validateScreenJson(jsonString)
        if (validationResult.isFailure) {
            logger.e(TAG, "Validation failed for $assetName: ${validationResult.exceptionOrNull()?.message}")
            return
        }

        val screenModel = json.decodeFromString(ScreenModel.serializer(), jsonString)
        screenRepository.saveScreenModel(screenModel)
    }
}
