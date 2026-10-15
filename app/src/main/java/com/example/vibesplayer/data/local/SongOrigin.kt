package com.example.vibesplayer.data.local

/** Song origin (spec section 11.1). DEVICE is found on the phone; JAMENDO and SERVER are remote. */
enum class SongOrigin {
    DEVICE,
    JAMENDO,
    SERVER,
}

/** Download state of a remote song (spec section 13). */
enum class DownloadStatus {
    NONE,
    QUEUED,
    DOWNLOADING,
    DONE,
    FAILED,
}
