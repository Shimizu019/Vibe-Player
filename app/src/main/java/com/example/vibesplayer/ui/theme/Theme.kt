package com.example.vibesplayer.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

// Dark theme only; no dynamic color (spec section 4).
private val VibesColorScheme = darkColorScheme(
    primary = VibesColors.Gold,
    onPrimary = VibesColors.Espresso,
    secondary = VibesColors.Sand,
    onSecondary = VibesColors.Espresso,
    background = VibesColors.Espresso,
    onBackground = VibesColors.Sleeve,
    surface = VibesColors.Surface,
    onSurface = VibesColors.Sleeve,
    surfaceVariant = VibesColors.Surface,
    onSurfaceVariant = VibesColors.Sand,
    outline = VibesColors.Border,
    outlineVariant = VibesColors.Divider,
    error = VibesColors.Danger,
)

@Composable
fun VibesTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = VibesColorScheme,
        typography = VibesTypography,
        content = content,
    )
}
