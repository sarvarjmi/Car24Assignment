package com.noorheroes.car24assignment.core.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "components",
    foreignKeys = [
        ForeignKey(
            entity = SectionEntity::class,
            parentColumns = ["sectionId"],
            childColumns = ["sectionId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["sectionId"])]
)
data class ComponentEntity(
    @PrimaryKey val componentId: String,
    val sectionId: String,
    val componentType: String,
    val componentJson: String, // Validated JSON string
    val displayOrder: Int,
    val version: Int,
    val updatedAt: Long
)
