package com.noorheroes.car24assignment.core.json.validator

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import com.noorheroes.car24assignment.core.designsystem.token.*
import com.noorheroes.car24assignment.core.json.version.VersionValidator
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Multi-stage validation engine for SDUI payloads.
 * Validates syntax, mandatory fields, design tokens, and hierarchy integrity.
 */
@Singleton
class SDUIValidator @Inject constructor(
    private val json: Json,
    private val versionValidator: VersionValidator
) {
    private val MAX_DEPTH = 10

    /**
     * Validates a complete screen JSON string.
     * Checks for renderer version compatibility, duplicate IDs, and circular references.
     */
    fun validateScreenJson(jsonString: String): Result<Unit> {
        return try {
            val element = json.parseToJsonElement(jsonString)
            val jsonObject = element as? JsonObject ?: return Result.failure(Exception("Not a JSON object"))
            
            // 1. Check version
            val metadata = jsonObject["metadata"] as? JsonObject
            val rendererVersion = (metadata?.get("rendererVersion") as? JsonPrimitive)?.content
            if (!versionValidator.isCompatible(rendererVersion)) {
                return Result.failure(Exception("Incompatible renderer version: $rendererVersion"))
            }

            // 2. Check for duplicate IDs & Depth (Circular refs)
            val ids = mutableSetOf<String>()
            validateHierarchy(jsonObject, ids, 0)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun validateHierarchy(obj: JsonObject, ids: MutableSet<String>, depth: Int) {
        if (depth > MAX_DEPTH) throw Exception("Max nesting depth exceeded (Potential circular reference)")

        // Check ID
        val id = (obj["id"] as? JsonPrimitive)?.content
        if (id != null && id.isNotEmpty() && !ids.add(id)) throw Exception("Duplicate ID found: $id")

        // Check Sections
        (obj["sections"] as? JsonArray)?.forEach { section ->
            (section as? JsonObject)?.let { validateHierarchy(it, ids, depth + 1) }
        }

        // Check Components
        (obj["components"] as? JsonArray)?.forEach { component ->
            (component as? JsonObject)?.let { validateHierarchy(it, ids, depth + 1) }
        }

        // Check Children
        (obj["children"] as? JsonArray)?.forEach { child ->
            (child as? JsonObject)?.let { validateHierarchy(it, ids, depth + 1) }
        }
    }

    fun validateComponentJson(jsonString: String): Result<Unit> {
        return try {
            val element = json.parseToJsonElement(jsonString)
            val jsonObject = element as? JsonObject ?: return Result.failure(Exception("Not a JSON object"))
            
            // 1. Mandatory base fields
            val id = (jsonObject["id"] as? JsonPrimitive)?.content ?: return Result.failure(Exception("Missing 'id'"))
            val type = (jsonObject["type"] as? JsonPrimitive)?.content ?: return Result.failure(Exception("Missing 'type'"))
            
            // 2. Widget-specific property validation (from Catalog Doc 26)
            val props = jsonObject["properties"] as? JsonObject
            when (type.lowercase()) {
                "text" -> if (props?.containsKey("text") != true) return Result.failure(Exception("Text widget missing 'text' prop"))
                "image" -> if (props?.containsKey("url") != true) return Result.failure(Exception("Image widget missing 'url' prop"))
                "button" -> if (props?.containsKey("text") != true) return Result.failure(Exception("Button widget missing 'text' prop"))
                "icon" -> if (props?.containsKey("icon") != true) return Result.failure(Exception("Icon widget missing 'icon' prop"))
                "badge" -> if (props?.containsKey("text") != true) return Result.failure(Exception("Badge widget missing 'text' prop"))
                "hero_banner" -> {
                    if (props == null) return Result.failure(Exception("HeroBanner missing properties"))
                    if (!props.containsKey("imageUrl")) return Result.failure(Exception("HeroBanner missing 'imageUrl'"))
                    if (!props.containsKey("title")) return Result.failure(Exception("HeroBanner missing 'title'"))
                }
                "banner" -> {
                    if (props == null) return Result.failure(Exception("Banner missing properties"))
                    if (!props.containsKey("imageUrl")) return Result.failure(Exception("Banner missing 'imageUrl'"))
                }
                "car_card" -> {
                    if (props == null) return Result.failure(Exception("CarCard missing properties"))
                    if (!props.containsKey("title")) return Result.failure(Exception("CarCard missing 'title'"))
                    if (!props.containsKey("price")) return Result.failure(Exception("CarCard missing 'price'"))
                    if (!props.containsKey("imageUrl")) return Result.failure(Exception("CarCard missing 'imageUrl'"))
                }
                "search_bar" -> if (props?.containsKey("placeholder") != true) return Result.failure(Exception("SearchBar missing 'placeholder'"))
            }

            // 3. Style / Token validation
            (jsonObject["style"] as? JsonObject)?.let { validateTokens(it) }

            // 4. Action validation
            (jsonObject["actions"] as? JsonObject)?.let { actions ->
                actions.forEach { (key, action) ->
                    val actionObj = action as? JsonObject
                    if (actionObj != null && !actionObj.containsKey("type")) {
                        return Result.failure(Exception("Action '$key' missing 'type'"))
                    }
                }
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun validateTokens(style: JsonObject) {
        // Validate padding tokens if string
        (style["padding"] as? JsonObject)?.let { padding ->
            padding.values.forEach { 
                val token = (it as? JsonPrimitive)?.content
                if (token != null && !token.contains(".") && !isNumeric(token)) {
                    try { SpacingToken.valueOf(token.uppercase()) } catch (e: Exception) {
                        throw Exception("Invalid spacing token: $token")
                    }
                }
            }
        }
        // Validate colors
        (style["background"] as? JsonObject)?.let { bg ->
            (bg["color"] as? JsonPrimitive)?.content?.let { token ->
                try { ColorToken.valueOf(token.uppercase()) } catch (e: Exception) {
                    throw Exception("Invalid color token: $token")
                }
            }
        }
    }

    private fun isNumeric(toCheck: String): Boolean = toCheck.toDoubleOrNull() != null
}
