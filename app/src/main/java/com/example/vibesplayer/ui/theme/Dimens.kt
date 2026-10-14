package com.example.vibesplayer.ui.theme

import androidx.compose.ui.unit.dp

/**
 * Spacing and sizing from spec sections 7.4 and 7.5. Screens and components use these
 * tokens instead of magic numbers so every distance is traceable to the spec.
 */
object Dimens {
    // Screen margins (7.5): 24 dp side margin; the mini player sits 16 dp in.
    val screenMargin = 24.dp
    val miniPlayerInset = 16.dp
    val miniPlayerTopGap = 8.dp

    // Mini player card content (8.2): 70 dp card, 40 dp cover, 16/8 dp content padding.
    val miniPlayerHeight = 70.dp
    val miniPlayerStartPadding = 16.dp
    val miniPlayerEndPadding = 8.dp
    val miniPlayerCover = 40.dp
    val miniPlayerCoverTextGap = 12.dp
    val miniPlayerTextTransportGap = 4.dp
    val miniPlayerTransportIcon = 24.dp
    val miniPlayerPlayPause = 44.dp
    val miniPlayerProgressHeight = 3.dp

    // Block spacing (7.5).
    val blockGap = 32.dp
    val sectionGap = 40.dp
    val headingToContent = 16.dp

    // Rows (7.5): 48 dp cover, 16 dp gap, 72 dp on Home/Search, 80 dp in collections.
    val rowCover = 48.dp
    val rowCoverGap = 16.dp
    val rowHeightCompact = 72.dp
    val rowHeightCollection = 80.dp
    val rowPaddingCompact = 12.dp
    val rowPaddingCollection = 16.dp
    val rowDivider = 1.dp

    // Corner radii (7.4): only 4 dp, 8 dp, and full.
    val radiusSmall = 4.dp
    val radiusMedium = 8.dp

    // Buttons and touch targets (7.5): minimum 48 dp; trailing icons end 4 dp in.
    val touchTarget = 48.dp
    val trailingIconEndPadding = 4.dp
    val topBarButtonEndPadding = 8.dp
    val iconButton = 52.dp

    // Tab bar (8.3).
    val tabBarHeight = 64.dp
    val tabBarHorizontalPadding = 32.dp
    val tabIconSize = 24.dp
    val tabIconLabelGap = 4.dp

    // Chips and misc shared components.
    val eraChipHeight = 40.dp
    val eraChipHorizontalPadding = 24.dp
    val eraChipGap = 12.dp
    val recentSearchChipHeight = 36.dp
    val recentSearchChipHorizontalPadding = 16.dp
    val recentSearchChipGap = 8.dp
    val segmentedControlHeight = 48.dp
    val segmentedControlInnerPadding = 4.dp
    val progressBarHeight = 4.dp
    val downloadRingSize = 24.dp
}
