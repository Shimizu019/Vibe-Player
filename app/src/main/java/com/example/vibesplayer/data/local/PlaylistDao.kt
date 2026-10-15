package com.example.vibesplayer.data.local

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

/** Playlist header with its track count and total duration for Library rows. */
data class PlaylistWithStats(
    val id: Long,
    val name: String,
    val createdAt: Long,
    val trackCount: Int,
    val totalDurationMs: Long,
)

/** One track of a playlist, joined with its song row. */
data class PlaylistTrack(
    val playlistId: Long,
    val songId: Long,
    val position: Int,
    @Embedded
    val song: SongEntity,
)

@Dao
interface PlaylistDao {
    @Upsert
    suspend fun upsertPlaylist(playlist: PlaylistEntity): Long

    @Query("SELECT * FROM playlists WHERE id = :id")
    suspend fun playlistById(id: Long): PlaylistEntity?

    @Query("SELECT * FROM playlists ORDER BY createdAt DESC")
    fun observePlaylists(): Flow<List<PlaylistEntity>>

    @Query(
        "SELECT p.id AS id, p.name AS name, p.createdAt AS createdAt, " +
            "COUNT(ps.songId) AS trackCount, " +
            "COALESCE(SUM(s.durationMs), 0) AS totalDurationMs " +
            "FROM playlists p LEFT JOIN playlist_songs ps ON ps.playlistId = p.id " +
            "LEFT JOIN songs s ON s.id = ps.songId " +
            "GROUP BY p.id ORDER BY p.createdAt DESC",
    )
    fun observePlaylistsWithStats(): Flow<List<PlaylistWithStats>>

    @Transaction
    @Query(
        "SELECT ps.playlistId AS playlistId, ps.songId AS songId, " +
            "ps.position AS position, s.* FROM playlist_songs ps " +
            "JOIN songs s ON s.id = ps.songId " +
            "WHERE ps.playlistId = :playlistId ORDER BY ps.position ASC",
    )
    fun observeTracks(playlistId: Long): Flow<List<PlaylistTrack>>

    @Upsert
    suspend fun upsertMembership(row: PlaylistSongEntity)

    @Query("DELETE FROM playlist_songs WHERE playlistId = :playlistId AND songId = :songId")
    suspend fun removeFromPlaylist(playlistId: Long, songId: Long)

    @Query("DELETE FROM playlists WHERE id = :id")
    suspend fun deletePlaylist(id: Long)

    @Query("UPDATE playlists SET name = :name WHERE id = :id")
    suspend fun renamePlaylist(id: Long, name: String)
}
