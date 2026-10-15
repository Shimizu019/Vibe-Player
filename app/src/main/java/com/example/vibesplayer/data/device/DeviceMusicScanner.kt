package com.example.vibesplayer.data.device

import android.content.Context
import com.example.vibesplayer.data.local.SongDao
import com.example.vibesplayer.data.local.SongEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.CancellationException
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import com.example.vibesplayer.di.IoDispatcher

/**
 * Runs the MediaStore query on a background dispatcher (spec 11.2): upserts
 * DEVICE rows, deletes rows whose files are gone (their PlaylistSong rows go
 * with them via cascade), and returns the number of songs found.
 */
@Singleton
class DeviceMusicScanner @Inject constructor(
    @ApplicationContext private val context: Context,
    private val songDao: SongDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun scan(nowMs: Long): Int = withContext(ioDispatcher) {
        val found = mutableListOf<SongEntity>()
        context.contentResolver.query(
            MediaScanContract.collectionUri(),
            MediaScanContract.PROJECTION,
            MediaScanContract.SELECTION,
            null,
            null,
        )?.use { cursor ->
            while (cursor.moveToNext()) {
                ensureActive()
                found.add(MediaScanContract.mapCursorRow(cursor, nowMs))
            }
        }
        songDao.upsertAll(found)
        if (found.isEmpty()) {
            songDao.deleteDeviceSongsNotIn(listOf(NO_MATCH_SENTINEL))
        } else {
            songDao.deleteDeviceSongsNotIn(found.map { it.remoteId })
        }
        found.size
    }

    private fun ensureActive() {
        if (!kotlin.coroutines.coroutineContext.isActive) {
            throw CancellationException()
        }
    }

    private companion object {
        /** remoteId can never equal this, so an empty scan deletes every DEVICE row. */
        const val NO_MATCH_SENTINEL = "__no_match__"
    }
}
