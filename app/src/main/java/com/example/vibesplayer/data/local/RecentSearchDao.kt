package com.example.vibesplayer.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

/**
 * Recent searches, newest first, at most 10 rows. The NOCASE primary key makes
 * an upsert replace a case-variant duplicate, keeping no duplicates ignoring case.
 */
@Dao
interface RecentSearchDao {
    @Upsert
    suspend fun upsert(search: RecentSearchEntity)

    @Query("SELECT * FROM recent_searches ORDER BY searchedAt DESC LIMIT 10")
    fun observeRecentSearches(): Flow<List<RecentSearchEntity>>

    @Query("DELETE FROM recent_searches WHERE searchedAt NOT IN (SELECT searchedAt FROM recent_searches ORDER BY searchedAt DESC LIMIT 10)")
    suspend fun trimToTen()

    @Query("DELETE FROM recent_searches WHERE query = :query COLLATE NOCASE")
    suspend fun delete(query: String)
}
