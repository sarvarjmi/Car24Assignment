package com.noorheroes.car24assignment.core.json.version

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VersionValidator @Inject constructor() {

    private val currentRendererVersion = "1.0.0"

    /**
     * Checks if the given rendererVersion required by the JSON is compatible 
     * with the current engine.
     * Rule: Major versions must match. Minor versions of JSON must be <= engine's minor version.
     */
    fun isCompatible(requiredVersion: String?): Boolean {
        if (requiredVersion == null) return true
        
        return try {
            val required = requiredVersion.split(".").map { it.toInt() }
            val current = currentRendererVersion.split(".").map { it.toInt() }
            
            if (required.size < 2 || current.size < 2) return false
            
            // Major version must match exactly for safety in SDUI
            if (required[0] != current[0]) return false
            
            // Minor version: current engine must be >= required
            if (required[1] > current[1]) return false
            
            true
        } catch (e: Exception) {
            false
        }
    }
}
