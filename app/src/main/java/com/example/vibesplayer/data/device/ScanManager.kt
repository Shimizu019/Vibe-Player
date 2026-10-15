package com.example.vibesplayer.data.device

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.Looper
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.vibesplayer.di.IoDispatcher

/** Scan lifecycle state, surfaced to the UI with the spec section 17 messages. */
sealed interface ScanState {
    data object Idle : ScanState

    data object Scanning : ScanState

    data class Done(val songCount: Int) : ScanState
}

/**
 * Owns scanning: scan on app start, rescan when the observer fires, rescan on
 * demand (Settings "Scan now", Library pull-to-refresh). Exposes the scan state
 * and last scan time as Flows. The ContentObserver is registered while the app
 * runs and unregistered when the process scope is cancelled.
 */
@Singleton
class ScanManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val scanner: DeviceMusicScanner,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    private val scope = CoroutineScope(SupervisorJob() + ioDispatcher)

    private val _state = MutableStateFlow<ScanState>(ScanState.Idle)
    val state: StateFlow<ScanState> = _state.asStateFlow()

    private val _lastScanMs = MutableStateFlow<Long?>(null)
    val lastScanMs: StateFlow<Long?> = _lastScanMs.asStateFlow()

    private var scanJob: Job? = null
    private var observer: ContentObserver? = null

    /** Registers the observer and scans once when permission is already granted. */
    fun start() {
        if (observer == null) {
            val fresh = object : ContentObserver(Handler(Looper.getMainLooper())) {
                override fun onChange(selfChange: Boolean, uri: Uri?) {
                    scan()
                }

                override fun onChange(selfChange: Boolean) {
                    scan()
                }
            }
            context.contentResolver.registerContentObserver(
                MediaScanContract.collectionUri(),
                true,
                fresh,
            )
            observer = fresh
        }
        if (AudioPermission.isGranted(context)) {
            scan()
        }
    }

    fun stop() {
        observer?.let { context.contentResolver.unregisterContentObserver(it) }
        observer = null
        scanJob?.cancel()
    }

    /** One scan; concurrent calls collapse into the running one. */
    fun scan() {
        if (scanJob?.isActive == true) return
        if (!AudioPermission.isGranted(context)) return
        scanJob = scope.launch {
            _state.value = ScanState.Scanning
            val now = System.currentTimeMillis()
            val count = scanner.scan(now)
            _lastScanMs.value = now
            _state.value = ScanState.Done(count)
        }
    }
}
