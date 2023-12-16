package com.whereismymotivation.ui.splash

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.whereismymotivation.R
import com.whereismymotivation.ui.common.LottieLoader

@Composable
fun Splash(modifier: Modifier, viewModel: SplashViewModel) {
    BackHandler { viewModel.navigator.finish() }
    SplashView(modifier) { viewModel.animationComplete() }
}

@Composable
fun SplashView(modifier: Modifier, onAnimationComplete: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieLoader(
            rawRes = R.raw.splash_loader,
            onComplete = onAnimationComplete
        )
    }
}

@Preview
@Composable
fun LoginPreview() {
    SplashView(modifier = Modifier) {}
}