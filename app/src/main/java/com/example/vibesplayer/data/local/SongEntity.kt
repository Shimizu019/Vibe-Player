package com.example.vibesplayer.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Song row (spec section 16). Origin + remoteId is unique so a rescan or a remote
 * refresh upserts instead of duplicating. downloadProgress is 0..100.
 */
@Entity(
    tableName = "songs",
    indices = [Index(value = ["origin", "remoteId"], unique = true)],
)
data class SongEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val origin: SongOrigin,
    val remoteId: String,
    val title: String,
    val artist: String,
    val album: String?,
    val albumId: Long?,
    val durationMs: Long,
    val year: Int,
    val license: String?,
    val licenseUrl: String?,
    val artworkUri: String?,
    val contentUri: String,
    val downloadStatus: DownloadStatus,
    val downloadProgress: Int,
    val localFilePath: String?,
    val sizeBytes: Long,
    val isLiked: Boolean,
    val likedAt: Long?,
    val addedAt: Long,
    val playCount: Int,
    val lastPlayedAt: Long?,
) {
    /**
     * A song is playable offline only when it is on the phone (DEVICE) or fully
     * downloaded (spec section 11.1). Single definition used by every layer.
     */
    val isPlayableOffline: Boolean
        get() = origin == SongOrigin.DEVICE || downloadStatus == DownloadStatus.DONE
}
