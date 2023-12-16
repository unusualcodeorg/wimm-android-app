package com.whereismymotivation.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

val md_theme_light_primary = Color(0xFF6451A5)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFE7DEFF)
val md_theme_light_onPrimaryContainer = Color(0xFF1F005F)
val md_theme_light_secondary = Color(0xFF615B71)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFE7DFF8)
val md_theme_light_onSecondaryContainer = Color(0xFF1D192B)
val md_theme_light_tertiary = Color(0xFF7D5262)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFFFD9E4)
val md_theme_light_onTertiaryContainer = Color(0xFF31111E)
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = Color(0xFFFFFBFF)
val md_theme_light_onBackground = Color(0xFF1C1B1E)
val md_theme_light_surface = Color(0xFFFFFBFF)
val md_theme_light_onSurface = Color(0xFF1C1B1E)
val md_theme_light_surfaceVariant = Color(0xFFE6E0EC)
val md_theme_light_onSurfaceVariant = Color(0xFF48454E)
val md_theme_light_outline = Color(0xFF79757F)
val md_theme_light_inverseOnSurface = Color(0xFFF4EFF4)
val md_theme_light_inverseSurface = Color(0xFF313033)
val md_theme_light_inversePrimary = Color(0xFFCDBDFF)
val md_theme_light_shadow = Color(0xFF000000)
val md_theme_light_surfaceTint = Color(0xFF6451A5)
val md_theme_light_outlineVariant = Color(0xFFCAC4CF)
val md_theme_light_scrim = Color(0xFF000000)

val md_theme_dark_primary = Color(0xFFCDBDFF)
val md_theme_dark_onPrimary = Color(0xFF351F74)
val md_theme_dark_primaryContainer = Color(0xFF4C388B)
val md_theme_dark_onPrimaryContainer = Color(0xFFE7DEFF)
val md_theme_dark_secondary = Color(0xFFCBC3DC)
val md_theme_dark_onSecondary = Color(0xFF322E41)
val md_theme_dark_secondaryContainer = Color(0xFF494458)
val md_theme_dark_onSecondaryContainer = Color(0xFFE7DFF8)
val md_theme_dark_tertiary = Color(0xFFEEB8CA)
val md_theme_dark_onTertiary = Color(0xFF492533)
val md_theme_dark_tertiaryContainer = Color(0xFF633B4A)
val md_theme_dark_onTertiaryContainer = Color(0xFFFFD9E4)
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val md_theme_dark_background = Color(0xFF1C1B1E)
val md_theme_dark_onBackground = Color(0xFFE6E1E6)
val md_theme_dark_surface = Color(0xFF1C1B1E)
val md_theme_dark_onSurface = Color(0xFFE6E1E6)
val md_theme_dark_surfaceVariant = Color(0xFF48454E)
val md_theme_dark_onSurfaceVariant = Color(0xFFCAC4CF)
val md_theme_dark_outline = Color(0xFF938F99)
val md_theme_dark_inverseOnSurface = Color(0xFF1C1B1E)
val md_theme_dark_inverseSurface = Color(0xFFE6E1E6)
val md_theme_dark_inversePrimary = Color(0xFF6451A5)
val md_theme_dark_shadow = Color(0xFF000000)
val md_theme_dark_surfaceTint = Color(0xFFCDBDFF)
val md_theme_dark_outlineVariant = Color(0xFF48454E)
val md_theme_dark_scrim = Color(0xFF000000)

val seed = Color(0xFF6451A5)

val ColorScheme.white: Color
    get() = Color(0xFFFFFFFF)

val ColorScheme.black: Color
    get() = Color(0xFF000000)

val ColorScheme.success: Color
    get() = Color(0xFF8DB333)

val ColorScheme.warning: Color
    get() = Color(0xFFFFA086)

val ColorScheme.info: Color
    get() = Color(0xFF72A2FF)

@Composable
fun ColorScheme.compositedOnSurface(alpha: Float): Color {
    return onSurface.copy(alpha = alpha).compositeOver(surface)
}