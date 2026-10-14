package com.example.vibesplayer.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.vibesplayer.R
import com.example.vibesplayer.ui.theme.Dimens
import com.example.vibesplayer.ui.theme.VibesColors
import com.example.vibesplayer.ui.theme.VibesTheme
import com.example.vibesplayer.ui.theme.VibesType

/** Leading badge drawn inside the subline (spec section 8.1). */
enum class SongRowBadge { None, Downloaded, OnThisPhone }

/**
 * Contents of the trailing 48 dp slot. [More] is the default "..." button; the other
 * values are the search variant's download states (spec section 8.1). Songs that live
 * on this phone always show [More] in search.
 */
enum class SongRowTrailing {
    More,
    DownloadIdle,
    DownloadQueued,
    Downloading,
    Downloaded,
    DownloadFailed,
    DownloadNotAllowed,
}

/**
 * Song row (spec section 8.1): cover 48 dp, 16 dp gap, text column (rowTitle over
 * meta), trailing 48 dp button. Tap plays the song in its list; long-press opens the
 * song menu. Playing turns the title Gold and swaps the trailing "..." for an animated
 * 3-bar equalizer. [onlineOnly] dims the whole row to 50% and switches the subline.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SongRow(
    title: String,
    artist: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    year: Int? = null,
    artworkModel: Any? = null,
    isPlaying: Boolean = false,
    badge: SongRowBadge = SongRowBadge.None,
    onlineOnly: Boolean = false,
    trailing: SongRowTrailing = SongRowTrailing.More,
    downloadProgress: Float = 0f,
    onLongClick: () -> Unit = {},
    onTrailingClick: () -> Unit = {},
    height: Dp = Dimens.rowHeightCompact,
    contentPadding: Dp = Dimens.rowPaddingCompact,
) {
    val subline = when {
        onlineOnly -> stringResource(R.string.song_meta_online_only, artist)
        year != null -> stringResource(R.string.song_meta_year, artist, year)
        else -> artist
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .then(if (onlineOnly) Modifier.alpha(0.5f) else Modifier)
            .combinedClickable(onClick = onClick, onLongClick = onLongClick)
            .padding(vertical = contentPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Artwork(
            model = artworkModel,
            title = title,
            modifier = Modifier.size(Dimens.rowCover),
            contentDescription = stringResource(R.string.artwork_for_song, title),
        )
        Spacer(Modifier.width(Dimens.rowCoverGap))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = VibesType.rowTitle,
                color = if (isPlaying) VibesColors.Gold else VibesColors.Sleeve,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Subline(
                subline = subline,
                badge = if (onlineOnly) SongRowBadge.None else badge,
                onlineOnly = onlineOnly,
            )
        }
        Spacer(Modifier.width(Dimens.rowCoverGap))
        TrailingSlot(
            isPlaying = isPlaying,
            onlineOnly = onlineOnly,
            trailing = trailing,
            downloadProgress = downloadProgress,
            title = title,
            onClick = onTrailingClick,
        )
    }
}

/**
 * Subline (spec section 8.1): meta text in Sand with an optional leading badge -
 * a 16 dp Gold circle-check when downloaded, a 14 dp Sand smartphone when the song is
 * on this phone, or a cloud-off icon before "Artist • Online only".
 */
