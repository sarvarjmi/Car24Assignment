package com.noorheroes.car24assignment.feature.renderer.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.noorheroes.car24assignment.core.model.domain.Component
import com.noorheroes.car24assignment.feature.renderer.resolver.StyleResolver

@Composable
fun CategoriesWidget(component: Component.Categories) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(StyleResolver.resolveModifier(component.style))
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "Categories",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(component.items) { item ->
                CategoryItem(item.label)
            }
        }
    }
}

@Composable
fun CategoryItem(label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(Color.LightGray.copy(alpha = 0.5f), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = label.take(1).uppercase())
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, style = MaterialTheme.typography.labelMedium)
    }
}
