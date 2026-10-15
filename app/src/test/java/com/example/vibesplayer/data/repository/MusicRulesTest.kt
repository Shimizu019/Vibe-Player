package com.example.vibesplayer.data.repository

import com.example.vibesplayer.data.local.DownloadStatus
import com.example.vibesplayer.data.local.SongEntity
import com.example.vibesplayer.data.local.SongOrigin
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MusicRulesTest {
    private fun song(
        id: Long,
        title: String,
        artist: String = "Artist",
        album: String? = null,
        year: Int = 0,
        playCount: Int = 0,
        origin: SongOrigin = SongOrigin.DEVICE,
        downloadStatus: DownloadStatus = DownloadStatus.NONE,
    ) = SongEntity(
        id = id,
        origin = origin,
        remoteId = "r$id",
        title = title,
        artist = artist,
        album = album,
        albumId = null,
        durationMs = 200_000,
        year = year,
        license = null,
        licenseUrl = null,
        artworkUri = null,
        contentUri = "content://media/$id",
        downloadStatus = downloadStatus,
        downloadProgress = 0,
        localFilePath = null,
        sizeBytes = 1000,
        isLiked = false,
        likedAt = null,
        addedAt = 0,
        playCount = playCount,
        lastPlayedAt = null,
    )

    @Test
    fun `decadeOf maps a year to its decade start`() {
        val rules = MusicRules()
        assertEquals(1960, rules.decadeOf(1967))
        assertEquals(1950, rules.decadeOf(1959))
        assertEquals(1980, rules.decadeOf(1989))
    }

    @Test
    fun `decadeOf treats year 0 and negatives as unknown`() {
        val rules = MusicRules()
        assertEquals(null, rules.decadeOf(0))
        assertEquals(null, rules.decadeOf(-1))
    }

    @Test
    fun `inDecade matches inclusive bounds`() {
        val rules = MusicRules()
        val s = song(1, "Song", year = 1965)
        assertTrue(rules.inDecade(s, 1960))
        assertFalse(rules.inDecade(s, 1970))
        assertTrue(rules.inDecade(song(2, "Edge", year = 1970), 1970))
        assertTrue(rules.inDecade(song(3, "Edge", year = 1979), 1970))
    }

    @Test
    fun `goldenHits prefers play history when present`() {
        val rules = MusicRules()
        val songs = listOf(
            song(1, "A", year = 1990, playCount = 5),
            song(2, "B", year = 1980, playCount = 2),
            song(3, "C", year = 1970, playCount = 0),
        )
        val hits = rules.goldenHits(songs)
        assertEquals(listOf(1L, 2L), hits.map { it.id })
    }

    @Test
    fun `goldenHits falls back to pre-1990 songs when no history`() {
        val rules = MusicRules()
        val songs = listOf(
            song(1, "New", year = 2001),
            song(2, "Old", year = 1975),
            song(3, "Older", year = 1985),
        )
        val hits = rules.goldenHits(songs)
        // Oldies first (year <= 1989), ties A-Z, then any others.
        assertEquals(listOf(2L, 3L, 1L), hits.map { it.id })
    }

    @Test
    fun `rankSearch orders exact then prefix then contains then artist`() {
        val rules = MusicRules()
        val songs = listOf(
            song(1, "Hello World"),
            song(2, "Hello"),
            song(3, "Say Hello"),
            song(4, "Other", artist = "Hello Kit"),
            song(5, "Unrelated", artist = "Nope"),
        )
        val ranked = rules.rankSearch(songs, "hello")
        assertEquals(listOf(2L, 1L, 3L, 4L), ranked.map { it.id })
    }

    @Test
    fun `rankSearch returns empty for a blank query`() {
        val rules = MusicRules()
        assertTrue(rules.rankSearch(listOf(song(1, "X")), "   ").isEmpty())
    }

    @Test
    fun `playable offline for device songs regardless of download`() {
        val device = song(1, "D", origin = SongOrigin.DEVICE)
        assertTrue(device.isPlayableOffline)
    }

    @Test
    fun `playable offline for downloaded remote songs only when done`() {
        val downloading = song(
            2, "S",
            origin = SongOrigin.JAMENDO,
            downloadStatus = DownloadStatus.DOWNLOADING,
        )
        val done = song(
            3, "S2",
            origin = SongOrigin.JAMENDO,
            downloadStatus = DownloadStatus.DONE,
        )
        assertFalse(downloading.isPlayableOffline)
        assertTrue(done.isPlayableOffline)
    }

    @Test
    fun `remote song without download is not playable offline`() {
        val remote = song(4, "S3", origin = SongOrigin.JAMENDO)
        assertFalse(remote.isPlayableOffline)
    }
}
