package com.whereismymotivation.ui.mentors

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Mentors(modifier: Modifier) {
    Box(modifier = modifier) {
        Text(text = "Mentors", style = MaterialTheme.typography.headlineLarge)
    }
}