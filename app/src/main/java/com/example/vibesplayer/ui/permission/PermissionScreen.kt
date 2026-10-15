package com.example.vibesplayer.ui.permission

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
 * Permission screen (spec 10.6, text from 17): centered music icon, heading,
 * body, a Gold Continue button opening the system dialog, and a Not now text
 * button. When [permanentlyDenied] the button reads Open settings instead.
 */
@Composable
fun PermissionScreen(
    permanentlyDenied: Boolean,
    onContinue: () -> Unit,
    onNotNow: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.screenMargin),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_music),
            contentDescription = null,
            tint = VibesColors.Sand,
            modifier = Modifier.size(48.dp),
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.permission_title),
            style = VibesType.heading,
            color = VibesColors.Sleeve,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.permission_body),
            style = VibesType.body,
            color = VibesColors.Sand,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        Spacer(Modifier.height(32.dp))
        Button(
            onClick = onContinue,
            colors = ButtonDefaults.buttonColors(
                containerColor = VibesColors.Gold,
                contentColor = VibesColors.Espresso,
            ),
        ) {
            Text(
                text = stringResource(
                    if (permanentlyDenied) R.string.open_settings else R.string.continue_label,
                ),
                style = VibesType.bodyMed,
            )
        }
        TextButton(onClick = onNotNow) {
            Text(
                text = stringResource(R.string.not_now),
                style = VibesType.bodyMed,
                color = VibesColors.Sand,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1E1915)
@Composable
private fun PermissionScreenPreview() {
    VibesTheme {
        PermissionScreen(permanentlyDenied = false, onContinue = {}, onNotNow = {})
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1E1915)
@Composable
private fun PermissionScreenDeniedPreview() {
    VibesTheme {
        PermissionScreen(permanentlyDenied = true, onContinue = {}, onNotNow = {})
    }
}
