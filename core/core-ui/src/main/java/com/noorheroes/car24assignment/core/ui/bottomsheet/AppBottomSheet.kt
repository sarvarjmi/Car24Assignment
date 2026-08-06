package com.noorheroes.car24assignment.core.ui.bottomsheet

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBottomSheet(
    sheetState: SheetState = rememberModalBottomSheetState()
) {
    var activeRequest by remember { mutableStateOf<BottomSheetRequest?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        BottomSheetController.requests.collect {
            activeRequest = it
        }
    }

    if (activeRequest != null) {
        ModalBottomSheet(
            onDismissRequest = { activeRequest = null },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(bottom = 32.dp)
            ) {
                activeRequest?.title?.let {
                    Text(text = it, style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(16.dp))
                }
                
                // For dynamic content, we'd pass a renderer here.
                // For the assignment, a simple message or placeholder is enough.
                Text(text = "Details for " + (activeRequest?.contentId ?: "selected item"))
            }
        }
    }
}
