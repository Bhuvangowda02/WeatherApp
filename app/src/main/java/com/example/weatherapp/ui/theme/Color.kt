package com.example.weatherapp.ui.theme

// Colors.kt
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val LightColors = lightColorScheme(
    primary = Color(0xFF6200EE),
    onPrimary = Color.White,
    background = Color(0xFFF2F2F2),
    onBackground = Color.Black,
    error = Color(0xFFB00020),
    // add other colors as needed
)

val DarkColors = darkColorScheme(
    primary = Color(0xFFBB86FC),
    onPrimary = Color.Black,
    background = Color(0xFF121212),
    onBackground = Color.White,
    error = Color(0xFFCF6679),
    // add other colors as needed
)
