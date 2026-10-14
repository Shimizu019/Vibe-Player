package com.example.vibesplayer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vibesplayer.R
import com.example.vibesplayer.ui.theme.Dimens
import com.example.vibesplayer.ui.theme.VibesColors
import com.example.vibesplayer.ui.theme.VibesTheme
import com.example.vibesplayer.ui.theme.VibesType

/**
 * Empty state (spec section 8.11): centered column with a 48 dp Sand icon, a heading,
 * body text in Sand (max 280 dp wide), and an optional primary button (full radius,
 * 48 dp high, Gold fill, Espresso bodyMed text).
 */
@Composable
fun EmptyState(
    iconRes: Int,
    title: String,
    body: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier.widthIn(max = 280.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = VibesColors.Sand,
            modifier = Modifier.size(48.dp),
        )
        Spacer(Modifier.height(Dimens.headingToContent))
        Text(
            text = title,
            style = VibesType.heading,
            color = VibesColors.Sleeve,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = body,
            style = VibesType.body,
            color = VibesColors.Sand,
            textAlign = TextAlign.Center,
        )
        if (actionLabel != null && onAction != null) {
            Spacer(Modifier.height(Dimens.headingToContent))
            TextButton(
                onClick = onAction,
                modifier = Modifier
                    .height(Dimens.touchTarget)
                    .clip(CircleShape)
                    .background(VibesColors.Gold),
            ) {
                Text(
                    text = actionLabel,
                    style = VibesType.bodyMed,
                    color = VibesColors.Espresso,
                )
            }
        }
    }
}

@Preview(name = "Empty state with action")
@Composable
private fun EmptyStateActionPreview() {
    VibesTheme {
        EmptyState(
            iconRes = R.drawable.ic_music,
            title = stringResource(R.string.home_empty_title),
            body = stringResource(R.string.home_empty_body),
            actionLabel = stringResource(R.string.local_storage),
            onAction = {},
            modifier = Modifier.padding(24.dp),
        )
    }
}

@Preview(name = "Empty state without action")
@Composable
private fun EmptyStatePlainPreview() {
    VibesTheme {
        EmptyState(
            iconRes = R.drawable.ic_download,
            title = stringResource(R.string.downloads_empty_title),
            body = stringResource(R.string.downloads_empty_body),
            modifier = Modifier.padding(24.dp),
        )
    }
}
