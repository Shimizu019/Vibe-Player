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
class RecentSearchDaoTest {
    private lateinit var db: VibesDatabase
    private lateinit var dao: RecentSearchDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            VibesDatabase::class.java,
        ).allowMainThreadQueries().build()
        dao = db.recentSearchDao()
    }

    @After
    fun tearDown() = db.close()

    @Test
    fun upsertReplacesCaseVariantDuplicate() = runTest {
        dao.upsert(RecentSearchEntity("Jazz", 1))
        dao.upsert(RecentSearchEntity("jazz", 2)) // same query ignoring case
        val all = dao.observeRecentSearches().first()
        assertEquals(1, all.size)
        assertEquals(2L, all.first().searchedAt) // timestamp updated to latest
    }

    @Test
    fun trimToTenKeepsTenWhenTimestampsAreIdentical() = runTest {
        // All 12 rows share the exact same timestamp. A value-based trim would be
        // ambiguous; row-identity ordering must still leave exactly 10.
        repeat(12) { i -> dao.upsert(RecentSearchEntity("q$i", 1_000)) }
        dao.trimToTen()
        assertEquals(10, dao.observeRecentSearches().first().size)
    }

    @Test
    fun trimToTenKeepsTheTenNewest() = runTest {
        for (i in 1..12) dao.upsert(RecentSearchEntity("q$i", i.toLong()))
        dao.trimToTen()
        val kept = dao.observeRecentSearches().first().map { it.query }.toSet()
        // Newest ten by searchedAt are q12..q3.
        assertEquals(10, kept.size)
        assertTrue(kept.contains("q12"))
        assertTrue(kept.contains("q3"))
        assertTrue(!kept.contains("q1"))
        assertTrue(!kept.contains("q2"))
    }

    @Test
    fun deleteRemovesCaseInsensitively() = runTest {
        dao.upsert(RecentSearchEntity("Rock", 1))
        dao.delete("rock")
        assertTrue(dao.observeRecentSearches().first().isEmpty())
    }
}
