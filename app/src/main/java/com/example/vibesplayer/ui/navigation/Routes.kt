package com.example.vibesplayer.ui.navigation

import kotlinx.serialization.Serializable

@Serializable data object HomeRoute
@Serializable data object SearchRoute
@Serializable data object LibraryRoute
@Serializable data object NowPlayingRoute
@Serializable data object RecentlyPlayedRoute
@Serializable data object SettingsRoute

/** One Collection screen serves user playlists, Golden Hits, Liked, albums, artists, and eras. */
@Serializable data class CollectionRoute(val kind: String, val id: String = "")

/** Full list behind a Search "VIEW ALL" link; section is "songs", "artists", or "albums". */
@Serializable data class SearchSectionRoute(val section: String)
