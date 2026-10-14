package com.example.vibesplayer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vibesplayer.ui.theme.Dimens
import com.example.vibesplayer.ui.theme.VibesColors
import com.example.vibesplayer.ui.theme.VibesTheme
import com.example.vibesplayer.ui.theme.VibesType

/**
 * Era chip (spec section 8.5): 40 dp high, 24 dp horizontal padding, full radius,
 * bodyMed text. Selected is a Gold fill with Espresso text; unselected is a Surface
 * fill with a 1 dp Border and Sleeve text. Rows space chips 12 dp apart.
 */
@Composable
fun EraChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(Dimens.eraChipHeight)
            .clip(CircleShape)
            .background(if (selected) VibesColors.Gold else VibesColors.Surface)
            .then(
                if (selected) {
                    Modifier
                } else {
                    Modifier.border(1.dp, VibesColors.Border, CircleShape)
                },
            )
            .clickable { onClick() }
            .padding(horizontal = Dimens.eraChipHorizontalPadding),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            style = VibesType.bodyMed,
            color = if (selected) VibesColors.Espresso else VibesColors.Sleeve,
        )
    }
}

@Preview(name = "Era chips")
@Composable
private fun EraChipPreview() {
    VibesTheme {
        androidx.compose.foundation.layout.Row(
            modifier = Modifier.padding(Dimens.eraChipGap),
        ) {
            EraChip(label = "1950s", selected = false, onClick = {})
            androidx.compose.foundation.layout.Spacer(
                Modifier.padding(horizontal = Dimens.eraChipGap / 2),
            )
            EraChip(label = "1960s", selected = true, onClick = {})
        }
    }
}
