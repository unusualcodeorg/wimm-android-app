package com.whereismymotivation.ui.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Search(modifier: Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Text(text = "Search", style = MaterialTheme.typography.headlineLarge)
    }
}