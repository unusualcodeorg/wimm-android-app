package com.whereismymotivation.ui.mybox

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MyBox(modifier: Modifier) {
    Box(modifier = modifier) {
        Text(text = "MyBox")
    }
}