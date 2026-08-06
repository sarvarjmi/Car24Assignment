package com.noorheroes.car24assignment.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.noorheroes.car24assignment.core.ui.button.PrimaryButton
import com.noorheroes.car24assignment.core.ui.button.SecondaryButton

@Composable
fun EntryScreen(
    onNavigateToApp: () -> Unit,
    onNavigateToServer: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Cars24 SDUI Framework",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        PrimaryButton(
            text = "Landing Page",
            onClick = onNavigateToApp
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        SecondaryButton(
            text = "SDUI Server Page",
            onClick = onNavigateToServer
        )
    }
}
