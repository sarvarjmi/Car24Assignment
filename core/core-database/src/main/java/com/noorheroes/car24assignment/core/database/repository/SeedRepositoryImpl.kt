package com.noorheroes.car24assignment.core.database.repository

import com.noorheroes.car24assignment.core.database.dao.SeedHistoryDao
import com.noorheroes.car24assignment.core.database.entity.SeedHistoryEntity
import com.noorheroes.car24assignment.core.model.repository.SeedRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SeedRepositoryImpl @Inject constructor(
    private val seedHistoryDao: SeedHistoryDao
) : SeedRepository {

    override suspend fun isDatabaseSeeded(): Boolean {
        val lastSeed = seedHistoryDao.getLastCompletedSeed()
        return lastSeed != null && lastSeed.completed
    }

    override suspend fun markAsSeeded(version: Int) {
        seedHistoryDao.insertSeedHistory(
            SeedHistoryEntity(
                seedVersion = version,
                seedTime = System.currentTimeMillis(),
                completed = true,
                checksum = null
            )
        )
    }
}
