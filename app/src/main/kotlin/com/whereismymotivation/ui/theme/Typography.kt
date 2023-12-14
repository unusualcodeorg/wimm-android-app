package com.whereismymotivation.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Typography
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.whereismymotivation.R

private val fonts = FontFamily(
    Font(R.font.rubik_regular),
    Font(R.font.rubik_medium, FontWeight.W500),
    Font(R.font.rubik_bold, FontWeight.Bold)
)

val typography = typographyFromDefaults(
    displayLarge = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold
    ),
    displayMedium = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold
    ),
    displaySmall = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold
    ),
    headlineLarge = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        lineHeight = 48.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        lineHeight = 40.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold
    ),
    titleLarge = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.W500,
        lineHeight = 28.sp
    ),
    titleMedium = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.W500,
        lineHeight = 22.sp
    ),
    titleSmall = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.W500
    ),
    bodyLarge = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        lineHeight = 28.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        lineHeight = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold
    ),
    bodySmall = TextStyle(
        fontFamily = fonts
    ),
    labelSmall = TextStyle(
        letterSpacing = 0.08.em
    )
)

fun typographyFromDefaults(
    displayLarge: TextStyle?,
    displayMedium: TextStyle?,
    displaySmall: TextStyle?,
    headlineLarge: TextStyle?,
    headlineMedium: TextStyle?,
    headlineSmall: TextStyle?,
    titleLarge: TextStyle?,
    titleMedium: TextStyle?,
    titleSmall: TextStyle?,
    bodyLarge: TextStyle?,
    bodyMedium: TextStyle?,
    labelLarge: TextStyle?,
    bodySmall: TextStyle?,
    labelSmall: TextStyle?
): Typography {
    val defaults = Typography()
    return Typography(
        displayLarge = defaults.displayLarge.merge(displayLarge),
        displayMedium = defaults.displayMedium.merge(displayMedium),
        displaySmall = defaults.displaySmall.merge(displaySmall),
        headlineLarge = defaults.headlineMedium.merge(headlineLarge),
        headlineMedium = defaults.headlineMedium.merge(headlineMedium),
        headlineSmall = defaults.headlineSmall.merge(headlineSmall),
        titleLarge = defaults.titleLarge.merge(titleLarge),
        titleMedium = defaults.titleLarge.merge(titleMedium),
        titleSmall = defaults.titleSmall.merge(titleSmall),
        bodyLarge = defaults.bodyLarge.merge(bodyLarge),
        bodyMedium = defaults.bodyMedium.merge(bodyMedium),
        labelLarge = defaults.labelLarge.merge(labelLarge),
        bodySmall = defaults.bodySmall.merge(bodySmall),
        labelSmall = defaults.labelSmall.merge(labelSmall)
    )
}
