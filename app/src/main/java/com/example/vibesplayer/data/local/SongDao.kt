package com.example.vibesplayer.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

/**
 * Song queries for the later screens. Ranking and ordering follow the spec so the
 * UI layer never re-sorts: exact title match first, then prefix, then contains,
 * then artist/album matches, ties A to Z (spec section 10.3).
 */
@Dao
interface SongDao {
    @Upsert
    suspend fun upsert(song: SongEntity): Long

    @Upsert
    suspend fun upsertAll(songs: List<SongEntity>)

    @Query("SELECT * FROM songs WHERE id = :id")
    suspend fun songById(id: Long): SongEntity?

    @Query("SELECT * FROM songs WHERE id = :id")
    fun observeSongById(id: Long): Flow<SongEntity?>

    @Query("SELECT * FROM songs ORDER BY title COLLATE NOCASE ASC")
    fun observeAllSongs(): Flow<List<SongEntity>>

    @Query("SELECT * FROM songs WHERE isLiked = 1 ORDER BY likedAt DESC")
    fun observeLikedSongs(): Flow<List<SongEntity>>

    @Query("SELECT * FROM songs WHERE downloadStatus = 'DONE' ORDER BY title COLLATE NOCASE ASC")
    fun observeDownloadedSongs(): Flow<List<SongEntity>>

    /**
     * Recently played, newest first, each song once (spec section 10.1).
     * The UI keeps at most 100 rows (RecentlyPlayed screen) or 10 (Home).
     */
    @Query("SELECT * FROM songs WHERE lastPlayedAt IS NOT NULL ORDER BY lastPlayedAt DESC")
    fun observeRecentlyPlayed(): Flow<List<SongEntity>>

    /**
     * Golden Hits (spec section 10.1): the 30 most-played songs. When there is no
     * play history yet the fallback query below picks songs with year <= 1989
     * first, then anything, up to 30.
     */
    @Query("SELECT * FROM songs WHERE playCount > 0 ORDER BY playCount DESC, title COLLATE NOCASE ASC LIMIT 30")
    fun observeGoldenHits(): Flow<List<SongEntity>>

    @Query(
        "SELECT * FROM songs ORDER BY " +
            "CASE WHEN year > 0 AND year <= 1989 THEN 0 ELSE 1 END, " +
            "playCount DESC, title COLLATE NOCASE ASC LIMIT 30",
    )
    fun observeGoldenHitsFallback(): Flow<List<SongEntity>>

    /** Songs from one decade, most played first, then title A to Z (spec 10.1.4). */
    @Query(
        "SELECT * FROM songs WHERE year >= :decadeStart AND year <= :decadeEnd " +
            "ORDER BY playCount DESC, title COLLATE NOCASE ASC",
    )
    fun observeSongsByDecade(decadeStart: Int, decadeEnd: Int): Flow<List<SongEntity>>

    /**
     * Local search with spec 10.3 ranking: exact title, title prefix, title
     * contains, then artist or album contains. Ties A to Z. Limit applied by caller.
     */
    @Query(
        "SELECT * FROM songs WHERE " +
            "title LIKE '%' || :query || '%' COLLATE NOCASE " +
            "OR artist LIKE '%' || :query || '%' COLLATE NOCASE " +
            "OR album LIKE '%' || :query || '%' COLLATE NOCASE " +
            "ORDER BY " +
            "CASE WHEN lower(title) = lower(:query) THEN 0 " +
            "WHEN lower(title) LIKE lower(:query) || '%' THEN 1 " +
            "WHEN lower(title) LIKE '%' || lower(:query) || '%' THEN 2 " +
            "ELSE 3 END, " +
            "title COLLATE NOCASE ASC LIMIT :limit",
    )
    suspend fun searchLocal(query: String, limit: Int): List<SongEntity>

    @Query("SELECT * FROM songs WHERE origin = 'DEVICE'")
    suspend fun deviceSongs(): List<SongEntity>

    @Query("DELETE FROM songs WHERE origin = 'DEVICE' AND remoteId NOT IN (:keepIds)")
    suspend fun deleteDeviceSongsNotIn(keepIds: List<String>)

    @Query("DELETE FROM songs WHERE id = :id")
    suspend fun deleteById(id: Long)
}
