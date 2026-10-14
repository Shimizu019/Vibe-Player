package com.example.vibesplayer.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vibesplayer.ui.theme.Dimens
import com.example.vibesplayer.ui.theme.VibesColors
import com.example.vibesplayer.ui.theme.VibesTheme
import com.example.vibesplayer.ui.theme.VibesType

/**
 * Segmented control (spec section 8.7): 48 dp high, Surface, 8 dp radius, 4 dp inner
 * padding, equal segments with rowTitle text. The selected segment is an Espresso fill
 * with 4 dp radius and Gold text; unselected is Sleeve. The selection slides in 150 ms.
 */
@Composable
fun SegmentedControl(
    options: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    require(options.size >= 2) { "SegmentedControl needs at least two options" }
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.segmentedControlHeight)
            .clip(RoundedCornerShape(Dimens.radiusMedium))
            .background(VibesColors.Surface)
            .padding(Dimens.segmentedControlInnerPadding),
    ) {
        // Widths are equal by construction; the indicator slides under the labels.
        val segmentWidth = maxWidth / options.size
        val offset by animateDpAsState(
            targetValue = segmentWidth * selectedIndex,
            animationSpec = tween(150),
            label = "segmentIndicator",
        )
        Box(
            modifier = Modifier
                .offset(x = offset)
                .width(segmentWidth)
                .fillMaxHeight()
                .clip(RoundedCornerShape(Dimens.radiusSmall))
                .background(VibesColors.Espresso),
        )
        Row(Modifier.fillMaxSize()) {
            options.forEachIndexed { index, label ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(
                            role = Role.Tab,
                            onClick = { onSelect(index) },
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = label,
                        style = VibesType.rowTitle,
                        color = if (index == selectedIndex) VibesColors.Gold else VibesColors.Sleeve,
                    )
                }
            }
        }
    }
}

@Preview(name = "Segmented control")
@Composable
private fun SegmentedControlPreview() {
    VibesTheme {
        androidx.compose.foundation.layout.Column {
            SegmentedControl(
                options = listOf("Playlists", "Downloads", "Liked"),
                selectedIndex = 0,
                onSelect = {},
            )
            androidx.compose.foundation.layout.Spacer(Modifier.height(8.dp))
            SegmentedControl(
                options = listOf("Playlists", "Downloads", "Liked"),
                selectedIndex = 2,
                onSelect = {},
            )
        }
    }
}
