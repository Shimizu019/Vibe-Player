package com.example.vibesplayer.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlaylistDaoTest {
    private lateinit var db: VibesDatabase
    private lateinit var playlistDao: PlaylistDao
    private lateinit var songDao: SongDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            VibesDatabase::class.java,
        ).allowMainThreadQueries().build()
        playlistDao = db.playlistDao()
        songDao = db.songDao()
    }

    @After
    fun tearDown() = db.close()

    private suspend fun newSong(title: String): Long {
        val song = SongEntity(
            id = 0,
            origin = SongOrigin.DEVICE,
            remoteId = title,
            title = title,
            artist = "A",
            album = null,
            albumId = null,
            durationMs = 120_000,
            year = 1980,
            license = null,
            licenseUrl = null,
            artworkUri = null,
            contentUri = "content://media/$title",
            downloadStatus = DownloadStatus.NONE,
            downloadProgress = 0,
            localFilePath = null,
            sizeBytes = 1000,
            isLiked = false,
            likedAt = null,
            addedAt = 0,
            playCount = 0,
            lastPlayedAt = null,
        )
        return songDao.upsert(song)
    }

    @Test
    fun deletingSongCascadesPlaylistMembership() = runTest {
        val playlistId = playlistDao.upsertPlaylist(PlaylistEntity(name = "P", createdAt = 1))
        val songId = newSong("S1")
        playlistDao.upsertMembership(PlaylistSongEntity(playlistId, songId, 0, 1))
        assertEquals(1, playlistDao.observeTracks(playlistId).first().size)

        songDao.deleteById(songId)
        assertTrue(playlistDao.observeTracks(playlistId).first().isEmpty())
    }

    @Test
    fun deletingPlaylistCascadesMembership() = runTest {
        val playlistId = playlistDao.upsertPlaylist(PlaylistEntity(name = "P", createdAt = 1))
        val songId = newSong("S1")
        playlistDao.upsertMembership(PlaylistSongEntity(playlistId, songId, 0, 1))

        playlistDao.deletePlaylist(playlistId)
        assertTrue(playlistDao.observePlaylists().first().isEmpty())
        // The song itself remains in the library.
        assertEquals(1, songDao.observeAllSongs().first().size)
    }

    @Test
    fun observePlaylistsWithStatsCountsTracksAndDuration() = runTest {
        val playlistId = playlistDao.upsertPlaylist(PlaylistEntity(name = "P", createdAt = 1))
        val a = newSong("A")
        val b = newSong("B")
        playlistDao.upsertMembership(PlaylistSongEntity(playlistId, a, 0, 1))
        playlistDao.upsertMembership(PlaylistSongEntity(playlistId, b, 1, 2))

        val stats = playlistDao.observePlaylistsWithStats().first().first()
        assertEquals(2, stats.trackCount)
        assertEquals(240_000L, stats.totalDurationMs)
    }
}
