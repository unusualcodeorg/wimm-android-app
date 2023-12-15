package com.whereismymotivation.ui.splash

import androidx.activity.compose.BackHandler
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Splash(modifier: Modifier, viewModel: SplashViewModel) {
    Text(modifier = modifier, text = "Splash", style = MaterialTheme.typography.headlineLarge)
    BackHandler { viewModel.navigator.finish() }
}