@Composable
private fun Subline(
    subline: String,
    badge: SongRowBadge,
    onlineOnly: Boolean,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        when {
            onlineOnly -> Icon(
                painter = painterResource(R.drawable.ic_cloud_off),
                contentDescription = null,
                tint = VibesColors.Sand,
                modifier = Modifier.size(16.dp),
            )
            badge == SongRowBadge.Downloaded -> CircleCheckFilled(
                color = VibesColors.Gold,
                modifier = Modifier.size(16.dp),
            )
            badge == SongRowBadge.OnThisPhone -> Icon(
                painter = painterResource(R.drawable.ic_phone),
                contentDescription = null,
                tint = VibesColors.Sand,
                modifier = Modifier.size(14.dp),
            )
        }
        if (badge != SongRowBadge.None || onlineOnly) {
            Spacer(Modifier.width(6.dp))
        }
        Text(
            text = subline,
            style = VibesType.meta,
            color = VibesColors.Sand,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

/**
 * Trailing 48 dp slot (spec section 8.1): "ellipsis-vertical" by default; the animated
 * Gold equalizer while playing; the search download states otherwise. Songs on this
 * phone keep [SongRowTrailing.More] even in search.
 */
@Composable
private fun TrailingSlot(
    isPlaying: Boolean,
    onlineOnly: Boolean,
    trailing: SongRowTrailing,
    downloadProgress: Float,
    title: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(Dimens.touchTarget)
            .clip(androidx.compose.foundation.shape.CircleShape)
            // The equalizer and online-only rows pass taps through to the row itself;
            // the "..." and download icons take their own action (spec section 8.1).
            .then(
                if (onlineOnly || isPlaying) {
                    Modifier
                } else {
                    Modifier.clickable { onClick() }
                },
            ),
        contentAlignment = Alignment.Center,
    ) {
        when {
            isPlaying -> EqualizerBars()
            onlineOnly -> Icon(
                painter = painterResource(R.drawable.ic_ellipsis_vertical),
                contentDescription = stringResource(R.string.cd_more_options_for, title),
                tint = VibesColors.Sleeve,
                modifier = Modifier.size(Dimens.tabIconSize),
            )
            trailing == SongRowTrailing.More -> Icon(
                painter = painterResource(R.drawable.ic_ellipsis_vertical),
                contentDescription = stringResource(R.string.cd_more_options_for, title),
                tint = VibesColors.Sleeve,
                modifier = Modifier.size(Dimens.tabIconSize),
            )
            else -> DownloadTrailingIcon(
                trailing = trailing,
                downloadProgress = downloadProgress,
                title = title,
                onClick = onClick,
            )
        }
    }
}


/**
 * Search-variant trailing icon (spec section 8.1): idle = download, queued = clock,
 * downloading = 24 dp Gold ring (tap cancels), downloaded = Gold circle-check (tap
 * opens the menu), failed = rotate-ccw (tap retries), not allowed = download at 38%
 * opacity (tap shows the section 17 message).
 */
@Composable
private fun DownloadTrailingIcon(
    trailing: SongRowTrailing,
    downloadProgress: Float,
    title: String,
    onClick: () -> Unit,
) {
    when (trailing) {
        SongRowTrailing.DownloadIdle -> Icon(
            painter = painterResource(R.drawable.ic_download),
            contentDescription = stringResource(R.string.cd_download_song, title),
            tint = VibesColors.Sleeve,
            modifier = Modifier.size(Dimens.tabIconSize),
        )
        SongRowTrailing.DownloadQueued -> Icon(
            painter = painterResource(R.drawable.ic_clock),
            contentDescription = stringResource(R.string.state_queued),
            tint = VibesColors.Sleeve,
            modifier = Modifier.size(Dimens.tabIconSize),
        )
        SongRowTrailing.Downloading -> {
            // Tap anywhere on the ring cancels the download (spec section 8.1).
            DownloadRing(
                progress = downloadProgress,
                modifier = Modifier
                    .size(Dimens.downloadRingSize)
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .clickable { onClick() },
            )
        }
        SongRowTrailing.Downloaded -> CircleCheckFilled(
            color = VibesColors.Gold,
            modifier = Modifier.size(Dimens.tabIconSize),
        )
        SongRowTrailing.DownloadFailed -> Icon(
            painter = painterResource(R.drawable.ic_retry),
            contentDescription = stringResource(R.string.state_download_failed),
            tint = VibesColors.Danger,
            modifier = Modifier.size(Dimens.tabIconSize),
        )
        SongRowTrailing.DownloadNotAllowed -> Icon(
            painter = painterResource(R.drawable.ic_download),
            contentDescription = stringResource(R.string.download_not_allowed),
            tint = VibesColors.Sleeve,
            modifier = Modifier
                .size(Dimens.tabIconSize)
                .alpha(0.38f),
        )
        SongRowTrailing.More -> error("More is rendered by the caller, not this state")
    }
}


/**
 * Animated 3-bar equalizer in Gold (spec section 8.1, audio-lines style). Bars move
 * only while the audio plays - previews and inspection render static bars, and a
 * system animation scale of 0 disables the transition through Compose defaults.
 */
@Composable
private fun EqualizerBars(modifier: Modifier = Modifier) {
    val inspection = LocalInspectionMode.current
    val transition = rememberInfiniteTransition(label = "equalizer")
    val phase by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(560, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "equalizerPhase",
    )
    val levels = if (inspection) listOf(0.6f, 1f, 0.4f) else listOf(
        0.35f + 0.65f * phase,
        1f - 0.55f * phase,
        0.5f + 0.5f * phase,
    )
    Canvas(modifier = modifier.size(Dimens.tabIconSize)) {
        val barWidth = 4.dp.toPx()
        val gap = 3.dp.toPx()
        val totalWidth = barWidth * 3 + gap * 2
        val startX = (size.width - totalWidth) / 2f
        val maxBarHeight = size.height
        levels.forEachIndexed { index, level ->
            val barHeight = maxBarHeight * level
            // Rounded caps come from the corner radius; bars are 4 dp wide with 3 dp gaps.
            drawRoundRect(
                color = VibesColors.Gold,
                topLeft = Offset(
                    x = startX + index * (barWidth + gap),
                    y = (maxBarHeight - barHeight) / 2f,
                ),
                size = androidx.compose.ui.geometry.Size(barWidth, barHeight),
                cornerRadius = CornerRadius(barWidth / 2f),
            )
        }
    }
}


@Preview(name = "SongRow default")
@Composable
private fun SongRowDefaultPreview() {
    VibesTheme {
        SongRow(
            title = "Sweet Home Alabama",
            artist = "Lynyrd Skynyrd",
            year = 1974,
            onClick = {},
            modifier = Modifier.padding(horizontal = 24.dp),
        )
    }
}

@Preview(name = "SongRow playing")
@Composable
private fun SongRowPlayingPreview() {
    VibesTheme {
        SongRow(
            title = "Bohemian Rhapsody",
            artist = "Queen",
            year = 1975,
            isPlaying = true,
            onClick = {},
            modifier = Modifier.padding(horizontal = 24.dp),
        )
    }
}

@Preview(name = "SongRow downloaded")
@Composable
private fun SongRowDownloadedPreview() {
    VibesTheme {
        SongRow(
            title = "Billie Jean",
            artist = "Michael Jackson",
            year = 1982,
            badge = SongRowBadge.Downloaded,
            onClick = {},
            modifier = Modifier.padding(horizontal = 24.dp),
        )
    }
}

@Preview(name = "SongRow on this phone")
@Composable
private fun SongRowOnPhonePreview() {
    VibesTheme {
        SongRow(
            title = "Free Bird",
            artist = "Lynyrd Skynyrd",
            year = 1973,
            badge = SongRowBadge.OnThisPhone,
            onClick = {},
            modifier = Modifier.padding(horizontal = 24.dp),
        )
    }
}

@Preview(name = "SongRow online only")
@Composable
private fun SongRowOnlineOnlyPreview() {
    VibesTheme {
        SongRow(
            title = "Midnight City",
            artist = "M83",
            year = 2011,
            onlineOnly = true,
            onClick = {},
            modifier = Modifier.padding(horizontal = 24.dp),
        )
    }
}


@Preview(name = "SongRow search download states")
@Composable
private fun SongRowSearchStatesPreview() {
    VibesTheme {
        Column {
            listOf(
                SongRowTrailing.DownloadIdle,
                SongRowTrailing.DownloadQueued,
                SongRowTrailing.Downloading,
                SongRowTrailing.Downloaded,
                SongRowTrailing.DownloadFailed,
                SongRowTrailing.DownloadNotAllowed,
            ).forEach { state ->
                SongRow(
                    title = "Take On Me",
                    artist = "a-ha",
                    year = 1985,
                    trailing = state,
                    downloadProgress = 0.6f,
                    onClick = {},
                    onTrailingClick = {},
                    modifier = Modifier.padding(horizontal = 24.dp),
                )
            }
        }
    }
}

