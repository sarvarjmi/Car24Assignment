package com.noorheroes.car24assignment.core.database.seeder

import android.content.Context
import com.noorheroes.car24assignment.core.common.logging.Logger
import com.noorheroes.car24assignment.core.database.dao.*
import com.noorheroes.car24assignment.core.database.entity.*
import com.noorheroes.car24assignment.core.json.validator.SDUIValidator
import com.noorheroes.car24assignment.core.model.json.ScreenModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InitialSeeder @Inject constructor(
    @ApplicationContext private val context: Context,
    private val screenDao: ScreenDao,
    private val sectionDao: SectionDao,
    private val componentDao: ComponentDao,
    private val seedHistoryDao: SeedHistoryDao,
    private val validator: SDUIValidator,
    private val logger: Logger,
    private val json: Json
) {
    private val TAG = "InitialSeeder"

    suspend fun seedIfNeeded() {
        val lastSeed = seedHistoryDao.getLastCompletedSeed()
        if (lastSeed != null && lastSeed.completed) {
            logger.d(TAG, "Database already seeded. Skipping.")
            return
        }

        try {
            seedScreen("landing.json")
            seedScreen("home.json")
            seedScreen("deals.json")
            seedScreen("profile.json")

            seedHistoryDao.insertSeedHistory(
                SeedHistoryEntity(
                    seedVersion = 1,
                    seedTime = System.currentTimeMillis(),
                    completed = true,
                    checksum = null
                )
            )
            logger.d(TAG, "Database seeded successfully from assets.")
        } catch (e: Exception) {
            logger.e(TAG, "Error seeding database", e)
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

        val screenModel = json.decodeFromString<ScreenModel>(jsonString)
        seedDatabase(screenModel)
    }

    private suspend fun seedDatabase(model: ScreenModel) {
        val now = System.currentTimeMillis()
        
        // 1. Insert Screen
        screenDao.insertScreen(
            ScreenEntity(
                screenId = model.metadata.id,
                name = model.metadata.name,
                description = model.metadata.description,
                schemaVersion = model.metadata.schemaVersion,
                rendererVersion = model.metadata.rendererVersion,
                configurationJson = json.encodeToJsonElement(model.configuration).toString(),
                themeJson = json.encodeToJsonElement(model.theme).toString(),
                layoutType = model.layout.type,
                layoutStyleJson = model.layout.style?.toString(),
                createdAt = model.metadata.createdAt,
                updatedAt = model.metadata.updatedAt,
                isActive = true
            )
        )

        // 2. Insert Sections
        model.sections.forEach { section ->
            sectionDao.insertSections(
                listOf(
                    SectionEntity(
                        sectionId = section.id,
                        screenId = model.metadata.id,
                        type = section.type,
                        title = section.title,
                        displayOrder = section.order,
                        visibility = section.visibility,
                        updatedAt = now
                    )
                )
            )

            // 3. Insert Components for each section
            val componentEntities = section.components.mapIndexed { index, component ->
                ComponentEntity(
                    componentId = component.id,
                    sectionId = section.id,
                    componentType = component.componentType,
                    componentJson = json.encodeToJsonElement(component).toString(),
                    displayOrder = index,
                    version = 1,
                    updatedAt = now
                )
            }
            componentDao.insertComponents(componentEntities)
        }
    }
}
