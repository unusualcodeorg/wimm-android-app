package com.whereismymotivation.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Home(modifier: Modifier) {
    Box(modifier = modifier) {
        Text(text = "Home", style = MaterialTheme.typography.headlineLarge)
    }
}