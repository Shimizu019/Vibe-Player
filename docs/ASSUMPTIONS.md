# Assumptions

Every assumption and deviation from the spec is recorded here.

## Project state (before Milestone 1)
- The project folder did not contain an Android project yet, so per spec section 2.7 the project was created by hand, including the Gradle wrapper (Gradle 9.8.1).
- The five approved screenshots (docs/design/) are still not in the repo. Per spec section 2.5 the spec text wins whenever a screenshot would disagree, so all Milestone 1 component work follows the written spec only. When the screenshots land, the shared components must be diffed against them element by element before Milestone 4 (screens).
- Git identity for commits is the repo owner's GitHub noreply address (Shimizu019@users.noreply.github.com), configured repo-locally.

## Milestone 1 decisions (resolved)
- **minSdk = 24** (spec ceiling): androidx.navigation:navigation-compose 2.10.2 ships AAR metadata requiring minSdk 24, which is the binding floor for this stack. The spec allows "never above 24", so 24 it is. targetSdk/compileSdk = 37 (build-tools 37.0.0).
- **AGP 9.4.1 ships built-in Kotlin**, which conflicts with the catalog's `org.jetbrains.kotlin.android` plugin. Opted out via `android.builtInKotlin=false` and `android.newDsl=false` in `gradle.properties` so the explicit Kotlin 2.4.21 plugin from the version catalog stays in charge, as required by the "no guessed versions" rule.
- **Library versions** were all verified against live Google Maven / Maven Central metadata at build time (AGP 9.4.1, Kotlin 2.4.21, KSP 2.3.12, Hilt 2.60.1, Compose BOM 2026.09.00, Navigation 2.10.2). No alpha/beta/rc versions anywhere.
- **local.properties** uses `sdk.dir=C\:/Users/...` (forward slashes, escaped drive colon): backslashes break `.properties` parsing (the original `SdkLocator` IOException), and lint's `PropertyEscape` check requires the colon escaped.
- **Debug components gallery** (spec 19.1) lives in `app/src/debug/` with its own launcher entry declared in `app/src/debug/AndroidManifest.xml`; release builds contain neither the class nor the manifest entry.
- **String resources** follow spec §17 wording. Quantity strings (`playlist_meta`, `scan_found`, `add_n_songs`, `state_downloading_percent`) are `<plurals>` because lint requires it; English keeps the spec's exact text (`"1 track"` = `<item quantity="one">`). `playlist_meta_one` was folded into the plural.
- **Warnings-only lint**: `lintDebug` passes with 0 errors. The remaining ~137 warnings are dominated by `UnusedResources` — spec §17 strings and Lucide icons intentionally pinned in Milestone 1 for milestones 2–6. They disappear as later milestones consume them.

## Milestone 2 fixes and decisions (resolved)
- **Manifest permissions** were missing entirely on the public branch; added `READ_MEDIA_AUDIO` (API 33+) and `READ_EXTERNAL_STORAGE` (`maxSdkVersion="32"`) per spec 11.2. Runtime-only handling is not sufficient because the manifest declaration is what makes a runtime request possible.
- **Null MediaStore cursor is now a failed scan**, not an empty library. `DeviceMusicScanner.scan()` throws `ScanFailureException` on a null cursor before any upsert or stale-record deletion, and re-checks `ensureActive()` after cursor iteration so a mid-scan cancellation cannot delete rows. `ScanManager` catches `CancellationException` (reset to Idle, rethrow) and other exceptions (reset to Idle) so state never sticks at `Scanning`. Stale DEVICE cleanup runs only after a complete, successful scan.
- **DISPLAY_NAME added to the projection** so the missing-title filename fallback (spec 11.2) actually fires. The deprecated `DATA` column is never read; the playable URI is built from the row `_ID` via `ContentUris.withAppendedId`.
- **`RecentSearchDao.trimToTen()`** now trims by `rowid` with deterministic ordering (`searchedAt DESC, rowid DESC`) so identical timestamps still leave exactly 10 rows; the previous `searchedAt NOT IN (...)` value-based delete could over- or under-delete on ties.
- **Mapping split for testability**: `MediaScanContract.buildSongEntity(...)` is a pure function (takes the already-built `contentUriString`) covered by plain JVM tests; `mapRow(...)` builds the URI via `ContentUris` and delegates. Android-only parts (real Cursor, `Build.VERSION`, `loadThumbnail`) are left to instrumentation tests, not mocked, per spec 19.
- **Test environment**: Room DAO tests are Android instrumentation tests in `app/src/androidTest/` using `Room.inMemoryDatabaseBuilder`; no Robolectric (not approved). AndroidX test deps verified against official Maven metadata and never guessed: `androidx.test:runner 1.7.0`, `androidx.test.ext:junit 1.3.0`, `androidx.test:core 1.7.0` (all latest/stable), plus existing `room-testing 2.8.5` and `kotlinx-coroutines-test 1.11.0`.
- **Unresolved / to confirm**: the instrumentation DAO tests require a connected device or emulator (`connectedDebugAndroidTest`). If none is available they are reported as blocked, not passed.

## Known limitations accepted for Milestone 1
- Previews cover every state of every §8 component, but there are no unit or instrumentation tests yet beyond the empty `test` task passing; real tests start in Milestone 2 (spec §19: MediaStore mapping, search ranking, etc.).
- The mini player and tab bar are wired into the shell but hidden until a queue exists (Milestone 3), per spec §8.2.
