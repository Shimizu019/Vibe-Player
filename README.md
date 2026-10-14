# Vibes Player 🎵

An Android music player for songs only — online streaming, offline downloads, and playback of music already stored on your phone. Inspired by Spotify, with a warm espresso-and-gold design.

> **Status:** Milestone 1 in progress — project scaffolding, Sleeve theme, navigation shell, and §8 shared components are landing commit by commit. Screen content, data, and playback arrive in Milestones 2–6.

---

## Overview

**Vibes Player** (`com.example.vibesplayer`) plays three kinds of songs:

| Source | Description | Offline? |
|---|---|---|
| `ON_DEVICE` | Found on the phone by the local MediaStore scan | ✅ Plays offline, no download needed |
| `DOWNLOADED` | A streaming song saved to the app's private storage | ✅ Plays offline |
| `STREAM` | A remote song from the online catalog | ❌ Needs a connection, and Offline mode off |

All UI text is in English only.

## Features

- **Home** — connection status ("Online" / "Offline mode" / "No connection"), notification bell, "Featured Playlist" hero card (Golden Hits), "Explore Eras" decade chips (1950s–1980s) that filter the list, and a "Recently Played" list.
- **Now Playing** — a rotating vinyl record (~3.6s per turn) with a gold ring showing download progress, `DOWNLOADED` / `ON THIS PHONE` badges, seekable progress bar, shuffle / previous / play-pause / next / repeat (Off → All → One), and Lyrics / Queue / Devices sheets.
- **Search** — debounced results (300ms) grouped into Songs, Artists, and Albums; recent-search chips stored in Room (up to 10); per-song download buttons; searches both on-device songs and the remote catalog.
- **Playlist** — large centered cover, "N songs • Xh Ym", play / shuffle / download-all / add-songs actions, and rich track-row states (downloaded, currently playing, online-only while offline, on-device).
- **Library** — "Your Library" with a create-playlist dialog, a Playlists / Downloads / Liked segmented control, a local-storage usage bar (app downloads vs. phone total via `StatFs`), and a sort row with grid/list toggle.
- **Song options menu** — Play next, Add to queue, Add to playlist, Add to Liked, Download, Remove download, Share.
- **Offline mode** — a DataStore-backed switch that hides `STREAM` songs in Search/Recently Played, dims them as "Online only" in playlists, and pauses streaming with a message if the connection drops.
- **Local scan** — `READ_MEDIA_AUDIO` (Android 13+) or `READ_EXTERNAL_STORAGE` (older), an explanation screen before the system prompt, a `ContentObserver` for live updates, and artwork via `loadThumbnail` on Android 10+.

## Tech Stack

- **Language / UI:** Kotlin, Jetpack Compose, Material 3 (customized theme)
- **SDK:** Min SDK 24, target/compile SDK 37 (latest stable; see `docs/ASSUMPTIONS.md` for the minSdk decision)
- **Playback:** Media3 ExoPlayer + `MediaSessionService` (notification & lock-screen controls)
- **Data:** Room (database), DataStore (settings)
- **DI:** Hilt
- **Async:** Coroutines + Flow
- **Images:** Coil · **Downloads:** OkHttp
- **Navigation:** Navigation Compose
- **Build:** Gradle version catalog (`gradle/libs.versions.toml`), latest stable library versions

## Design Tokens

| Token | Value | Usage |
|---|---|---|
| Background | `#1E1915` | Espresso screen background |
| Surface | `#2A241E` | Rows, chips, mini player |
| Primary text | `#F1E6CF` | Sleeve |
| Secondary text | `#D6C8AE` | Muted sand |
| Accent gold | `#D4A73A` | Play button, progress bars, active tab, download ring, selected chips |
| Oxblood | `#7A2E2E` | Small tints and badges |

- **Fonts:** Fraunces SemiBold (headings) and Instrument Sans (body/UI) — both open source via Google Fonts, bundled in `res/font`.
- **Icons:** Lucide (ISC) or Material Symbols (Apache 2.0) — one style everywhere, 24dp.
- **Radii:** 4dp for cover art; fully rounded / 16dp for chips, buttons, and the mini player. No other radii.
- **Spacing:** 20dp screen margins, ≥48dp touch targets.
- Labels are exact, including uppercase "NOW PLAYING", "DOWNLOADED", "VIEW ALL", and "LOCAL STORAGE".

## Project Structure (planned)

```
app/
└── src/main/
    ├── java/com/example/vibesplayer/
    │   ├── data/          # Room entities, DAOs, repositories, DataStore
    │   ├── download/      # OkHttp download manager
    │   ├── local/         # MediaStore scan + permissions
    │   ├── playback/      # Media3 service, notification, controls
    │   ├── remote/        # RemoteMusicSource + FakeRemoteMusicSource
    │   ├── ui/            # Compose screens, theme, components
    │   └── di/            # Hilt modules
    └── res/
        ├── font/          # Fraunces, Instrument Sans
        └── ...            # drawables, strings ("Vibes Player")
```

## Milestones

1. **Project setup** — Gradle, version catalog, Hilt, theme, fonts, icons, navigation shell with the tab bar and empty screens.
2. **Data layer** — Room entities, DAOs, repositories, MediaStore scan with permissions. Unit tests for mapping and source rules.
3. **Playback** — Media3 service, mini player, Now Playing with the rotating record and controls.
4. **Screens** — Home, Search, Playlist, Library, song options menu, and the add-songs picker.
5. **Downloads & offline** — download manager, progress ring, Offline mode switch, connection handling, error messages.
6. **Polish** — animations, accessibility checks, empty states.

Each milestone ends with `./gradlew assembleDebug` passing before moving on.

## Deliverables

- App builds with `./gradlew assembleDebug` and runs on an emulator.
- Unit tests pass with `./gradlew test`.
- This README: setup steps, how to add music on an emulator, assumptions, and the streaming licensing note.
- A final list of every screen and any part of the design that could not be matched, with reasons.

## Setup (coming soon)

Setup instructions, emulator music-loading steps, and the **Assumptions** section will be filled in as development begins.

## Licensing Note for Streaming

A real launch needs **licensed music**. Development uses only royalty-free or public-domain audio and placeholder URLs via a fake remote source (`FakeRemoteMusicSource`). Do not add commercial streams. The production network source will read its base URL from `BuildConfig.MUSIC_API_BASE_URL`.

## Icons & Fonts Licensing

- **Lucide icons** — ISC License
- **Material Symbols** — Apache License 2.0
- **Fraunces**, **Instrument Sans** — SIL Open Font License (Google Fonts)

---

*App name: **Vibes Player** · Package: `com.example.vibesplayer`*
