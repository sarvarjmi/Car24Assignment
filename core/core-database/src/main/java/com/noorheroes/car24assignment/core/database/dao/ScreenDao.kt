package com.noorheroes.car24assignment.core.database.dao

import androidx.room.*
import com.noorheroes.car24assignment.core.database.entity.ScreenEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScreenDao {
    @Query("SELECT * FROM screens WHERE screenId = :screenId")
    fun getScreenById(screenId: String): Flow<ScreenEntity?>

    @Query("SELECT * FROM screens WHERE isActive = 1")
    fun getActiveScreens(): Flow<List<ScreenEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScreen(screen: ScreenEntity)

    @Delete
    suspend fun deleteScreen(screen: ScreenEntity)
}
