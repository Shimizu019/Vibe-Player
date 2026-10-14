package com.example.vibesplayer.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.vibesplayer.R
import com.example.vibesplayer.ui.theme.Dimens
import com.example.vibesplayer.ui.theme.VibesColors
import com.example.vibesplayer.ui.theme.VibesTheme

/** Download states shared by the collection DownloadButton and the SongRow search slot. */
enum class DownloadState { Idle, Downloading, Downloaded }

/**
 * Gold progress ring (spec sections 8.1 and 8.8): a clockwise arc from 12 o'clock with
 * no track and no rotation. Used both at 24 dp in SongRow and around the 52 dp
 * collection DownloadButton.
 */
@Composable
fun DownloadRing(
    progress: Float,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 2.dp,
) {
    val animated by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(250),
        label = "downloadRing",
    )
    Canvas(modifier = modifier) {
        val stroke = strokeWidth.toPx()
        val inset = stroke / 2f
        drawArc(
            color = VibesColors.Gold,
            startAngle = -90f,
            sweepAngle = animated * 360f,
            useCenter = false,
            topLeft = Offset(inset, inset),
            size = Size(size.width - stroke, size.height - stroke),
            style = Stroke(width = stroke, cap = StrokeCap.Round),
        )
    }
}

/**
 * Collection download button (spec section 8.8): 52 dp Surface circle with a 24 dp
 * icon. Not downloaded shows arrow-down in Sleeve; downloading shows a Gold ring around
 * the circle with a square icon; all downloaded shows a Gold circle-check.
 */
@Composable
fun DownloadButton(
    state: DownloadState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    downloadProgress: Float = 0f,
) {
    val percent = (downloadProgress * 100).toInt()
    val contentDescription = when (state) {
        DownloadState.Idle -> stringResource(R.string.cd_download_collection)
        DownloadState.Downloading -> pluralStringResource(
            R.plurals.state_downloading_percent,
            percent,
            percent,
        )
        DownloadState.Downloaded -> stringResource(R.string.state_downloaded)
    }
    Box(
        modifier = modifier
            .size(Dimens.iconButton)
            .clip(CircleShape)
            .background(VibesColors.Surface)
            .clickable { onClick() }
            .semantics { this.contentDescription = contentDescription },
        contentAlignment = Alignment.Center,
    ) {
        when (state) {
            DownloadState.Idle -> Icon(
                painter = painterResource(R.drawable.ic_arrow_down),
                contentDescription = null,
                tint = VibesColors.Sleeve,
                modifier = Modifier.size(Dimens.downloadRingSize),
            )
            DownloadState.Downloading -> {
                DownloadRing(
                    progress = downloadProgress,
                    modifier = Modifier.size(Dimens.iconButton),
                    strokeWidth = 2.dp,
                )
                Icon(
                    painter = painterResource(R.drawable.ic_square),
                    contentDescription = null,
                    tint = VibesColors.Sleeve,
                    modifier = Modifier.size(Dimens.downloadRingSize),
                )
            }
            DownloadState.Downloaded -> CircleCheckFilled(
                color = VibesColors.Gold,
                modifier = Modifier.size(Dimens.downloadRingSize),
            )
        }
    }
}

@Preview(name = "Download button states")
@Composable
private fun DownloadButtonPreview() {
    VibesTheme {
        androidx.compose.foundation.layout.Row {
            DownloadButton(state = DownloadState.Idle, onClick = {})
            androidx.compose.foundation.layout.Spacer(Modifier.size(8.dp))
            DownloadButton(state = DownloadState.Downloading, downloadProgress = 0.6f, onClick = {})
            androidx.compose.foundation.layout.Spacer(Modifier.size(8.dp))
            DownloadButton(state = DownloadState.Downloaded, onClick = {})
        }
    }
}
