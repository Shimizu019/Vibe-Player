package com.example.vibesplayer.ui.permission

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.vibesplayer.data.device.AudioPermission

/** Permission entry point: decides which state the screen shows. */
@Composable
fun PermissionGate(
    onGranted: () -> Unit,
    onDismissed: () -> Unit,
    viewModel: PermissionViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val permanentlyDenied by viewModel.permanentlyDenied.collectAsStateWithLifecycle()
    var resolved by remember { mutableStateOf<Boolean?>(null) }
    val systemLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { granted ->
        if (granted) {
            context.markPermissionAsked()
            viewModel.onGranted()
            resolved = true
        } else {
            context.markPermissionAsked()
            viewModel.onDenied(canAskAgain = context.canAskAgain())
        }
    }

    val required = AudioPermission.requiredPermission()
    LaunchedEffect(required) {
        if (required == null) {
            resolved = true
        } else if (ContextCompat.checkSelfPermission(context, required) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            resolved = true
        } else {
            resolved = false
        }
    }
    LaunchedEffect(resolved) {
        if (resolved == true) onGranted()
    }
    when (resolved) {
        true -> return
        false -> Unit
        null -> return
    }

    // resolved == false is only reachable when a permission is required and not yet
    // granted, so required is non-null here; narrow it for the calls below.
    val permission = required ?: return

    PermissionScreen(
        permanentlyDenied = permanentlyDenied || !context.canAskAgainFor(permission),
        onContinue = {
            if (permanentlyDenied || !context.canAskAgainFor(permission)) {
                context.openAppSettings()
            } else {
                systemLauncher.launch(permission)
            }
        },
        onNotNow = {
            viewModel.onDismissed()
            onDismissed()
        },
    )
}

private fun Context.openAppSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        .setData(Uri.fromParts("package", packageName, null))
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    startActivity(intent)
}

/** True while the system will still show its permission prompt. */
private fun Context.canAskAgain(): Boolean {
    val permission = AudioPermission.requiredPermission() ?: return false
    return canAskAgainFor(permission)
}

private fun Context.canAskAgainFor(permission: String): Boolean {
    val activity = findActivity() ?: return true
    return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission) ||
        !hasDeniedBefore(permission)
}

private fun Context.hasDeniedBefore(permission: String): Boolean =
    getSharedPreferences(PERMISSION_PREFS, Context.MODE_PRIVATE)
        .getBoolean("$DENIED_PREFIX$permission", false)

private fun Context.markPermissionAsked() {
    val permission = AudioPermission.requiredPermission() ?: return
    getSharedPreferences(PERMISSION_PREFS, Context.MODE_PRIVATE)
        .edit()
        .putBoolean("$DENIED_PREFIX$permission", true)
        .apply()
}

private fun Context.findActivity(): Activity? {
    var current = this
    while (current is ContextWrapper) {
        if (current is Activity) return current
        current = current.baseContext
    }
    return null
}

private const val PERMISSION_PREFS = "vibes_permission"
private const val DENIED_PREFIX = "denied_"
