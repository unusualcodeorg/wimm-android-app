package com.whereismymotivation.ui.common.utils

import androidx.annotation.FloatRange
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp as lerpColor

/**
 * Linearly interpolate between two values
 */
fun lerp(
    startValue: Float,
    endValue: Float,
    @FloatRange(from = 0.0, to = 1.0) fraction: Float
): Float {
    return startValue + fraction * (endValue - startValue)
}

/**
 * Linearly interpolate between two [Float]s when the [fraction] is in a given range.
 */
fun lerp(
    startValue: Float,
    endValue: Float,
    @FloatRange(from = 0.0, to = 1.0) startFraction: Float,
    @FloatRange(from = 0.0, to = 1.0) endFraction: Float,
    @FloatRange(from = 0.0, to = 1.0) fraction: Float
): Float {
    if (fraction < startFraction) return startValue
    if (fraction > endFraction) return endValue

    return lerp(startValue, endValue, (fraction - startFraction) / (endFraction - startFraction))
}

/**
 * Linearly interpolate between two [Color]s when the [fraction] is in a given range.
 */
fun lerp(
    startColor: Color,
    endColor: Color,
    @FloatRange(from = 0.0, to = 1.0) startFraction: Float,
    @FloatRange(from = 0.0, to = 1.0) endFraction: Float,
    @FloatRange(from = 0.0, to = 1.0) fraction: Float
): Color {
    if (fraction < startFraction) return startColor
    if (fraction > endFraction) return endColor

    return lerpColor(
        startColor,
        endColor,
        (fraction - startFraction) / (endFraction - startFraction)
    )
}
