package com.noorheroes.car24assignment.core.json.validator

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import com.noorheroes.car24assignment.core.designsystem.token.*
import com.noorheroes.car24assignment.core.json.version.VersionValidator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SDUIValidator @Inject constructor(
    private val json: Json,
    private val versionValidator: VersionValidator
) {
    fun validateScreenJson(jsonString: String): Result<Unit> {
        return try {
            val element = json.parseToJsonElement(jsonString)
            val jsonObject = element.jsonObject
            
            // Check version
            val rendererVersion = jsonObject["metadata"]?.jsonObject?.get("rendererVersion")?.jsonPrimitive?.content
            if (!versionValidator.isCompatible(rendererVersion)) {
                return Result.failure(Exception("Incompatible renderer version: $rendererVersion"))
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun validateComponentJson(jsonString: String): Result<Unit> {
        return try {
            val element = json.parseToJsonElement(jsonString)
            val jsonObject = element.jsonObject
            
            // 1. Mandatory base fields
            jsonObject["id"]?.jsonPrimitive?.content ?: return Result.failure(Exception("Missing 'id'"))
            val type = jsonObject["type"]?.jsonPrimitive?.content ?: return Result.failure(Exception("Missing 'type'"))
            
            // 2. Widget-specific property validation (from Catalog)
            val props = jsonObject["properties"]?.jsonObject
            when (type.lowercase()) {
                "text" -> if (props?.containsKey("text") != true) return Result.failure(Exception("Text widget missing 'text' prop"))
                "image" -> if (props?.containsKey("url") != true) return Result.failure(Exception("Image widget missing 'url' prop"))
                "button" -> if (props?.containsKey("text") != true) return Result.failure(Exception("Button widget missing 'text' prop"))
                "car_card" -> {
                    if (props == null) return Result.failure(Exception("CarCard missing properties"))
                    if (!props.containsKey("title")) return Result.failure(Exception("CarCard missing 'title'"))
                    if (!props.containsKey("price")) return Result.failure(Exception("CarCard missing 'price'"))
                }
            }

            // 3. Style / Token validation
            jsonObject["style"]?.jsonObject?.let { style ->
                validateTokens(style)
            }

            // 4. Action validation
            jsonObject["actions"]?.jsonObject?.let { actions ->
                actions.forEach { (key, action) ->
                    val actionObj = action.jsonObject
                    if (!actionObj.containsKey("type")) {
                        return Result.failure(Exception("Action '$key' missing 'type'"))
                    }
                }
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun validateTokens(style: kotlinx.serialization.json.JsonObject) {
        // Validate padding tokens if string
        style["padding"]?.jsonObject?.values?.forEach { 
            val token = it.jsonPrimitive.content
            if (!token.contains(".") && !isNumeric(token)) {
                try { SpacingToken.valueOf(token.uppercase()) } catch (e: Exception) {
                    throw Exception("Invalid spacing token: $token")
                }
            }
        }
        // Validate colors
        style["background"]?.jsonObject?.get("color")?.jsonPrimitive?.content?.let { token ->
            try { ColorToken.valueOf(token.uppercase()) } catch (e: Exception) {
                throw Exception("Invalid color token: $token")
            }
        }
    }

    private fun isNumeric(toCheck: String): Boolean = toCheck.toDoubleOrNull() != null
}
