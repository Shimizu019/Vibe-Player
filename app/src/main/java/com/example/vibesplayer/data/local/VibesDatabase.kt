package com.example.vibesplayer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * App database (spec section 16). The schema is exported and migrations must be
 * written by hand; there is deliberately no destructive-migration fallback.
 */
@Database(
    entities = [SongEntity::class, PlaylistEntity::class, PlaylistSongEntity::class, RecentSearchEntity::class],
    version = 1,
    exportSchema = true,
)
@TypeConverters(VibesTypeConverters::class)
abstract class VibesDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao

    abstract fun playlistDao(): PlaylistDao

    abstract fun recentSearchDao(): RecentSearchDao
}
