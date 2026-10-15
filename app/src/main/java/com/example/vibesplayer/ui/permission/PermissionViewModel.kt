package com.example.vibesplayer.ui.permission

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Permission-screen state. A denial that still allows another system prompt
 * shows "Allow access"; when the system will no longer prompt, the button
 * opens the app settings page instead (spec 11.2).
 */
@HiltViewModel
class PermissionViewModel @Inject constructor() : ViewModel() {
    private val _permanentlyDenied = MutableStateFlow(false)
    val permanentlyDenied: StateFlow<Boolean> = _permanentlyDenied.asStateFlow()

    private val _dismissed = MutableStateFlow(false)
    val dismissed: StateFlow<Boolean> = _dismissed.asStateFlow()

    fun onGranted() {
        _permanentlyDenied.value = false
    }

    fun onDenied(canAskAgain: Boolean) {
        _permanentlyDenied.value = !canAskAgain
    }

    fun onDismissed() {
        _dismissed.value = true
    }
}
