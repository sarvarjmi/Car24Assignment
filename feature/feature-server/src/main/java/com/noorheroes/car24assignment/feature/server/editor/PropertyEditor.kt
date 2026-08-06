package com.noorheroes.car24assignment.feature.server.editor

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.noorheroes.car24assignment.feature.server.metadata.PropertyMetadata
import com.noorheroes.car24assignment.feature.server.metadata.PropertyType

@Composable
fun PropertyEditor(
    metadata: PropertyMetadata,
    value: Any?,
    onValueChange: (Any) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = metadata.label, style = MaterialTheme.typography.labelMedium)
        Spacer(modifier = Modifier.height(4.dp))
        
        when (metadata.type) {
            PropertyType.TEXT, PropertyType.IMAGE_URL -> {
                TextField(
                    value = value?.toString() ?: "",
                    onValueChange = onValueChange,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
            PropertyType.BOOLEAN -> {
                Switch(
                    checked = value as? Boolean ?: false,
                    onCheckedChange = onValueChange
                )
            }
            // Handle other types
            else -> {
                TextField(
                    value = value?.toString() ?: "",
                    onValueChange = onValueChange,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
