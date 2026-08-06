package com.noorheroes.car24assignment.core.model.repository

interface SeedRepository {
    suspend fun isDatabaseSeeded(): Boolean
    suspend fun markAsSeeded(version: Int)
}
