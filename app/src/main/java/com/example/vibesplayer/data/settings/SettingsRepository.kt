package com.example.vibesplayer.data.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Settings from spec section 16, exposed as Flows: offlineMode, downloadWifiOnly,
 * jamendoEnabled, library sort per tab, selected Library segment, queue and
 * position, server settings.
 */
@Singleton
class SettingsRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {
    val offlineMode: Flow<Boolean> = dataStore.data.map { it[Keys.OFFLINE_MODE] ?: false }
    val downloadWifiOnly: Flow<Boolean> = dataStore.data.map { it[Keys.DOWNLOAD_WIFI_ONLY] ?: true }
    val jamendoEnabled: Flow<Boolean> = dataStore.data.map { it[Keys.JAMENDO_ENABLED] ?: true }
    val selectedLibrarySegment: Flow<Int> = dataStore.data.map { it[Keys.LIBRARY_SEGMENT] ?: 0 }
    val sortPlaylists: Flow<String> = dataStore.data.map { it[Keys.SORT_PLAYLISTS] ?: SORT_RECENT }
    val sortDownloads: Flow<String> = dataStore.data.map { it[Keys.SORT_DOWNLOADS] ?: SORT_RECENT }
    val sortLiked: Flow<String> = dataStore.data.map { it[Keys.SORT_LIKED] ?: SORT_RECENT }
    val queueSongIds: Flow<String> = dataStore.data.map { it[Keys.QUEUE_IDS] ?: "" }
    val queueIndex: Flow<Int> = dataStore.data.map { it[Keys.QUEUE_INDEX] ?: 0 }
    val queuePositionMs: Flow<Long> = dataStore.data.map { it[Keys.QUEUE_POSITION] ?: 0L }
    val queueShuffle: Flow<Boolean> = dataStore.data.map { it[Keys.QUEUE_SHUFFLE] ?: false }
    val queueRepeat: Flow<Int> = dataStore.data.map { it[Keys.QUEUE_REPEAT] ?: REPEAT_OFF }
    val serverAddress: Flow<String> = dataStore.data.map { it[Keys.SERVER_ADDRESS] ?: "" }
    val serverUsername: Flow<String> = dataStore.data.map { it[Keys.SERVER_USERNAME] ?: "" }

    suspend fun setOfflineMode(enabled: Boolean) {
        dataStore.edit { it[Keys.OFFLINE_MODE] = enabled }
    }

    suspend fun setDownloadWifiOnly(enabled: Boolean) {
        dataStore.edit { it[Keys.DOWNLOAD_WIFI_ONLY] = enabled }
    }

    suspend fun setJamendoEnabled(enabled: Boolean) {
        dataStore.edit { it[Keys.JAMENDO_ENABLED] = enabled }
    }

    suspend fun setSelectedLibrarySegment(index: Int) {
        dataStore.edit { it[Keys.LIBRARY_SEGMENT] = index }
    }

    suspend fun setSort(tab: String, sort: String) {
        dataStore.edit { it[stringPreferencesKey("sort_$tab")] = sort }
    }

    suspend fun saveQueue(ids: List<Long>, index: Int, positionMs: Long, shuffle: Boolean, repeat: Int) {
        dataStore.edit {
            it[Keys.QUEUE_IDS] = ids.joinToString(",")
            it[Keys.QUEUE_INDEX] = index
            it[Keys.QUEUE_POSITION] = positionMs
            it[Keys.QUEUE_SHUFFLE] = shuffle
            it[Keys.QUEUE_REPEAT] = repeat
        }
    }

    private object Keys {
        val OFFLINE_MODE = booleanPreferencesKey("offline_mode")
        val DOWNLOAD_WIFI_ONLY = booleanPreferencesKey("download_wifi_only")
        val JAMENDO_ENABLED = booleanPreferencesKey("jamendo_enabled")
        val LIBRARY_SEGMENT = intPreferencesKey("library_segment")
        val SORT_PLAYLISTS = stringPreferencesKey("sort_playlists")
        val SORT_DOWNLOADS = stringPreferencesKey("sort_downloads")
        val SORT_LIKED = stringPreferencesKey("sort_liked")
        val QUEUE_IDS = stringPreferencesKey("queue_ids")
        val QUEUE_INDEX = intPreferencesKey("queue_index")
        val QUEUE_POSITION = longPreferencesKey("queue_position_ms")
        val QUEUE_SHUFFLE = booleanPreferencesKey("queue_shuffle")
        val QUEUE_REPEAT = intPreferencesKey("queue_repeat")
        val SERVER_ADDRESS = stringPreferencesKey("server_address")
        val SERVER_USERNAME = stringPreferencesKey("server_username")
    }

    companion object {
        const val SORT_RECENT = "recent"
        const val SORT_NAME = "name"
        const val SORT_LARGEST = "largest"
        const val REPEAT_OFF = 0
        const val REPEAT_ALL = 1
        const val REPEAT_ONE = 2
    }
}
