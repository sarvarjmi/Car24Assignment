package com.noorheroes.car24assignment.core.database.dao

import androidx.room.*
import com.noorheroes.car24assignment.core.database.entity.ComponentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ComponentDao {
    @Query("SELECT * FROM components WHERE componentId = :componentId")
    fun getComponentById(componentId: String): Flow<ComponentEntity?>

    @Query("SELECT componentJson FROM components WHERE componentId = :componentId")
    suspend fun getComponentJsonById(componentId: String): String?

    @Query("SELECT * FROM components WHERE sectionId = :sectionId ORDER BY displayOrder ASC")
    fun getComponentsBySectionId(sectionId: String): Flow<List<ComponentEntity>>

    @Query("SELECT * FROM components WHERE sectionId = :sectionId ORDER BY displayOrder ASC")
    suspend fun getComponentsBySectionIdSync(sectionId: String): List<ComponentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComponents(components: List<ComponentEntity>)

    @Query("UPDATE components SET componentJson = :json, updatedAt = :updatedAt WHERE componentId = :componentId")
    suspend fun updateComponentJson(componentId: String, json: String, updatedAt: Long)

    @Query("DELETE FROM components WHERE sectionId = :sectionId")
    suspend fun deleteComponentsBySectionId(sectionId: String)

    @Query("DELETE FROM components WHERE sectionId IN (SELECT sectionId FROM sections WHERE screenId = :screenId)")
    suspend fun deleteComponentsByScreenId(screenId: String)
}
