package com.example.weatherapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF6200EE),
    secondary = Color.White,
    tertiary = Color(0xFFF2F2F2),
    background = Color(0xFF121212),         // Dark background
    onBackground = Color(0xFFE0E0E0),       // Light text on dark background
    surface = Color(0xFF1E1E1E),            // Surface color for cards etc.
    onSurface = Color(0xFFE0E0E0)            // Text on surface
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFBB86FC),
    secondary = Color.Black,
    tertiary = Color(0xFF121212),
    background = Color(0xFFFFFBFE),         // Light background
    onBackground = Color(0xFF1C1B1F),       // Dark text on light background
    surface = Color(0xFFFFFFFF),             // Surface color for cards etc.
    onSurface = Color(0xFF1C1B1F)            // Text on surface
)

@Composable
fun WeatherAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
