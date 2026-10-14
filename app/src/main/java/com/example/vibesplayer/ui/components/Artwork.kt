package com.example.vibesplayer.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.vibesplayer.ui.theme.PlaceholderPalette
import com.example.vibesplayer.ui.theme.VibesColors
import com.example.vibesplayer.ui.theme.VibesType
import kotlin.math.abs

/**
 * Single artwork entry point (spec section 12): a real image when [model] is set,
 * otherwise a palette square with the title's first letter in Fraunces SemiBold.
 */
@Composable
fun Artwork(
    model: Any?,
    title: String,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
    contentDescription: String? = null,
) {
    if (model != null) {
        AsyncImage(
            model = model,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = modifier.clip(shape),
        )
    } else {
        val colors = PlaceholderPalette[abs(title.hashCode()) % PlaceholderPalette.size]
        Box(
            modifier = modifier.clip(shape).background(colors.background),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = title.firstOrNull()?.uppercase() ?: "?",
                style = VibesType.titleM.copy(color = colors.letter),
                textAlign = TextAlign.Center,
            )
        }
    }
}

/** Hero card and Golden Hits cover: Oxblood with a simple vector record (spec section 12). */
@Composable
fun RecordArtwork(modifier: Modifier = Modifier) {
    Box(modifier = modifier.background(VibesColors.Oxblood)) {
        Canvas(Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val radius = minOf(size.width, size.height) * 0.36f
            val ring = Color(0xFFF1E6CF).copy(alpha = 0.12f)
            drawCircle(color = ring, radius = radius, center = center, style = Stroke(radius * 0.07f, cap = StrokeCap.Round))
            drawCircle(color = ring, radius = radius * 0.74f, center = center, style = Stroke(radius * 0.06f))
            drawCircle(color = ring, radius = radius * 0.5f, center = center, style = Stroke(radius * 0.06f))
            drawCircle(color = ring, radius = radius * 0.14f, center = center)
        }
    }
}
