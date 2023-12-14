package com.whereismymotivation.ui.search

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Search(modifier: Modifier) {
    Box(modifier = modifier) {
        Text(text = "Search", style = MaterialTheme.typography.headlineLarge)
    }
}