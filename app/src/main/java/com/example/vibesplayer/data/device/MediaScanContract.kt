package com.example.vibesplayer.data.device

import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.example.vibesplayer.data.local.DownloadStatus
import com.example.vibesplayer.data.local.SongEntity
import com.example.vibesplayer.data.local.SongOrigin

/**
 * MediaStore query contract and cursor mapping (spec section 11.2). The DATA
 * column is never read: the playable URI is built from the row _ID.
 */
object MediaScanContract {
    /** Selection: music files of at least 30 seconds. */
    const val SELECTION =
        "${MediaStore.Audio.Media.IS_MUSIC} != 0 AND " +
            "${MediaStore.Audio.Media.DURATION} >= 30000"

    /** Columns in a fixed order; indices resolved by name at read time. */
    val PROJECTION = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.ALBUM,
        MediaStore.Audio.Media.ALBUM_ID,
        MediaStore.Audio.Media.DURATION,
        MediaStore.Audio.Media.YEAR,
        MediaStore.Audio.Media.SIZE,
        MediaStore.Audio.Media.DATE_ADDED,
        // File name, used only when TITLE is missing (spec 11.2). Not the
        // deprecated DATA column: this is the display name from the metadata.
        MediaStore.Audio.Media.DISPLAY_NAME,
    )

    /** External audio collection URI for this OS version. */
    fun collectionUri(): Uri =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            @Suppress("DEPRECATION")
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        }

    fun contentUriFor(id: Long): Uri =
        ContentUris.withAppendedId(collectionUri(), id)

    /** Album-art content URI for API 28 and older (spec 11.2). */
    @Suppress("DEPRECATION")
    fun legacyAlbumArtUri(albumId: Long): Uri =
        ContentUris.withAppendedId(
            Uri.parse("content://media/external/audio/albumart"),
            albumId,
        )

    /** Display title: the TITLE column, or the content URI's last segment as fallback. */
    fun displayTitle(cursorTitle: String?, contentUri: Uri): String {
        if (!cursorTitle.isNullOrBlank()) return cursorTitle
        return contentUri.lastPathSegment ?: "Unknown title"
    }

    /**
     * Pure mapping: no Android framework calls, so unit tests cover it on the JVM.
     * The playable [contentUriString] is passed in already built. Title falls back
     * to the file name without its extension, then to the URI id (spec 11.2).
     */
    fun buildSongEntity(
        id: Long,
        cursorTitle: String?,
        artist: String?,
        album: String?,
        albumId: Long?,
        durationMs: Long,
        year: Int,
        sizeBytes: Long,
        dateAddedSecs: Long,
        displayName: String?,
        contentUriString: String,
    ): SongEntity {
        val title = when {
            !cursorTitle.isNullOrBlank() -> cursorTitle
            !displayName.isNullOrBlank() -> displayName.substringBeforeLast('.', displayName)
            else -> contentUriString.substringAfterLast('/').ifEmpty { "Unknown title" }
        }
        return SongEntity(
            origin = SongOrigin.DEVICE,
            remoteId = id.toString(),
            title = title,
            artist = if (artist.isNullOrBlank()) "Unknown artist" else artist,
            album = album,
            albumId = albumId,
            durationMs = durationMs,
            year = year,
            license = null,
            licenseUrl = null,
            artworkUri = null,
            contentUri = contentUriString,
            downloadStatus = DownloadStatus.NONE,
            downloadProgress = 0,
            localFilePath = null,
            sizeBytes = sizeBytes,
            isLiked = false,
            likedAt = null,
            addedAt = dateAddedSecs * 1000,
            playCount = 0,
            lastPlayedAt = null,
        )
    }

    /** Builds the playable URI for the id, then delegates to the pure mapper. */
    fun mapRow(
        id: Long,
        cursorTitle: String?,
        artist: String?,
        album: String?,
        albumId: Long?,
        durationMs: Long,
        year: Int,
        sizeBytes: Long,
        dateAddedSecs: Long,
        displayName: String?,
        nowMs: Long,
    ): SongEntity = buildSongEntity(
        id = id,
        cursorTitle = cursorTitle,
        artist = artist,
        album = album,
        albumId = albumId,
        durationMs = durationMs,
        year = year,
        sizeBytes = sizeBytes,
        dateAddedSecs = dateAddedSecs,
        displayName = displayName,
        contentUriString = contentUriFor(id).toString(),
    )

    /** Reads the current cursor row. Call only with the projection above. */
    fun mapCursorRow(cursor: Cursor, nowMs: Long): SongEntity {
        fun string(name: String): String? =
            cursor.getString(cursor.getColumnIndexOrThrow(name))

        fun long(name: String): Long =
            cursor.getLong(cursor.getColumnIndexOrThrow(name))

        fun int(name: String): Int =
            cursor.getInt(cursor.getColumnIndexOrThrow(name))

        val id = long(MediaStore.Audio.Media._ID)
        val displayName = runCatching {
            cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME))
        }.getOrNull()
        return mapRow(
            id = id,
            cursorTitle = string(MediaStore.Audio.Media.TITLE),
            artist = string(MediaStore.Audio.Media.ARTIST),
            album = string(MediaStore.Audio.Media.ALBUM),
            albumId = runCatching { long(MediaStore.Audio.Media.ALBUM_ID) }.getOrNull(),
            durationMs = long(MediaStore.Audio.Media.DURATION),
            year = runCatching { int(MediaStore.Audio.Media.YEAR) }.getOrDefault(0),
            sizeBytes = runCatching { long(MediaStore.Audio.Media.SIZE) }.getOrDefault(0L),
            dateAddedSecs = runCatching {
                long(MediaStore.Audio.Media.DATE_ADDED)
            }.getOrDefault(nowMs / 1000),
            displayName = displayName,
            nowMs = nowMs,
        )
    }
}
