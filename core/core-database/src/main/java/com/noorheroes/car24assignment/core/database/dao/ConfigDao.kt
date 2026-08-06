package com.noorheroes.car24assignment.core.database.dao

import androidx.room.*
import com.noorheroes.car24assignment.core.database.entity.AppConfigEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConfigDao {
    @Query("SELECT * FROM app_config WHERE configId = 'default'")
    fun AppConfig(): Flow<AppConfigEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveConfig(config: AppConfigEntity)
}
