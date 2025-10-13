package com.thehubstudios.jordandrivingtheorytest.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.Color

// Light theme color scheme
private val LightColorScheme = lightColorScheme(
    primary = Orange,
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = OrangeLight,
    onPrimaryContainer = Color(0xFF000000),

    secondary = Yellow,
    onSecondary = Color(0xFF000000),
    secondaryContainer = YellowLight,
    onSecondaryContainer = Color(0xFF000000),

    tertiary = Blue,
    onTertiary = Color(0xFFFFFFFF),

    error = Red,
    onError = Color(0xFFFFFFFF),

    background = Color(0xFFFFFBFE),
    onBackground = Color(0xFF1C1B1F),

    surface = Color(0xFFFFFBFE),
    onSurface = Color(0xFF1C1B1F),
)

// Dark theme color scheme
private val DarkColorScheme = darkColorScheme(
    primary = OrangeDark,
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Orange,
    onPrimaryContainer = Color(0xFFFFFFFF),

    secondary = YellowDark,
    onSecondary = Color(0xFF000000),
    secondaryContainer = Yellow,
    onSecondaryContainer = Color(0xFF000000),

    tertiary = Blue,
    onTertiary = Color(0xFFFFFFFF),

    error = Red,
    onError = Color(0xFFFFFFFF),

    background = Color(0xFF1C1B1F),
    onBackground = Color(0xFFE6E1E5),

    surface = Color(0xFF1C1B1F),
    onSurface = Color(0xFFE6E1E5),
)

/**
 * Main theme for the Jordan Driving Theory Test app
 * Supports both light and dark mode
 */
@Composable
fun JordanDrivingTheoryTestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}