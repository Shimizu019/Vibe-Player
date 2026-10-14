package com.example.vibesplayer.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType

/**
 * Filled circle-check (spec 7.6): one path, currentColor, with the check knocked out
 * via EvenOdd so it works on every API level (native Path fillType, no vector attribute).
 */
@Composable
fun CircleCheckFilled(
    color: Color,
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier) {
        val s = size.minDimension / 24f
        val path = Path().apply {
            fillType = PathFillType.EvenOdd
            addOval(Rect(2f * s, 2f * s, 22f * s, 22f * s))
            moveTo(8.2f * s, 12.3f * s)
            lineTo(10.6f * s, 14.7f * s)
            lineTo(15.8f * s, 9.5f * s)
            lineTo(14.4f * s, 8.1f * s)
            lineTo(10.6f * s, 11.9f * s)
            lineTo(9.6f * s, 10.9f * s)
            close()
        }
        drawPath(path = path, color = color)
    }
}
