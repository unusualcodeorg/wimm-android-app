package com.whereismymotivation.ui.common.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * A [Modifier] which draws a vertical gradient
 */
fun Modifier.scrim(colors: List<Color>): Modifier = drawWithContent {
    drawContent()
    drawRect(Brush.verticalGradient(colors))
}
