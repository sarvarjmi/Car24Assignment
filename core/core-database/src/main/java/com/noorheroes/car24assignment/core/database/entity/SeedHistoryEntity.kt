package com.noorheroes.car24assignment.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "seed_history")
data class SeedHistoryEntity(
    @PrimaryKey val seedVersion: Int,
    val seedTime: Long,
    val completed: Boolean,
    val checksum: String?
)
