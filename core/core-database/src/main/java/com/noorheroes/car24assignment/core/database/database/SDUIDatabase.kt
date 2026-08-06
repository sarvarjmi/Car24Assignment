package com.noorheroes.car24assignment.core.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.noorheroes.car24assignment.core.database.dao.*
import com.noorheroes.car24assignment.core.database.entity.*

@Database(
    entities = [
        ScreenEntity::class,
        SectionEntity::class,
        ComponentEntity::class,
        AppConfigEntity::class,
        SeedHistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SDUIDatabase : RoomDatabase() {
    abstract fun screenDao(): ScreenDao
    abstract fun sectionDao(): SectionDao
    abstract fun componentDao(): ComponentDao
    abstract fun configDao(): ConfigDao
    abstract fun seedHistoryDao(): SeedHistoryDao
}
