package com.example.vibesplayer.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vibesplayer.R
import com.example.vibesplayer.ui.components.Artwork
import com.example.vibesplayer.ui.components.BottomTabBar
import com.example.vibesplayer.ui.components.DialogActions
import com.example.vibesplayer.ui.components.DownloadButton
import com.example.vibesplayer.ui.components.DownloadState
import com.example.vibesplayer.ui.components.EraChip
import com.example.vibesplayer.ui.components.EmptyState
import com.example.vibesplayer.ui.components.MainTab
import com.example.vibesplayer.ui.components.MiniPlayer
import com.example.vibesplayer.ui.components.MiniPlayerSong
import com.example.vibesplayer.ui.components.RecentSearchChip
import com.example.vibesplayer.ui.components.RecordArtwork
import com.example.vibesplayer.ui.components.SectionHeader
import com.example.vibesplayer.ui.components.SegmentedControl
import com.example.vibesplayer.ui.components.SheetHandle
import com.example.vibesplayer.ui.components.SkeletonRows
import com.example.vibesplayer.ui.components.SongRow
import com.example.vibesplayer.ui.components.SongRowBadge
import com.example.vibesplayer.ui.components.SongRowTrailing
import com.example.vibesplayer.ui.components.VibesSnackbar
import com.example.vibesplayer.ui.theme.Dimens
import com.example.vibesplayer.ui.theme.VibesColors
import com.example.vibesplayer.ui.theme.VibesType

/**
 * The debug components gallery (spec section 19.1): one scrollable list showing every
 * shared component from section 8 in every state. Only compiled into debug builds.
 */
@Composable
fun ComponentsGalleryScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
            .verticalScroll(rememberScrollState())
            .padding(vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(Dimens.blockGap),
    ) {
        GalleryHeader()
        Section("SectionHeader") {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SectionHeader(title = "Recently Played", actionLabel = "See All", onAction = {})
                SectionHeader(
                    title = "Top Songs",
                    actionLabel = "VIEW ALL",
                    actionStyle = VibesType.label,
                    onAction = {},
                )
                SectionHeader(title = "Explore Eras")
            }
        }
        Section("EraChip") {
            Row(horizontalArrangement = Arrangement.spacedBy(Dimens.eraChipGap)) {
                EraChip(label = "1950s", selected = false, onClick = {})
                EraChip(label = "1960s", selected = true, onClick = {})
                EraChip(label = "1970s", selected = false, onClick = {})
                EraChip(label = "1980s", selected = false, onClick = {})
            }
        }
        Section("RecentSearchChip") {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                RecentSearchChip(query = "queens of the stone age", onSearch = {}, onRemove = {})
                RecentSearchChip(query = "fleetwood mac", onSearch = {}, onRemove = {})
            }
        }
        Section("SegmentedControl") {
            var selected by remember { mutableIntStateOf(0) }
            SegmentedControl(
                options = listOf(
                    stringResource(R.string.segment_playlists),
                    stringResource(R.string.segment_downloads),
                    stringResource(R.string.segment_liked),
                ),
                selectedIndex = selected,
                onSelect = { selected = it },
            )
        }
        Section("SongRow") {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                SongRow(
                    title = "Sweet Home Alabama",
                    artist = "Lynyrd Skynyrd",
                    year = 1974,
                    onClick = {},
                )
                SongRow(
                    title = "Bohemian Rhapsody",
                    artist = "Queen",
                    year = 1975,
                    isPlaying = true,
                    onClick = {},
                )
                SongRow(
                    title = "Billie Jean",
                    artist = "Michael Jackson",
                    year = 1982,
                    badge = SongRowBadge.Downloaded,
                    onClick = {},
                )
                SongRow(
                    title = "Free Bird",
                    artist = "Lynyrd Skynyrd",
                    year = 1973,
                    badge = SongRowBadge.OnThisPhone,
                    onClick = {},
                )
                SongRow(
                    title = "Midnight City",
                    artist = "M83",
                    year = 2011,
                    onlineOnly = true,
                    onClick = {},
                )
                SongRow(
                    title = "Take On Me",
                    artist = "a-ha",
                    year = 1985,
                    trailing = SongRowTrailing.Downloading,
                    downloadProgress = 0.6f,
                    onClick = {},
                    onTrailingClick = {},
                )
            }
        }
        Section("MiniPlayer") {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
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
        Section("BottomTabBar") {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                BottomTabBar(selected = MainTab.Home, onSelect = {})
                BottomTabBar(selected = MainTab.Search, onSelect = {})
                BottomTabBar(selected = MainTab.Library, onSelect = {})
            }
        }
        Section("DownloadButton") {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DownloadButton(state = DownloadState.Idle, onClick = {})
                DownloadButton(state = DownloadState.Downloading, downloadProgress = 0.6f, onClick = {})
                DownloadButton(state = DownloadState.Downloaded, onClick = {})
            }
        }
        Section("EmptyState") {
            EmptyState(
                iconRes = R.drawable.ic_music,
                title = stringResource(R.string.home_empty_title),
                body = stringResource(R.string.home_empty_body),
                actionLabel = stringResource(R.string.local_storage),
                onAction = {},
                modifier = Modifier.fillMaxWidth(),
            )
        }
        Section("Skeleton") {
            SkeletonRows(count = 3)
        }
        Section("Dialogs and sheets") {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                SheetHandle()
                DialogActions(onDismiss = {}, onConfirm = {}, confirmLabel = "Save")
            }
        }
        Section("Snackbar") {
            VibesSnackbar(
                message = stringResource(R.string.added_to_liked),
                actionLabel = stringResource(R.string.try_again),
                onAction = {},
            )
        }
        Section("Artwork") {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Artwork(model = null, title = "Golden Hits", modifier = Modifier.size(72.dp))
                RecordArtwork(modifier = Modifier.size(72.dp))
            }
        }

    }
}

@Composable
private fun GalleryHeader() {
    Text(
        text = stringResource(R.string.gallery_title),
        style = VibesType.titleL,
        color = VibesColors.Gold,
        modifier = Modifier.padding(horizontal = Dimens.screenMargin),
    )
}

/** Titled block used for every gallery section. */
@Composable
private fun Section(title: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.screenMargin),
        verticalArrangement = Arrangement.spacedBy(Dimens.headingToContent),
    ) {
        Text(text = title, style = VibesType.label, color = VibesColors.Sand)
        content()
    }
}
