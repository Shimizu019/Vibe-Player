package com.example.vibesplayer.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.vibesplayer.ui.theme.VibesColors
import com.example.vibesplayer.ui.theme.VibesTheme
import com.example.vibesplayer.ui.theme.VibesType

/**
 * Section heading (spec section 8.4): heading style at the start with an optional action
 * at the end - "See All" (bodyMed, Gold) on Home, "VIEW ALL" (label style, Gold) on
 * Search. The caller passes the label and style so both variants stay one component.
 */
@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    actionStyle: TextStyle = VibesType.bodyMed,
    onAction: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = VibesType.heading,
            color = VibesColors.Sleeve,
        )
        if (actionLabel != null && onAction != null) {
            Text(
                text = actionLabel,
                style = actionStyle.copy(color = VibesColors.Gold),
                modifier = Modifier.clickable { onAction() },
            )
        }
    }
}

@Preview(name = "See All action")
@Composable
private fun SectionHeaderHomePreview() {
    VibesTheme {
        SectionHeader(
            title = "Recently Played",
            actionLabel = "See All",
            onAction = {},
        )
    }
}

@Preview(name = "VIEW ALL action")
@Composable
private fun SectionHeaderSearchPreview() {
    VibesTheme {
        SectionHeader(
            title = "Top Songs",
            actionLabel = "VIEW ALL",
            actionStyle = VibesType.label,
            onAction = {},
        )
    }
}

@Preview(name = "No action")
@Composable
private fun SectionHeaderPlainPreview() {
    VibesTheme {
        SectionHeader(title = "Explore Eras")
    }
}
