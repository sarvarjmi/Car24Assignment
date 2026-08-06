package com.noorheroes.car24assignment.feature.landing.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LandingScreen(
    viewModel: LandingViewModel
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "CARS24",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Black,
                letterSpacing = 4.sp
            )
            Text(
                text = "SDUI ENGINE",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(bottom = 48.dp)
            )

            Button(
                onClick = { viewModel.onOpenHomeClicked() },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "Open Home", modifier = Modifier.padding(vertical = 8.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = { viewModel.onOpenServerPanelClicked() },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "Open Server Panel", modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}
