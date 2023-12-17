package com.whereismymotivation.ui.feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Feed(modifier: Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Text(text = "Feed", style = MaterialTheme.typography.headlineLarge)
    }
}