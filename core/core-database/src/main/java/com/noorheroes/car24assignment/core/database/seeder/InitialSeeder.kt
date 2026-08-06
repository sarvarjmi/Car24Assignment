package com.noorheroes.car24assignment.core.database.seeder

import android.content.Context
import com.noorheroes.car24assignment.core.database.dao.ScreenDao
import com.noorheroes.car24assignment.core.database.dao.SectionDao
import com.noorheroes.car24assignment.core.database.dao.ComponentDao
import com.noorheroes.car24assignment.core.database.dao.SeedHistoryDao
import com.noorheroes.car24assignment.core.database.entity.ScreenEntity
import com.noorheroes.car24assignment.core.database.entity.SectionEntity
import com.noorheroes.car24assignment.core.database.entity.ComponentEntity
import com.noorheroes.car24assignment.core.database.entity.SeedHistoryEntity
import com.noorheroes.car24assignment.core.model.json.ScreenModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import javax.inject.Inject
import javax.inject.Singleton
import timber.log.Timber

@Singleton
class InitialSeeder @Inject constructor(
    @ApplicationContext private val context: Context,
    private val screenDao: ScreenDao,
    private val sectionDao: SectionDao,
    private val componentDao: ComponentDao,
    private val seedHistoryDao: SeedHistoryDao,
    private val json: Json
) {
    suspend fun seedIfNeeded() {
        val lastSeed = seedHistoryDao.getLastCompletedSeed()
        if (lastSeed != null && lastSeed.completed) {
            Timber.d("Database already seeded. Skipping.")
            return
        }

        try {
            val jsonString = context.assets.open("home.json").bufferedReader().use { it.readText() }
            val screenModel = json.decodeFromString<ScreenModel>(jsonString)

            seedDatabase(screenModel)

            seedHistoryDao.insertSeedHistory(
                SeedHistoryEntity(
                    seedVersion = screenModel.version,
                    seedTime = System.currentTimeMillis(),
                    completed = true,
                    checksum = null
                )
            )
            Timber.d("Database seeded successfully from assets.")
        } catch (e: Exception) {
            Timber.e(e, "Error seeding database")
        }
    }

    private suspend fun seedDatabase(model: ScreenModel) {
        val now = System.currentTimeMillis()
        
        // 1. Insert Screen
        screenDao.insertScreen(
            ScreenEntity(
                screenId = model.id,
                name = model.title,
                description = null,
                schemaVersion = 1,
                rendererVersion = 1,
                themeId = null,
                configurationId = null,
                createdAt = now,
                updatedAt = now,
                isActive = true
            )
        )

        // 2. Create a default section for all components for now
        val sectionId = "${model.id}_default_section"
        sectionDao.insertSections(
            listOf(
                SectionEntity(
                    sectionId = sectionId,
                    screenId = model.id,
                    type = "default",
                    title = "Main Section",
                    displayOrder = 0,
                    visibility = true,
                    updatedAt = now
                )
            )
        )

        // 3. Insert Components
        val componentEntities = model.components.mapIndexed { index, component ->
            ComponentEntity(
                componentId = component.id,
                sectionId = sectionId,
                componentType = component.type,
                componentJson = json.encodeToJsonElement(component).toString(),
                displayOrder = index,
                version = 1,
                updatedAt = now
            )
        }
        componentDao.insertComponents(componentEntities)
    }
}
