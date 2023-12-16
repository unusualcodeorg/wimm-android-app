package com.whereismymotivation.ui.common.progress

import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Loading(
    modifier: Modifier = Modifier,
    loader: Loader
) {
    val loading = loader.loading.collectAsState().value
    LoadingView(modifier, loading)
}

@Composable
private fun LoadingView(
    modifier: Modifier = Modifier,
    loading: Boolean
) {
    if (!loading) return
    LinearProgressIndicator(
        modifier = modifier,
        color = MaterialTheme.colorScheme.tertiary,
        trackColor = MaterialTheme.colorScheme.onPrimary,
    )
}

@Preview
@Composable
private fun LoadingPreview() {
    LoadingView(loading = true)
}