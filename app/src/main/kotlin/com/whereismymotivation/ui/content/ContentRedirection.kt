package com.whereismymotivation.ui.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.whereismymotivation.ui.common.progress.LoadingPlaceholder

@Composable
fun ContentRedirection(modifier: Modifier, viewModel: ContentViewModel) {
    LaunchedEffect(key1 = Unit) {
        viewModel.checkRedirection()
    }
    ContentRedirectionView(modifier)
}

@Composable
private fun ContentRedirectionView(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        LoadingPlaceholder(loading = true)
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginPreview() {
    ContentRedirectionView(modifier = Modifier)
}