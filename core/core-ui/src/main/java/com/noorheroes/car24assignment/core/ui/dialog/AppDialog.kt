package com.noorheroes.car24assignment.core.ui.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*

@Composable
fun AppDialog() {
    var activeRequest by remember { mutableStateOf<DialogRequest?>(null) }

    LaunchedEffect(Unit) {
        DialogController.requests.collect {
            activeRequest = it
        }
    }

    activeRequest?.let { request ->
        AlertDialog(
            onDismissRequest = { 
                request.onDismiss()
                activeRequest = null 
            },
            title = { Text(text = request.title) },
            text = { Text(text = request.message) },
            confirmButton = {
                TextButton(onClick = { 
                    request.onConfirm()
                    activeRequest = null 
                }) {
                    Text(request.confirmLabel)
                }
            },
            dismissButton = request.dismissLabel?.let { label ->
                {
                    TextButton(onClick = { 
                        request.onDismiss()
                        activeRequest = null 
                    }) {
                        Text(label)
                    }
                }
            }
        )
    }
}
