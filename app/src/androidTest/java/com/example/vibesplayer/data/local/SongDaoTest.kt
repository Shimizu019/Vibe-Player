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
class SongDaoTest {
    private lateinit var db: VibesDatabase
    private lateinit var dao: SongDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            VibesDatabase::class.java,
        ).allowMainThreadQueries().build()
        dao = db.songDao()
    }

    @After
    fun tearDown() = db.close()

    private fun device(id: Long, title: String, artist: String = "A") = SongEntity(
        id = id,
        origin = SongOrigin.DEVICE,
        remoteId = "dev$id",
        title = title,
        artist = artist,
        album = null,
        albumId = null,
        durationMs = 200_000,
        year = 1970,
        license = null,
        licenseUrl = null,
        artworkUri = null,
        contentUri = "content://media/$id",
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

    @Test
    fun upsertDoesNotDuplicateSameOriginAndRemoteId() = runTest {
        dao.upsert(device(0, "Song"))
        dao.upsert(device(0, "Song")) // same origin+remoteId => upsert, not duplicate
        assertEquals(1, dao.observeAllSongs().first().size)
    }

    @Test
    fun deleteDeviceSongsNotInRemovesOnlyStaleRows() = runTest {
        dao.upsertAll(listOf(device(0, "Keep", "A"), device(0, "Stale", "B")))
        val keep = dao.observeAllSongs().first().first { it.title == "Keep" }
        dao.deleteDeviceSongsNotIn(listOf(keep.remoteId))
        val remaining = dao.observeAllSongs().first()
        assertEquals(1, remaining.size)
        assertEquals("Keep", remaining.first().title)
    }

    @Test
    fun successfulEmptyScanRemovesAllDeviceRows() = runTest {
        dao.upsertAll(listOf(device(0, "Gone", "A"), device(0, "Gone2", "B")))
        // Empty scan: sentinel id matches nothing, so all DEVICE rows are deleted.
        dao.deleteDeviceSongsNotIn(listOf("__no_match__"))
        assertTrue(dao.observeAllSongs().first().isEmpty())
    }

    @Test
    fun searchLocalRanksExactFirst() = runTest {
        dao.upsertAll(
            listOf(
                device(0, "Hello World", "A"),
                device(1, "Hello", "B"),
                device(2, "Say Hello", "C"),
            ),
        )
        val results = dao.searchLocal("hello", 10)
        assertEquals("Hello", results.first().title)
    }

    @Test
    fun observeLikedSongsReturnsOnlyLiked() = runTest {
        dao.upsert(device(0, "Liked", "A").copy(isLiked = true, likedAt = 5))
        dao.upsert(device(0, "Not", "B"))
        val liked = dao.observeLikedSongs().first()
        assertEquals(1, liked.size)
        assertEquals("Liked", liked.first().title)
    }
}
