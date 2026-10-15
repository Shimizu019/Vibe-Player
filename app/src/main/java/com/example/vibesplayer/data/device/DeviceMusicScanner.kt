package com.example.vibesplayer.data.device

import android.content.Context
import com.example.vibesplayer.data.local.SongDao
import com.example.vibesplayer.data.local.SongEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.CancellationException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import com.example.vibesplayer.di.IoDispatcher

/** Thrown when the MediaStore query cannot be completed, so no DEVICE rows are touched. */
class ScanFailureException(message: String) : Exception(message)

/**
 * Runs the MediaStore query on a background dispatcher (spec 11.2): upserts
 * DEVICE rows, deletes rows whose files are gone (their PlaylistSong rows go
 * with them via cascade), and returns the number of songs found.
 *
 * Data integrity (spec 14 / regression tests): a null cursor is a failed query,
 * never an empty library. If the query fails or the scan is cancelled, no upsert
 * and no stale-record deletion runs, so every previously scanned DEVICE row is
 * preserved. Stale cleanup happens only after a complete, successful scan.
 */
@Singleton
class DeviceMusicScanner @Inject constructor(
    @ApplicationContext private val context: Context,
    private val songDao: SongDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun scan(nowMs: Long): Int = withContext(ioDispatcher) {
        val found = mutableListOf<SongEntity>()
        val cursor = context.contentResolver.query(
            MediaScanContract.collectionUri(),
            MediaScanContract.PROJECTION,
            MediaScanContract.SELECTION,
            null,
            null,
        ) ?: throw ScanFailureException("MediaStore audio query returned no cursor")

        cursor.use {
            while (it.moveToNext()) {
                // Cancellation throws CancellationException before any DB write,
                // so the stale-record deletion below is never reached.
                coroutineContext.ensureActive()
                found.add(MediaScanContract.mapCursorRow(it, nowMs))
            }
        }
        // Re-check after iteration: a cancellation that arrived mid-scan must not
        // proceed to upsert or deletion.
        coroutineContext.ensureActive()
        songDao.upsertAll(found)
        if (found.isEmpty()) {
            songDao.deleteDeviceSongsNotIn(listOf(NO_MATCH_SENTINEL))
        } else {
            songDao.deleteDeviceSongsNotIn(found.map { it.remoteId })
        }
        found.size
    }

    private companion object {
        /** remoteId can never equal this, so an empty scan deletes every DEVICE row. */
        const val NO_MATCH_SENTINEL = "__no_match__"
    }
}
