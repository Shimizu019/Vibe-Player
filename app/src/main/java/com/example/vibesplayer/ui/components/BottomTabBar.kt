package com.example.vibesplayer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vibesplayer.R
import com.example.vibesplayer.ui.theme.Dimens
import com.example.vibesplayer.ui.theme.VibesColors
import com.example.vibesplayer.ui.theme.VibesTheme
import com.example.vibesplayer.ui.theme.VibesType

/** The three root destinations shown in the bottom tab bar (spec section 9). */
enum class MainTab { Home, Search, Library }

/**
 * Bottom tab bar (spec section 8.3). Espresso background with a 1 dp divider on top,
 * 64 dp content height plus the bottom system inset. Selected tab is Gold; unselected
 * is a Sleeve icon with a Sand label. The selected item carries `selected = true` in
 * its semantics for TalkBack.
 */
@Composable
fun BottomTabBar(
    selected: MainTab,
    onSelect: (MainTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(VibesColors.Divider),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(VibesColors.Espresso)
                .padding(horizontal = Dimens.tabBarHorizontalPadding)
                .height(Dimens.tabBarHeight),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TabItem(MainTab.Home, selected, onSelect)
            TabItem(MainTab.Search, selected, onSelect)
            TabItem(MainTab.Library, selected, onSelect)
        }
    }
}

@Composable
private fun TabItem(
    tab: MainTab,
    selected: MainTab,
    onSelect: (MainTab) -> Unit,
) {
    val isSelected = tab == selected
    val tint = if (isSelected) VibesColors.Gold else VibesColors.Sleeve
    val labelColor = if (isSelected) VibesColors.Gold else VibesColors.Sand
    val labelRes = when (tab) {
        MainTab.Home -> R.string.tab_home
        MainTab.Search -> R.string.tab_search
        MainTab.Library -> R.string.tab_library
    }
    val iconRes = when (tab) {
        MainTab.Home -> if (isSelected) R.drawable.ic_home_selected else R.drawable.ic_home
        MainTab.Search -> R.drawable.ic_search
        MainTab.Library -> R.drawable.ic_layers
    }
    val label = stringResource(labelRes)

    Column(
        modifier = Modifier
            .size(width = Dimens.touchTarget, height = Dimens.tabBarHeight)
            .selectable(
                selected = isSelected,
                role = Role.Tab,
                onClick = { onSelect(tab) },
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(Dimens.tabIconSize),
        )
        Spacer(Modifier.height(Dimens.tabIconLabelGap))
        // Tab label uses the label style (14 sp, +1.2 sp tracking) but stays in normal
        // case: only the labelL styles (NOW PLAYING, LOCAL STORAGE) are uppercased (7.3).
        Text(
            text = label,
            style = VibesType.label,
            color = labelColor,
        )
    }
}

@Preview
@Composable
private fun BottomTabBarPreview() {
    VibesTheme {
        Column {
            BottomTabBar(selected = MainTab.Home, onSelect = {})
            Spacer(Modifier.height(8.dp))
            BottomTabBar(selected = MainTab.Search, onSelect = {})
            Spacer(Modifier.height(8.dp))
            BottomTabBar(selected = MainTab.Library, onSelect = {})
        }
    }
}
