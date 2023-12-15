package com.whereismymotivation.ui.login

import androidx.activity.compose.BackHandler
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Login(modifier: Modifier, viewModel: LoginViewModel) {
    Text(modifier = modifier, text = "Login", style = MaterialTheme.typography.headlineLarge)
    BackHandler { viewModel.navigator.finish() }
}