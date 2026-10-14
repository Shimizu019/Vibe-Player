package com.example.vibesplayer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vibesplayer.ui.theme.Dimens
import com.example.vibesplayer.ui.theme.VibesColors
import com.example.vibesplayer.ui.theme.VibesTheme

/**
 * Skeleton block (spec section 8.12): a static Surface block that matches the real
 * layout. No shimmer - loading state uses the same shape as the loaded content.
 */
@Composable
fun SkeletonBlock(
    width: androidx.compose.ui.unit.Dp,
    height: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier,
    radius: androidx.compose.ui.unit.Dp = Dimens.radiusSmall,
) {
    androidx.compose.foundation.layout.Box(
        modifier = modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(radius))
            .background(VibesColors.Surface),
    )
}

/** Skeleton row matching SongRow: 48 dp cover, 16 dp gap, two text lines (section 8.12). */
@Composable
fun SkeletonRow(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.rowHeightCompact)
            .padding(vertical = Dimens.rowPaddingCompact),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
    ) {
        SkeletonBlock(
            width = Dimens.rowCover,
            height = Dimens.rowCover,
            modifier = Modifier.size(Dimens.rowCover),
        )
        Spacer(Modifier.width(Dimens.rowCoverGap))
        Column {
            SkeletonBlock(width = 180.dp, height = 14.dp)
            Spacer(Modifier.height(8.dp))
            SkeletonBlock(width = 120.dp, height = 12.dp)
        }
    }
}

/** Five skeleton rows, the Home loading state (spec section 10.1). */
@Composable
fun SkeletonRows(modifier: Modifier = Modifier, count: Int = 5) {
    Column(modifier = modifier.fillMaxWidth()) {
        repeat(count) { SkeletonRow() }
    }
}

@Preview(name = "Skeleton rows")
@Composable
private fun SkeletonRowsPreview() {
    VibesTheme {
        SkeletonRows(count = 3, modifier = Modifier.padding(horizontal = 24.dp))
    }
}
