package com.example.vibesplayer.data.device

import com.example.vibesplayer.data.local.DownloadStatus
import com.example.vibesplayer.data.local.SongOrigin
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

/**
 * Covers the pure mapping helper [MediaScanContract.buildSongEntity] on the JVM.
 * Android-dependent parts (the actual cursor, ContentUris, Build.VERSION) are
 * exercised by the androidTest suite instead of being mocked here.
 */
class MediaScanContractTest {

    @Test
    fun `full metadata maps to a DEVICE song`() {
        val song = MediaScanContract.buildSongEntity(
            id = 42L,
            cursorTitle = "Blue Moon",
            artist = "Elvis",
            album = "Album",
            albumId = 7L,
            durationMs = 180_000,
            year = 1956,
            sizeBytes = 5000,
            dateAddedSecs = 1_600_000_000,
            displayName = "01 - Blue Moon.mp3",
            contentUriString = "content://media/external/audio/media/42",
        )
        assertEquals(SongOrigin.DEVICE, song.origin)
        assertEquals("42", song.remoteId)
        assertEquals("Blue Moon", song.title)
        assertEquals("Elvis", song.artist)
        assertEquals("Album", song.album)
        assertEquals(7L, song.albumId)
        assertEquals(180_000L, song.durationMs)
        assertEquals(1956, song.year)
        assertEquals(5000L, song.sizeBytes)
        assertEquals(1_600_000_000L * 1000, song.addedAt)
        assertEquals("content://media/external/audio/media/42", song.contentUri)
        assertEquals(DownloadStatus.NONE, song.downloadStatus)
        assertEquals(0, song.playCount)
        assertNull(song.lastPlayedAt)
    }

    @Test
    fun `missing title falls back to file name without extension`() {
        val song = MediaScanContract.buildSongEntity(
            id = 1L,
            cursorTitle = null,
            artist = "Artist",
            album = null,
            albumId = null,
            durationMs = 60_000,
            year = 0,
            sizeBytes = 0,
            dateAddedSecs = 0,
            displayName = "MyTrack.flac",
            contentUriString = "content://media/external/audio/media/1",
        )
        assertEquals("MyTrack", song.title)
    }

    @Test
    fun `blank title falls back to file name`() {
        val song = MediaScanContract.buildSongEntity(
            id = 2L,
            cursorTitle = "   ",
            artist = "Artist",
            album = null,
            albumId = null,
            durationMs = 60_000,
            year = 0,
            sizeBytes = 0,
            dateAddedSecs = 0,
            displayName = "song.mp3",
            contentUriString = "content://media/external/audio/media/2",
        )
        assertEquals("song", song.title)
    }

    @Test
    fun `missing title and file name falls back to uri id`() {
        val song = MediaScanContract.buildSongEntity(
            id = 99L,
            cursorTitle = null,
            artist = "Artist",
            album = null,
            albumId = null,
            durationMs = 60_000,
            year = 0,
            sizeBytes = 0,
            dateAddedSecs = 0,
            displayName = null,
            contentUriString = "content://media/external/audio/media/99",
        )
        assertEquals("99", song.title)
    }

    @Test
    fun `missing artist becomes Unknown artist`() {
        val song = MediaScanContract.buildSongEntity(
            id = 3L,
            cursorTitle = "T",
            artist = null,
            album = null,
            albumId = null,
            durationMs = 60_000,
            year = 0,
            sizeBytes = 0,
            dateAddedSecs = 0,
            displayName = "T.mp3",
            contentUriString = "content://media/external/audio/media/3",
        )
        assertEquals("Unknown artist", song.artist)
    }

    @Test
    fun `year 0 is preserved as unknown`() {
        val song = MediaScanContract.buildSongEntity(
            id = 4L,
            cursorTitle = "T",
            artist = "A",
            album = null,
            albumId = null,
            durationMs = 60_000,
            year = 0,
            sizeBytes = 0,
            dateAddedSecs = 0,
            displayName = "T.mp3",
            contentUriString = "content://media/external/audio/media/4",
        )
        assertEquals(0, song.year)
    }

    @Test
    fun `projection never includes the deprecated DATA column`() {
        // The spec forbids reading the DATA column ("_data") to build a song path.
        // Column-name literals are used here so this stays a pure JVM test.
        assert(MediaScanContract.PROJECTION.none { it == "_data" }) {
            "PROJECTION must not contain the deprecated DATA column"
        }
    }

    @Test
    fun `projection includes display name for the filename fallback`() {
        // Spec 11.2: the file name backs up a missing title.
        assert(MediaScanContract.PROJECTION.contains("_display_name")) {
            "PROJECTION must include DISPLAY_NAME (_display_name)"
        }
    }
}
