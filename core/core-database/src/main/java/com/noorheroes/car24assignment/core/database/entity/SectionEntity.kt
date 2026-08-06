package com.noorheroes.car24assignment.core.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "sections",
    foreignKeys = [
        ForeignKey(
            entity = ScreenEntity::class,
            parentColumns = ["screenId"],
            childColumns = ["screenId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["screenId"])]
)
data class SectionEntity(
    @PrimaryKey val sectionId: String,
    val screenId: String,
    val type: String,
    val title: String?,
    val displayOrder: Int,
    val visibility: Boolean,
    val updatedAt: Long
)
