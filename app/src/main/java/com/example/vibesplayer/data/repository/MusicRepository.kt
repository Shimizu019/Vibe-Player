package com.example.vibesplayer.data.repository

import com.example.vibesplayer.data.local.PlaylistDao
import com.example.vibesplayer.data.local.PlaylistTrack
import com.example.vibesplayer.data.local.PlaylistWithStats
import com.example.vibesplayer.data.local.RecentSearchEntity
import com.example.vibesplayer.data.local.SongDao
import com.example.vibesplayer.data.local.SongEntity
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

/** Data access for songs, playlists, and recent searches used by later screens. */
@Singleton
class MusicRepository @Inject constructor(
    private val songDao: SongDao,
    private val playlistDao: PlaylistDao,
    private val recentSearchDao: com.example.vibesplayer.data.local.RecentSearchDao,
    private val rules: MusicRules,
) {
    fun observeAllSongs(): Flow<List<SongEntity>> = songDao.observeAllSongs()

    fun observeSong(id: Long): Flow<SongEntity?> = songDao.observeSongById(id)

    fun observeLikedSongs(): Flow<List<SongEntity>> = songDao.observeLikedSongs()

    fun observeDownloadedSongs(): Flow<List<SongEntity>> = songDao.observeDownloadedSongs()

    fun observeRecentlyPlayed(limit: Int = 100): Flow<List<SongEntity>> =
        map(songDao.observeRecentlyPlayed()) { it.take(limit) }

    fun observeGoldenHits(): Flow<List<SongEntity>> =
        combine(
            songDao.observeGoldenHits(),
            songDao.observeGoldenHitsFallback(),
        ) { played, fallback ->
            if (played.isNotEmpty()) played else fallback
        }

    fun observeSongsByDecade(decadeStart: Int): Flow<List<SongEntity>> =
        songDao.observeSongsByDecade(decadeStart, decadeStart + 9)

    suspend fun searchLocal(query: String, limit: Int): List<SongEntity> {
        val rows = songDao.searchLocal(query.trim(), Int.MAX_VALUE)
        return rules.rankSearch(rows, query).take(limit)
    }

    fun observePlaylists(): Flow<List<PlaylistWithStats>> =
        playlistDao.observePlaylistsWithStats()

    fun observeTracks(playlistId: Long): Flow<List<PlaylistTrack>> =
        playlistDao.observeTracks(playlistId)

    fun observeRecentSearches(): Flow<List<RecentSearchEntity>> =
        recentSearchDao.observeRecentSearches()

    suspend fun saveRecentSearch(query: String, nowMs: Long) {
        val trimmed = query.trim()
        if (trimmed.isEmpty()) return
        recentSearchDao.upsert(RecentSearchEntity(trimmed, nowMs))
        recentSearchDao.trimToTen()
    }

    suspend fun upsertSong(song: SongEntity): Long = songDao.upsert(song)
}
