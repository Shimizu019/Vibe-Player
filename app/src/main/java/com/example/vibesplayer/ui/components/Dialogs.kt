package com.example.vibesplayer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vibesplayer.R
import com.example.vibesplayer.ui.theme.Dimens
import com.example.vibesplayer.ui.theme.VibesColors
import com.example.vibesplayer.ui.theme.VibesTheme
import com.example.vibesplayer.ui.theme.VibesType

/**
 * Bottom-sheet drag handle (spec section 8.9): 32 x 4 dp Border-colored capsule.
 * Pass it as the `dragHandle` of a Material ModalBottomSheet.
 */
@Composable
fun SheetHandle(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(width = 32.dp, height = 4.dp)
            .clip(RoundedCornerShape(Dimens.radiusMedium / 2))
            .background(VibesColors.Border),
    )
}

/**
 * Text-button row for dialogs and sheets (spec section 8.9): "Cancel" in Sand and the
 * main action in Gold. The container supplies the Surface fill and 8 dp radius.
 */
@Composable
fun DialogActions(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    confirmLabel: String,
    modifier: Modifier = Modifier,
    dismissLabel: String = stringResource(R.string.cancel),
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd,
    ) {
        TextButton(onClick = onDismiss) {
            Text(dismissLabel, style = VibesType.bodyMed, color = VibesColors.Sand)
        }
        TextButton(onClick = onConfirm) {
            Text(confirmLabel, style = VibesType.bodyMed, color = VibesColors.Gold)
        }
    }
}

/**
 * Snackbar content (spec section 8.10): Surface, 1 dp Border, 8 dp radius, body text
 * in Sleeve, action label in Gold. Callers show it above the mini player and tab bar.
 */
@Composable
fun VibesSnackbar(
    message: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
) {
    androidx.compose.foundation.layout.Row(
        modifier = modifier
            .clip(RoundedCornerShape(Dimens.radiusMedium))
            .background(VibesColors.Surface)
            .border(1.dp, VibesColors.Border, RoundedCornerShape(Dimens.radiusMedium))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = message,
            style = VibesType.body,
            color = VibesColors.Sleeve,
            modifier = Modifier.weight(1f),
        )
        if (actionLabel != null && onAction != null) {
            TextButton(onClick = onAction) {
                Text(actionLabel, style = VibesType.bodyMed, color = VibesColors.Gold)
            }
        }
    }
}

@Preview(name = "Sheet handle")
@Composable
private fun SheetHandlePreview() {
    VibesTheme {
        SheetHandle(Modifier.padding(16.dp))
    }
}

@Preview(name = "Dialog actions")
@Composable
private fun DialogActionsPreview() {
    VibesTheme {
        DialogActions(onDismiss = {}, onConfirm = {}, confirmLabel = "Save")
    }
}

@Preview(name = "Snackbar with action")
@Composable
private fun SnackbarPreview() {
    VibesTheme {
        VibesSnackbar(
            message = stringResource(R.string.added_to_liked),
            actionLabel = stringResource(R.string.try_again),
            onAction = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}
