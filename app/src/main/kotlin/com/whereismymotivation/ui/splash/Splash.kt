package com.whereismymotivation.ui.splash

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier

@Composable
fun Splash(modifier: Modifier, viewModel: SplashViewModel, splashComplete: () -> Unit) {
    val close = viewModel.close.collectAsState()
    if (close.value) splashComplete()
    Text(modifier = modifier, text = "Splash", style = MaterialTheme.typography.headlineLarge)

}