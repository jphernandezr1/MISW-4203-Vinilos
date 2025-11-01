package com.example.vinyls.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Use the custom colors for your dark theme
private val DarkColorScheme = darkColorScheme(
    primary = PrimaryPurple,
    secondary = AccentColor,
    tertiary = Pink80,
    background = AppBackgroundColor,
    surface = CardBackgroundColor,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = TextColorPrimary,
    onSurface = TextColorPrimary
)

@Composable
fun VinylsTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme, // Use our new dark scheme
        typography = Typography,
        content = content
    )
}
