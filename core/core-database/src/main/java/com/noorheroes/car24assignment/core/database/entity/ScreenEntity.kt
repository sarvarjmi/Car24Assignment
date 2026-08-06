package com.noorheroes.car24assignment.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "screens")
data class ScreenEntity(
    @PrimaryKey val screenId: String,
    val name: String,
    val description: String?,
    val schemaVersion: Int,
    val rendererVersion: Int,
    val themeId: String?,
    val configurationId: String?,
    val createdAt: Long,
    val updatedAt: Long,
    val isActive: Boolean
)
