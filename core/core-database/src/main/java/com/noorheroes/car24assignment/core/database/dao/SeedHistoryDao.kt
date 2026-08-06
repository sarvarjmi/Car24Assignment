package com.noorheroes.car24assignment.core.database.dao

import androidx.room.*
import com.noorheroes.car24assignment.core.database.entity.SeedHistoryEntity

@Dao
interface SeedHistoryDao {
    @Query("SELECT * FROM seed_history WHERE completed = 1 ORDER BY seedVersion DESC LIMIT 1")
    suspend fun getLastCompletedSeed(): SeedHistoryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeedHistory(seedHistory: SeedHistoryEntity)
}
