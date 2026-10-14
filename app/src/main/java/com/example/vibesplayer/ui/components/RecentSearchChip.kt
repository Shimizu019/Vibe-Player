package com.example.vibesplayer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vibesplayer.R
import com.example.vibesplayer.ui.theme.Dimens
import com.example.vibesplayer.ui.theme.VibesColors
import com.example.vibesplayer.ui.theme.VibesTheme
import com.example.vibesplayer.ui.theme.VibesType

/**
 * Recent search chip (spec section 8.6): 36 dp high, 4 dp radius, Surface fill, body
 * text in Sleeve with 16 dp horizontal padding, then a 16 dp Sand "x" icon 8 dp after
 * the text. Tapping the chip runs the search; tapping the x deletes it. Callers lay
 * chips out in a FlowRow with 8 dp gaps.
 */
@Composable
fun RecentSearchChip(
    query: String,
    onSearch: () -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .height(Dimens.recentSearchChipHeight)
            .clip(RoundedCornerShape(Dimens.radiusSmall))
            .background(VibesColors.Surface),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = query,
            style = VibesType.body,
            color = VibesColors.Sleeve,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .clickable { onSearch() }
                .padding(horizontal = Dimens.recentSearchChipHorizontalPadding),
        )
        Icon(
            painter = painterResource(R.drawable.ic_close),
            contentDescription = stringResource(R.string.cd_remove),
            tint = VibesColors.Sand,
            modifier = Modifier
                .size(16.dp)
                .clip(RoundedCornerShape(Dimens.radiusSmall))
                .clickable { onRemove() },
        )
        Box(Modifier.size(8.dp))
    }
}

@Preview(name = "Recent search chip")
@Composable
private fun RecentSearchChipPreview() {
    VibesTheme {
        RecentSearchChip(query = "queens of the stone age", onSearch = {}, onRemove = {})
    }
}
