package com.noorheroes.car24assignment.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_config")
data class AppConfigEntity(
    @PrimaryKey val configId: String = "default",
    val landingScreen: String,
    val defaultTheme: String,
    val debugMode: Boolean,
    val currentSchema: Int,
    val rendererVersion: Int
)
