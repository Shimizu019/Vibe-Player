package com.example.vibesplayer.data.local

import androidx.room.TypeConverter

/** Room stores the enums as their names so the database stays readable. */
class VibesTypeConverters {
    @TypeConverter
    fun originToString(origin: SongOrigin): String = origin.name

    @TypeConverter
    fun stringToOrigin(value: String): SongOrigin = SongOrigin.valueOf(value)

    @TypeConverter
    fun downloadStatusToString(status: DownloadStatus): String = status.name

    @TypeConverter
    fun stringToDownloadStatus(value: String): DownloadStatus = DownloadStatus.valueOf(value)
}
