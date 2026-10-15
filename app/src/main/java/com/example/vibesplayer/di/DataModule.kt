package com.example.vibesplayer.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.example.vibesplayer.data.local.RecentSearchDao
import com.example.vibesplayer.data.local.SongDao
import com.example.vibesplayer.data.local.VibesDatabase
import com.example.vibesplayer.data.local.PlaylistDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATABASE_NAME = "vibes.db"
private const val SETTINGS_NAME = "vibes_settings"

/** Provides the Room database, its DAOs, and the DataStore file. */
@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): VibesDatabase =
        Room.databaseBuilder(context, VibesDatabase::class.java, DATABASE_NAME).build()

    @Provides
    fun provideSongDao(db: VibesDatabase): SongDao = db.songDao()

    @Provides
    fun providePlaylistDao(db: VibesDatabase): PlaylistDao = db.playlistDao()

    @Provides
    fun provideRecentSearchDao(db: VibesDatabase): RecentSearchDao = db.recentSearchDao()

    @Provides
    @Singleton
    fun provideSettingsStore(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(SETTINGS_NAME) },
        )
}
