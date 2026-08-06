package com.noorheroes.car24assignment.core.database.dao

import androidx.room.*
import com.noorheroes.car24assignment.core.database.entity.SectionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SectionDao {
    @Query("SELECT * FROM sections WHERE screenId = :screenId ORDER BY displayOrder ASC")
    fun getSectionsByScreenId(screenId: String): Flow<List<SectionEntity>>

    @Query("SELECT * FROM sections WHERE screenId = :screenId ORDER BY displayOrder ASC")
    suspend fun getSectionsByScreenIdSync(screenId: String): List<SectionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSections(sections: List<SectionEntity>)

    @Query("DELETE FROM sections WHERE screenId = :screenId")
    suspend fun deleteSectionsByScreenId(screenId: String)
}
