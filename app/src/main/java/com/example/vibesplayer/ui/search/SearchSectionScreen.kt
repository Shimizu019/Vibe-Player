package com.example.vibesplayer.ui.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vibesplayer.R
import com.example.vibesplayer.ui.theme.VibesColors
import com.example.vibesplayer.ui.theme.VibesType

/** Milestone 1 shell placeholder; the full section list arrives in milestone 4. */
@Composable
fun SearchSectionScreen(section: String, onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        IconButton(onClick = onBack, modifier = Modifier.align(Alignment.TopStart)) {
            Icon(
                painter = painterResource(R.drawable.ic_chevron_left),
                contentDescription = stringResource(R.string.cd_back),
                tint = VibesColors.Sleeve,
            )
        }
        Text(
            text = section.replaceFirstChar { it.uppercase() },
            style = VibesType.titleM.copy(color = VibesColors.Gold),
        )
    }
}
