package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val MayaDarkColorScheme = darkColorScheme(
    primary = MayaViolet,
    onPrimary = MayaTextWhite,
    primaryContainer = MayaVioletDark,
    onPrimaryContainer = MayaVioletLight,
    secondary = MayaCyan,
    onSecondary = MayaBgDark,
    secondaryContainer = Color(0xFF0E3A4B),
    onSecondaryContainer = MayaCyanLight,
    tertiary = MayaRose,
    onTertiary = MayaTextWhite,
    background = MayaBgDark,
    onBackground = MayaTextWhite,
    surface = MayaBgElevated,
    onSurface = MayaTextWhite,
    surfaceVariant = MayaCardBg,
    onSurfaceVariant = MayaTextSecondary,
    outline = MayaCardBorder,
    outlineVariant = MayaCardBorderGlow
)

private val MayaLightColorScheme = lightColorScheme(
    primary = MayaViolet,
    onPrimary = MayaTextWhite,
    primaryContainer = MayaVioletLight.copy(alpha = 0.3f),
    onPrimaryContainer = MayaVioletDark,
    secondary = MayaCyan,
    onSecondary = MayaTextWhite,
    background = MayaBgLight,
    onBackground = MayaTextDark,
    surface = MayaSurfaceLight,
    onSurface = MayaTextDark,
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = MayaTextMutedLight,
    outline = MayaCardBorderLight
)

@Composable
fun MayaAITheme(
    darkTheme: Boolean = true, // Maya defaults to sleek cyberpunk obsidian dark theme
    accentHex: Long = 0xFF7C3AED,
    content: @Composable () -> Unit
) {
    val accentColor = Color(accentHex)
    val baseScheme = if (darkTheme) MayaDarkColorScheme else MayaLightColorScheme
    val colorScheme = baseScheme.copy(
        primary = accentColor,
        outlineVariant = accentColor.copy(alpha = 0.6f)
    )
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = colorScheme.background.toArgb()
                window.navigationBarColor = colorScheme.background.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

// Keep backwards-compatibility alias
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) = MayaAITheme(darkTheme = darkTheme, content = content)

