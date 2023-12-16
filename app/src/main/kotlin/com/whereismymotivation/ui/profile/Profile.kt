package com.whereismymotivation.ui.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Profile(modifier: Modifier, viewModel: ProfileViewModel) {
    ProfileView(modifier = modifier) { viewModel.logout() }
}

@Composable
private fun ProfileView(modifier: Modifier, logout: () -> Unit) {
    Box(modifier = modifier) {
        Button(onClick = logout) {
            Text(text = "Logout")
        }
    }
}

@Preview
@Composable
private fun ProfilePreview() {
    ProfileView(modifier = Modifier) {}
}