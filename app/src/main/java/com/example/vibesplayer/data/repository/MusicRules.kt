package com.example.vibesplayer.data.repository

import com.example.vibesplayer.data.local.SongEntity
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Read rules shared by every screen, kept here so the logic has exactly one home
 * and the unit tests cover it without a database.
 */
@Singleton
class MusicRules @Inject constructor() {
    /** Decade of a year, e.g. 1967 -> 1960. Year 0 means unknown (spec 11.2). */
    fun decadeOf(year: Int): Int? = if (year <= 0) null else (year / 10) * 10

    /** Era filter for the Explore Eras chips (spec 10.1.4). */
    fun inDecade(song: SongEntity, decadeStart: Int): Boolean =
        song.year >= decadeStart && song.year <= decadeStart + 9

    /**
     * Golden Hits (spec 10.1.3): 30 most-played songs; with no play history yet,
     * up to 30 songs with year 1989 or earlier, then any songs.
     */
    fun goldenHits(songs: List<SongEntity>, limit: Int = 30): List<SongEntity> {
        if (songs.any { it.playCount > 0 }) {
            return songs.filter { it.playCount > 0 }
                .sortedWith(compareByDescending<SongEntity> { it.playCount }.thenBy { it.title.lowercase() })
                .take(limit)
        }
        val comparator = compareByDescending<SongEntity> { it.playCount }.thenBy { it.title.lowercase() }
        val oldies = songs.filter { it.year in 1..1989 }.sortedWith(comparator)
        if (oldies.size >= limit) return oldies.take(limit)
        val rest = songs.filter { it !in oldies.toSet() }.sortedWith(comparator)
        return (oldies + rest).take(limit)
    }

    /**
     * Local search ranking (spec 10.3.6): exact title, title prefix, title
     * contains, then artist/album matches. Ties A to Z.
     */
    fun rankSearch(songs: List<SongEntity>, query: String): List<SongEntity> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return emptyList()
        return songs.filter {
            it.title.lowercase().contains(q) ||
                it.artist.lowercase().contains(q) ||
                (it.album?.lowercase()?.contains(q) == true)
        }.sortedWith(
            compareBy<SongEntity> { song ->
                val title = song.title.lowercase()
                when {
                    title == q -> 0
                    title.startsWith(q) -> 1
                    title.contains(q) -> 2
                    else -> 3
                }
            }.thenBy { it.title.lowercase() },
        )
    }
}
