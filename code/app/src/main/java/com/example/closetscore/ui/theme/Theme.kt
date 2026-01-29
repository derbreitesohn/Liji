package com.example.closetscore.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(

    primary = LightGreen,
    onPrimary = Green,
    primaryContainer = Green,
    onPrimaryContainer = LightGreen,


    secondary = BrandRed,
    onSecondary = White,


    tertiary = BrandOrange,


    background = Black,
    surface = DarkestGrey,
    onBackground = Grey,
    onSurface = White,


    error = BrandRed
)

private val LightColorScheme = lightColorScheme(
    primary = LightGreen,
    onPrimary = White,
    primaryContainer = Grey,
    onPrimaryContainer = Green,


    secondary = BrandRed,
    onSecondary = White,


    tertiary = BrandOrange,
    onTertiary = White,


    background = White,
    surface = White,
    surfaceVariant = Grey,


    onBackground = Black,
    onSurface = DarkestGrey,
    onSurfaceVariant = DarkGrey,
)

@Composable
fun ClosetScoreTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
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