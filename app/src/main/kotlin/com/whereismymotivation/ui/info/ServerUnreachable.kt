package com.whereismymotivation.ui.info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whereismymotivation.R
import com.whereismymotivation.ui.common.image.LottieLoader
import com.whereismymotivation.ui.theme.AppTheme
import com.whereismymotivation.ui.theme.white

@Composable
fun ServerUnreachableInfo(modifier: Modifier, viewModel: InfoViewModel) {
    ServerUnreachableInfoView(
        modifier = modifier,
        retry = { viewModel.retry() }
    )
}

@Composable
fun ServerUnreachableInfoView(
    modifier: Modifier = Modifier,
    retry: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.white),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieLoader(
            modifier
                .fillMaxWidth()
                .fillMaxHeight(.75f),
            rawRes = R.raw.server_unreachable,
            forever = true
        )
        Button(
            modifier = modifier.width(154.dp),
            onClick = retry
        ) {
            Text(
                text = "Retry",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ServerUnreachableInfoPreview() {
    AppTheme {
        ServerUnreachableInfoView(retry = {})
    }
}