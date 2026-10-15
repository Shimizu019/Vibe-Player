package com.example.vibesplayer.data.device

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Size
import com.example.vibesplayer.data.local.SongEntity
import com.example.vibesplayer.data.local.SongOrigin
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import com.example.vibesplayer.di.IoDispatcher

/**
 * Artwork model for a song (spec sections 11.2, 12). DEVICE songs resolve to a
 * thumbnail on API 29+ or the legacy album-art URI on older versions; anything
 * else returns null and the Artwork composable draws the placeholder.
 */
@Singleton
class DeviceArtworkResolver @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    /** URI model for Coil, or null when the placeholder should be drawn. */
    suspend fun artworkModel(song: SongEntity): Any? = withContext(ioDispatcher) {
        if (song.origin != SongOrigin.DEVICE) {
            return@withContext song.artworkUri
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val uri = Uri.parse(song.contentUri)
            val exists = runCatching {
                context.contentResolver.loadThumbnail(uri, Size(300, 300), null)
            }.getOrNull()
            if (exists != null) return@withContext uri
            return@withContext null
        }
        val albumId = song.albumId ?: return@withContext null
        MediaScanContract.legacyAlbumArtUri(albumId)
    }

    /** Eager thumbnail bytes for callers that need a Bitmap; null on failure. */
    suspend fun thumbnailBitmap(song: SongEntity): Bitmap? = withContext(ioDispatcher) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return@withContext null
        if (song.origin != SongOrigin.DEVICE) return@withContext null
        runCatching {
            context.contentResolver.loadThumbnail(
                Uri.parse(song.contentUri),
                Size(300, 300),
                null,
            )
        }.getOrNull()
    }
}
