package com.whereismymotivation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import com.whereismymotivation.R

private val fonts = FontFamily(
    Font(R.font.rubik_regular),
    Font(R.font.rubik_medium, FontWeight.W500),
    Font(R.font.rubik_bold, FontWeight.Bold)
)

val Typography.quoteSail: TextStyle
    get() = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.sail_regular),
        )
    )

val Typography.quoteAmaranth: TextStyle
    get() = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.amaranth_regular),
        )
    )

val Typography.quotePayToneOne: TextStyle
    get() = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.paytone_one_regular),
        )
    )

val Typography.quoteBerkshire: TextStyle
    get() = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.berkshire_swash_regular),
        )
    )

val Typography.quoteOleoScript: TextStyle
    get() = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.oleo_script_regular),
        )
    )

val Typography.quoteOswald: TextStyle
    get() = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.oswald_regular),
        )
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
    ),
    headlineMedium = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
    ),
    headlineSmall = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold
    ),
    titleLarge = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.W500,
    ),
    titleMedium = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.W500,
    ),
    titleSmall = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.W500
    ),
    bodyLarge = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
    ),
    bodyMedium = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
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
