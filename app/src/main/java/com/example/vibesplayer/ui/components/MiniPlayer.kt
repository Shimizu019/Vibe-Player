package com.example.vibesplayer.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vibesplayer.R
import com.example.vibesplayer.ui.theme.Dimens
import com.example.vibesplayer.ui.theme.VibesColors
import com.example.vibesplayer.ui.theme.VibesTheme
import com.example.vibesplayer.ui.theme.VibesType

/**
 * The song the mini player shows. Kept standalone so the component can preview
 * without the data layer, which arrives in milestone 2.
 */
data class MiniPlayerSong(
    val id: String,
    val title: String,
    val artist: String,
    val artworkModel: Any? = null,
    val durationMs: Long = 0L,
    val positionMs: Long = 0L,
)

/**
 * Mini player (spec section 8.2): a Surface card 8 dp above the tab bar carrying the
 * cover, title, artist, previous, play/pause and next, with a 3 dp Gold progress line
 * clipped to the card's bottom edge. Width is the screen width minus 32 dp. Tapping the
 * card (not the buttons) opens Now Playing; press feedback is ripple only (section 7.7).
 */
@Composable
fun MiniPlayer(
    song: MiniPlayerSong,
    isPlaying: Boolean,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onOpen: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val fraction = if (song.durationMs > 0L) {
        (song.positionMs.toFloat() / song.durationMs.toFloat()).coerceIn(0f, 1f)
    } else {
        0f
    }
    val progress by animateFloatAsState(
        targetValue = fraction,
        animationSpec = tween(250),
        label = "miniPlayerProgress",
    )
    val accessibilityLabel =
        stringResource(R.string.mini_player_a11y, song.title, song.artist)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.miniPlayerInset),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.miniPlayerHeight)
                .clip(RoundedCornerShape(Dimens.radiusMedium))
                .background(VibesColors.Surface)
                .border(1.dp, VibesColors.Border, RoundedCornerShape(Dimens.radiusMedium))
                .clickable { onOpen() }
                .semantics { this.contentDescription = accessibilityLabel },
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = Dimens.miniPlayerStartPadding,
                        end = Dimens.miniPlayerEndPadding,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Artwork(
                    model = song.artworkModel,
                    title = song.title,
                    modifier = Modifier.size(Dimens.miniPlayerCover),
                    contentDescription = stringResource(R.string.artwork_for_song, song.title),
                )
                Spacer(Modifier.width(Dimens.miniPlayerCoverTextGap))
                // Takes the remaining width and ellipsizes first so all three transport
                // buttons stay visible at a 320 dp screen width (spec section 8.2).
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = song.title,
                        style = VibesType.rowTitle.copy(color = VibesColors.Sleeve),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = song.artist,
                        style = VibesType.meta.copy(color = VibesColors.Sand),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Spacer(Modifier.width(Dimens.miniPlayerTextTransportGap))
                MiniTransportButton(
                    iconRes = R.drawable.ic_skip_back,
                    contentDescription = stringResource(R.string.cd_skip_previous),
                    onClick = onPrevious,
                )
                MiniTransportButton(
                    iconRes = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play,
                    contentDescription = stringResource(
                        if (isPlaying) R.string.cd_pause else R.string.cd_play,
                    ),
                    onClick = onPlayPause,
                    filled = true,
                )
                MiniTransportButton(
                    iconRes = R.drawable.ic_skip_forward,
                    contentDescription = stringResource(R.string.cd_skip_next),
                    onClick = onNext,
                )
            }
            MiniPlayerProgressLine(
                fraction = progress,
                modifier = Modifier.align(Alignment.BottomStart),
            )
        }
    }
}


/**
 * A circular transport button: 48 dp target with a 24 dp Lucide icon. [filled] gives the
 * play/pause button its 44 dp Sleeve circle with an Espresso icon (spec section 8.2).
 */
@Composable
private fun MiniTransportButton(
    iconRes: Int,
    contentDescription: String,
    onClick: () -> Unit,
    filled: Boolean = false,
) {
    Box(
        modifier = Modifier
            .size(Dimens.touchTarget)
            .clip(CircleShape)
            .clickable { onClick() }
            .semantics { this.contentDescription = contentDescription },
        contentAlignment = Alignment.Center,
    ) {
        // The Sleeve circle is a child so the 48 dp touch target stays intact while the
        // visible play/pause circle is 44 dp (spec section 8.2).
        Box(
            modifier = Modifier
                .size(if (filled) Dimens.miniPlayerPlayPause else Dimens.touchTarget)
                .clip(CircleShape)
                .then(if (filled) Modifier.background(VibesColors.Sleeve) else Modifier),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = if (filled) VibesColors.Espresso else VibesColors.Sleeve,
                modifier = Modifier.size(Dimens.miniPlayerTransportIcon),
            )
        }
    }
}

/**
 * 3 dp Gold progress line on the card's bottom edge. The unfilled part draws in the card
 * color, so the line reads as "no track" (spec section 8.2). Clipped to the 8 dp radius
 * by the card's own shape clip.
 */
@Composable
private fun MiniPlayerProgressLine(
    fraction: Float,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.miniPlayerProgressHeight)
            .background(VibesColors.Surface),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(fraction)
                .fillMaxHeight()
                .background(VibesColors.Gold),
        )
    }
}

@Preview(name = "Paused")
@Composable
private fun MiniPlayerPausedPreview() {
    VibesTheme {
        MiniPlayer(
            song = MiniPlayerSong(
                id = "1",
                title = "Take On Me",
                artist = "a-ha",
                durationMs = 225_000,
                positionMs = 62_000,
            ),
            isPlaying = false,
            onPlayPause = {},
            onNext = {},
            onPrevious = {},
            onOpen = {},
        )
    }
}

@Preview(name = "Playing")
@Composable
private fun MiniPlayerPlayingPreview() {
    VibesTheme {
        MiniPlayer(
            song = MiniPlayerSong(
                id = "2",
                title = "A Very Long Song Title That Must Ellipsize",
                artist = "The Long Artist Name Collective",
                durationMs = 225_000,
                positionMs = 200_000,
            ),
            isPlaying = true,
            onPlayPause = {},
            onNext = {},
            onPrevious = {},
            onOpen = {},
        )
    }
}

