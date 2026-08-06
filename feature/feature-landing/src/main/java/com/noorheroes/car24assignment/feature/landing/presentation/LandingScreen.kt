package com.noorheroes.car24assignment.feature.landing.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
            Button(
                onClick = { viewModel.onOpenHomeClicked() },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
            ) {
                Text(text = "Open Home")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { viewModel.onOpenServerPanelClicked() },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
            ) {
                Text(text = "Open Server Panel")
            }
        }
    }
}
