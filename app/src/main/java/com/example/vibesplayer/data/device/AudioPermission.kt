package com.example.vibesplayer.data.device

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

/**
 * Audio permission rules (spec section 11.2): API 33+ needs READ_MEDIA_AUDIO,
 * API 23-32 needs READ_EXTERNAL_STORAGE, API 21-22 grant at install so no
 * request is needed.
 */
object AudioPermission {
    /** The permission to request on this device, or null when none is needed. */
    fun requiredPermission(): String? =
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> Manifest.permission.READ_MEDIA_AUDIO
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> Manifest.permission.READ_EXTERNAL_STORAGE
            else -> null
        }

    /** True when the scan may run: permission granted, or none required. */
    fun isGranted(context: Context): Boolean {
        val permission = requiredPermission() ?: return true
        return ContextCompat.checkSelfPermission(context, permission) ==
            PackageManager.PERMISSION_GRANTED
    }

    /**
     * Manifest entry for the legacy permission, kept in sync with the runtime
     * rule above (maxSdkVersion 32, since API 33+ uses READ_MEDIA_AUDIO).
     */
    const val LEGACY_MAX_SDK = 32
}
