package com.noorheroes.car24assignment.core.common.extensions

import kotlinx.serialization.json.*

fun JsonElement.toMapValue(): Any? = when (this) {
    is JsonPrimitive -> {
        if (isString) content
        else if (content == "true" || content == "false") content.toBoolean()
        else content.toDoubleOrNull() ?: content.toLongOrNull() ?: content
    }
    is JsonObject -> this.entries.associate { it.key to it.value.toMapValue() }
    is JsonArray -> this.map { it.toMapValue() }
    JsonNull -> null
}

fun JsonObject.toMap(): Map<String, Any?> = this.entries.associate { it.key to it.value.toMapValue() }
