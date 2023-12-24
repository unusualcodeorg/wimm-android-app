package com.whereismymotivation.ui.common.image

import androidx.annotation.RawRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LottieLoader(
    modifier: Modifier = Modifier,
    @RawRes rawRes: Int,
    forever: Boolean = false,
    onComplete: () -> Unit = {}
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(rawRes))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = if (forever) LottieConstants.IterateForever else 1
    )
    if (!forever && progress == 1.0f) onComplete()

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier,
    )
}