package com.goldenhour.colorpicker.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = primary_app_color,
    primaryVariant = quadenary_app_color,
    secondary = secondary_app_color,
    secondaryVariant = transverb_orange,
    surface = tertiary_app_color,
    onPrimary = bottom_bar_app_color,
    onBackground = quinary_app_color,
    onSecondary = alert_blue,
    onSurface = alert_background,
)

private val LightColorPalette = lightColors(
    primary = primary_app_color,
    secondary = secondary_app_color,
    surface = tertiary_app_color,
    primaryVariant = quadenary_app_color,
    onPrimary = bottom_bar_app_color,
    onBackground = quinary_app_color,
    onSecondary = alert_blue,
    onSurface = alert_background,
)

@Composable
fun ColorPickerTheme(
    darkTheme: Boolean = true, //isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}