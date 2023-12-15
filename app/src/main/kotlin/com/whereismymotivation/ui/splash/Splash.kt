package com.whereismymotivation.ui.splash

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun Splash(splashComplete: () -> Unit) {
    Button(onClick = { splashComplete() }) {
        Text(text = "Close")
    }
}