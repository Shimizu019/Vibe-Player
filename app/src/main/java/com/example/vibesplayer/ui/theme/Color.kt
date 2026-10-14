package com.example.vibesplayer.ui.theme

import androidx.compose.ui.graphics.Color

/** Design tokens from spec section 7.1. Screens must use these, never raw hex. */
object VibesColors {
    val Espresso = Color(0xFF1E1915)
    val Surface = Color(0xFF2A241E)
    val Border = Color(0xFF3A3229)
    val Divider = Color(0xFF2E2822)
    val Sleeve = Color(0xFFF1E6CF)
    val Sand = Color(0xFFD6C8AE)
    val Gold = Color(0xFFD4A73A)
    val Oxblood = Color(0xFF7A2E2E)
    val Disc = Color(0xFF0F0D0B)
    val Danger = Color(0xFFE57C6B)
    val Groove = Color(0xFF1E1B18)
}

/** Placeholder artwork palette (spec section 12): background paired with letter color. */
data class PlaceholderArtColors(val background: Color, val letter: Color)

val PlaceholderPalette = listOf(
    PlaceholderArtColors(Color(0xFF7A2E2E), Color(0xFFF1E6CF)),
    PlaceholderArtColors(Color(0xFF8C6D3F), Color(0xFFF1E6CF)),
    PlaceholderArtColors(Color(0xFF4A3B2A), Color(0xFFF1E6CF)),
    PlaceholderArtColors(Color(0xFFB89A6A), Color(0xFF1E1915)),
)
