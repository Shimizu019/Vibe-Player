# VIBES PLAYER: FULL BUILD SPEC

## VIBES Player rules
- The full spec is docs/VIBES_PLAYER_SPEC.md. The approved design screenshots are in docs/design/. Re-read the sections for the current milestone before writing code.
- Follow the spec exactly. Do not change colors, fonts, sizes, or text. If something in the spec seems wrong, ask me before you deviate.
- Build one milestone at a time. After each one, run ./gradlew assembleDebug, ./gradlew test, and ./gradlew lintDebug, fix every error, then stop and summarize in 5 lines or fewer.
- Never guess library versions. Never add YouTube or any code that extracts or downloads audio from streaming services.
- Record every assumption in docs/ASSUMPTIONS.md.

## 0. TOP RULES (read first, they matter most)
1. The five screenshots in docs/design/ are the approved design. Build that UI exactly. Section 3 lists flaws in them that you must NOT copy.
2. Work in the milestones of section 20, one at a time, and stop after each.
3. A song is playable offline only if it is on the phone (DEVICE) or fully downloaded. Offline mode and "no connection" both hide or dim every other song (section 14).
4. The mini player must show cover, title, artist, previous, play/pause, and next inside its card at every screen width (section 8.2).
5. Use the exact sizes in this spec: 24 dp screen margins, 48 dp covers, corner radii of 4 dp, 8 dp, and full only.
6. Sources are limited to music on the phone, Jamendo, and the user's own Subsonic-compatible server. Never add YouTube or any code that extracts or downloads audio from streaming services.
7. Support old Android versions (section 4). Never use an API newer than minSdk without a version check and a fallback.
8. Never guess library versions. Never leave TODOs, empty functions, or fake success paths.
9. Before you finish each screen, compare it with its screenshot element by element (position, size, color, text) and fix every difference.
10. Do not add screens or features that are not in this spec. If something seems missing, tell me.

## 1. GOAL
Build "Vibes Player": a personal-use, songs-only music app like Spotify. It plays music already on the phone, streams songs from online sources, and downloads songs for offline playback. Online and offline are both first-class: the user must always know which mode the app is in.
Tagline: "Old but Gold". Audience: one person on one Android phone who loves classic songs from the 1950s to the 1980s.

## 2. HOW TO WORK
1. Start in Plan mode. Read this whole spec and view all five screenshots. Write a plan: file tree, dependencies with versions, assumptions, risks. Wait for my approval.
2. Build one milestone at a time (section 20). After each: run ./gradlew assembleDebug, ./gradlew test, ./gradlew lintDebug. Fix every error. Then stop and give me a summary of 5 lines or fewer. Continue only when I say "next".
3. Never guess library versions. Look up the latest stable versions (Google Maven, Maven Central). Do not use alpha, beta, or rc versions unless no stable version exists. If a version does not resolve, use the previous stable one.
4. Ask me a question only when you are truly blocked. Otherwise choose the safest default and record it in docs/ASSUMPTIONS.md.
5. Where this spec and a screenshot disagree, this spec wins. Where this spec is silent, follow the screenshot, then Material 3 defaults adapted to this style.
6. Keep code simple: one ViewModel per screen, immutable UiState, one-way data flow, small files. Comment only what is not obvious.
7. The project already exists (Android Studio Empty Activity template). Keep its Gradle wrapper. If no project exists, create one by hand, including the wrapper.

## 3. READING THE SCREENSHOTS
- Each screenshot is 750 px wide = 375 dp (2x). Divide pixel measurements by 2 to get dp. All sizes in this spec are already in dp and sp.
- Ignore the white strip at the right edge of screenshots 1 and 3, and any system status or navigation bar.
- The photos and illustrations in the screenshots are placeholders. Do not copy them. Follow section 12.

Flaws in the screenshots. Do NOT copy these:
a. Mini player: screenshots 1, 3, and 5 cut off or hide the next button. Use screenshot 4 as the reference: previous, play/pause, and next all fit inside the card.
b. The Devices icon (screenshot 2) and the offline-only row icon (screenshot 4) are placeholder glyphs (dashed circle with a question mark). Use the icons named in this spec.
c. Search (screenshot 3) shows Recent Searches and results at the same time. Build two states: empty field shows Recent Searches, typed text shows results.
d. Home (screenshot 1) shows the 1950s chip selected while the list shows other decades. Start with no chip selected.
e. Horizontal rows are clipped at different places in screenshots 1 and 3. Make every horizontal row scroll edge to edge, with 24 dp of start padding.
f. In screenshot 4 the active song's "..." button is replaced by an equalizer. Keep that, but make long-press on the row open the song menu so the menu is always reachable.

## 4. DEVICE SUPPORT (any Android phone or tablet)
- minSdk 21 (Android 5.0) if every library in the stack supports it. If a library needs more, use the lowest minSdk the stack allows, never above 24, and write the reason in docs/ASSUMPTIONS.md. compileSdk and targetSdk: latest stable.
- Phones and tablets, portrait and landscape, 320 dp to 1280 dp wide. Use WindowSizeClass.
  - Compact width: layouts exactly as in the screenshots.
  - Landscape, or Medium/Expanded width: center content with a max width of 640 dp. Now Playing becomes two columns (record on the left; title, progress, controls, and bottom row on the right). On Expanded width, Library shows the list on the left (360 dp) and the open collection on the right.
- Edge-to-edge on every Android version. Use WindowInsets for the status bar, navigation bar, cutouts, and keyboard. Gesture navigation and three-button navigation must both look right.
- Dark theme only. No dynamic color. Set the window background to #1E1915 so there is no white flash at launch. Use the AndroidX core-splashscreen library with that background and the record logo.
- Enable core library desugaring so java.time and other newer APIs work on old versions.
- Guard every API newer than minSdk with Build.VERSION.SDK_INT and give a fallback. Never crash on an old device.
- Text must scale to 200% without clipping. Never give text containers a fixed height; use minimum heights.
- Use dp and sp only.
- App icon: adaptive icon (gold record on #1E1915) with a monochrome layer for Android 13 themed icons, plus PNG fallbacks for API 21 to 25.
- Enable predictive back (android:enableOnBackInvokedCallback="true").

## 5. TECH STACK
- Kotlin 2.x, Jetpack Compose with the Compose compiler Gradle plugin, Material 3, Gradle Kotlin DSL, version catalog (gradle/libs.versions.toml). One module: app.
- Hilt (KSP, not kapt). Room (KSP). DataStore (Preferences).
- Media3: ExoPlayer and Session. Playback runs in a MediaSessionService.
- WorkManager (with Hilt integration) for background downloads.
- Navigation Compose with type-safe routes.
- Kotlin Coroutines and Flow. kotlinx.serialization for JSON.
- OkHttp for network and downloads. Coil 3 for images.
- Tests: JUnit4, MockK, kotlinx-coroutines-test, Turbine, Compose UI test.
- Do not use Accompanist, Retrofit, kapt, LiveData, or deprecated APIs. Use built-in Compose APIs (FlowRow, PullToRefreshBox, ModalBottomSheet).
- Package: com.example.vibesplayer. App name in strings.xml: "Vibes Player". Put all UI text in strings.xml (escape apostrophes).

## 6. STRUCTURE
com.example.vibesplayer
  ui/theme, ui/components, ui/home, ui/search, ui/nowplaying, ui/collection, ui/library, ui/settings, ui/navigation
  data/local (Room), data/device (MediaStore scanner), data/remote (RemoteMusicSource, jamendo, subsonic, fake), data/download, data/settings, data/repository
  playback (service and player controller), di, util
Each screen has a Screen composable, a ViewModel, a UiState, and a UiEvent.

## 7. DESIGN SYSTEM
### 7.1 Colors (define as tokens, use no raw hex in screens)
  Espresso #1E1915  screen background, tab bar, selected segment
  Surface  #2A241E  mini player, chips, search field, circle buttons, sheets, dialogs, snackbar
  Border   #3A3229  1 dp outlines (mini player, unselected era chips, sheet handle)
  Divider  #2E2822  list dividers, progress and storage tracks
  Sleeve   #F1E6CF  primary text and icons, the mini player play/pause circle
  Sand     #D6C8AE  secondary text, inactive controls
  Gold     #D4A73A  accent: play buttons, progress, selected tab and chip, links, download ring
  Oxblood  #7A2E2E  hero card background, placeholder artwork
  Disc     #0F0D0B  vinyl disc
  Danger   #E57C6B  destructive actions only (Delete, Remove)
Rules: text on Gold uses Espresso. Gold text only on Espresso or Surface. Dimmed rows use 50% opacity. No gradients, no shadows, no elevation: separate layers with Surface and Border.

### 7.2 Fonts (both open source)
- Fraunces: Regular 400 and SemiBold 600. Instrument Sans: Regular 400 and Medium 500.
- Bundle STATIC files in res/font: fraunces_regular.ttf, fraunces_semibold.ttf, instrument_sans_regular.ttf, instrument_sans_medium.ttf. (Variable fonts do not work before Android 8.)
- Get them from the official font repositories or the google/fonts repository. If only variable files exist, create static instances with fontTools (fonttools varLib.instancer), pinning every axis (wght 400 or 600, opsz 72, SOFT 0, WONK 0 for Fraunces).
- If you cannot get the fonts, use FontFamily.Serif and SansSerif, note it in docs/ASSUMPTIONS.md, and continue.

### 7.3 Type scale (size/line height in sp)
  titleXL   Fraunces SemiBold 32/38  Home title "Vibes Player", collection title
  titleL    Fraunces SemiBold 30/36  Home hero title
  titleM    Fraunces SemiBold 28/34  "Your Library", Now Playing song title, screen titles
  heading   Fraunces Regular 22/28   Explore Eras, Recently Played, Recent Searches, Songs, Artists, Albums, sheet and dialog titles
  artistL   Instrument Sans Regular 20/26  artist on Now Playing
  rowTitle  Instrument Sans Medium 18/24   song, playlist, and album titles; mini player title; segmented control
  input     Instrument Sans Regular 18/24  search field text
  body      Instrument Sans Regular 16/22  chips, hero label, status text, artist names under avatars, collection meta line, sort label
  bodyMed   Instrument Sans Medium 16/22   buttons, "See All", storage amount
  meta      Instrument Sans Regular 14/20  song sublines, times, tab labels, bottom-row labels
  label     Instrument Sans Medium 14/20, +1.2 sp tracking, ALL CAPS  DOWNLOADED, ON THIS PHONE, VIEW ALL
  labelL    Instrument Sans Medium 16/22, +1.5 sp tracking, ALL CAPS  NOW PLAYING, LOCAL STORAGE
Store text in normal case in strings.xml and apply uppercase() in the UI for the label styles only. No other text is uppercase.

### 7.4 Shapes: only three radii.
  4 dp: cover art, hero card, search field, recent-search chips, album art, selected segment.
  8 dp: mini player, segmented control container, sheets (top corners), dialogs, snackbar.
  Full: circle buttons, era chips, primary buttons, badge, avatars, record, progress bars.

### 7.5 Spacing (4 dp grid)
- Screen side margin 24 dp. Mini player side inset 16 dp.
- Gap between blocks 32 dp unless stated. Gap between sections 40 dp. Heading to its content 16 dp.
- Song row: cover 48 dp, 16 dp gap, text. Row height 72 dp (12 dp vertical padding) on Home and Search; 80 dp (16 dp vertical padding) in collections and Library.
- Touch targets at least 48 dp x 48 dp. If a visual element is smaller (chips, small buttons), extend its tap area to 48 dp.
- Trailing icon buttons are 48 dp and end 4 dp from the screen edge. Top-bar buttons end 8 dp from the screen edge.

### 7.6 Icons
- Lucide icons (ISC license), 24 dp, 2 dp stroke, rounded caps. Use the official SVGs from the Lucide repository and convert them to Android vector drawables (for example with the svg2vectordrawable npm tool). Do not draw icons from memory. If conversion fails, use Material Symbols Outlined (Apache 2.0) instead, but use one set everywhere.
- Needed: bell, house, search, layers, chevron-down, chevron-left, chevron-right, ellipsis, ellipsis-vertical, shuffle, skip-back, skip-forward, play, pause, repeat, repeat-1, quote, list, smartphone, download, arrow-down, plus, circle-plus, circle-check, circle-x, circle-alert, x, arrow-down-wide-narrow, layout-grid, cloud-off, audio-lines, list-start, list-end, list-plus, heart, trash-2, share-2, info, settings, music, clock, rotate-ccw, wifi-off, square.
- Draw these filled (fill = current color): play, pause, skip-back, skip-forward, circle-check (Gold in lists), and house when selected.

### 7.7 Motion
- Record rotation, Now Playing slide, equalizer, and progress only. No decorative animation, no shimmer.
- Screen changes: 250 ms horizontal slide. Tab changes: 150 ms cross-fade. Now Playing: 300 ms slide up, FastOutSlowIn, and slide down on close. Press feedback: ripple only.
- If the system animator duration scale is 0, turn off all animation, including record rotation.

## 8. SHARED COMPONENTS (build each once, with @Preview for every state)
### 8.1 SongRow
  Layout: cover 48 dp | 16 dp | text column | trailing 48 dp button.
  Title: rowTitle, Sleeve, one line, ellipsis. Subline: meta, Sand, one line, ellipsis.
  Subline format: "Artist" or "Artist • Year". Leave out " • Year" when the year is unknown.
  Tap: play this song within its list. Long-press: open the song menu. Trailing "...": ellipsis-vertical.
  States:
  - Playing: title in Gold. Trailing is an animated 3-bar equalizer (audio-lines style) in Gold; bars move only while audio plays.
  - Downloaded (collections and Library): subline starts with a 16 dp Gold circle-check, then the artist.
  - On this phone (collections): subline starts with a 14 dp smartphone icon in Sand, then the artist.
  - Online only (not downloaded while Offline mode is on or there is no connection): the whole row at 50% opacity. Subline: cloud-off icon, then "Artist • Online only". Tap shows the matching message from section 17.
  - Search variant: the trailing slot shows the download state instead of "...": idle = download icon; queued = clock; downloading = 24 dp Gold progress ring (tap cancels); downloaded = Gold circle-check (tap opens the song menu); failed = rotate-ccw (tap retries); download not allowed = download icon at 38% opacity (tap shows the message in section 17). Songs on this phone show "...".

### 8.2 MiniPlayer
  Card: screen width minus 32 dp, centered, 70 dp high, Surface fill, 1 dp Border, 8 dp radius. It sits 8 dp above the tab bar. Hidden when the queue is empty and on Now Playing.
  Content (16 dp start padding, 8 dp end padding): cover 40 dp (4 dp radius) | 12 dp | title (rowTitle) over artist (meta), taking the remaining width, one line each with ellipsis | previous (skip-back, 24 dp icon, 48 dp button) | play/pause (44 dp Sleeve circle with an Espresso icon) | next (skip-forward, 24 dp icon, 48 dp button).
  Bottom edge: a 3 dp Gold progress line, clipped to the card shape, no track.
  Tapping the card (not the buttons) opens Now Playing.
  All three buttons stay visible at 320 dp width. The text column shrinks and ellipsizes first.
  Accessibility label: "Now playing: [title] by [artist]. Double tap to open the player."

### 8.3 BottomTabBar
  Espresso background, 1 dp Divider on top, 64 dp content height plus the bottom system inset. Three items with Arrangement.SpaceBetween and 32 dp horizontal padding: Home (house), Search (search), Library (layers). Each: 24 dp icon above a 14 sp label, 4 dp apart, 48 dp minimum target. Selected = Gold icon and label. Unselected = Sleeve icon, Sand label. Mark the selected item with semantics selected = true.

### 8.4 SectionHeader: heading style at the start. Optional action at the end: "See All" (bodyMed, Gold) on Home, "VIEW ALL" (label style, Gold) on Search.

### 8.5 EraChip: 40 dp high, 24 dp horizontal padding, full radius, bodyMed text. Selected: Gold fill, Espresso text. Unselected: Surface fill, 1 dp Border, Sleeve text. 12 dp gaps.

### 8.6 RecentSearchChip: 36 dp high, 4 dp radius, Surface fill, body text in Sleeve, 16 dp horizontal padding, then an 16 dp "x" icon in Sand 8 dp after the text. Chips wrap (FlowRow) with 8 dp gaps. Tapping the chip runs the search; tapping the x deletes it.

### 8.7 SegmentedControl: 48 dp high, Surface, 8 dp radius, 4 dp inner padding, three equal segments with rowTitle text. Selected segment: Espresso fill, 4 dp radius, Gold text. Unselected: Sleeve text. The selection slides in 150 ms.

### 8.8 DownloadButton (collections): 52 dp Surface circle, 24 dp icon. Not downloaded: arrow-down in Sleeve. Downloading: Gold progress ring around the circle (done/total) and a square icon. All downloaded: Gold circle-check.

### 8.9 Dialogs and sheets: Surface fill, 8 dp radius, heading style titles. Text buttons: "Cancel" in Sand, the main action in Gold. Sheets use ModalBottomSheet with a 32 x 4 dp Border-colored handle.

### 8.10 Snackbar: Surface, 1 dp Border, 8 dp radius, body text in Sleeve, action label in Gold. Always shown above the mini player and tab bar (offset by their heights plus 8 dp). Dismiss after 4 s (6 s with an action). One at a time.

### 8.11 EmptyState: centered column: 48 dp Sand icon, heading, body text in Sand (max 280 dp wide), optional primary button (full radius, 48 dp high, Gold fill, Espresso bodyMed text).

### 8.12 Skeleton: static Surface blocks that match the real layout. No shimmer.

### 8.13 Artwork: see section 12.

## 9. NAVIGATION
- Three tabs: Home, Search, Library. Each has its own back stack, saved and restored when switching. Tapping the selected tab scrolls its list to the top.
- Destinations: Home, Search, Library, Collection (user playlist, Golden Hits, Liked songs, album, artist, era), SearchSection ("VIEW ALL"), RecentlyPlayed, Settings. NowPlaying is a full-screen destination above everything.
- The tab bar and mini player show everywhere except Now Playing.
- The selected tab is the tab that owns the current back stack. A playlist opened from Home keeps Home selected.
- Now Playing closes with the chevron, the system back button or gesture, or a downward swipe.
- Back on a tab root goes to Home, then exits. Sheets and dialogs close first.

## 10. SCREENS
### 10.1 HOME (screenshot 01)
LazyColumn with 24 dp side margins, below the status bar. Bottom padding clears the mini player and tab bar.
1. Top row (56 dp high). Start: status text (body, Sand): "Online" (connected, Offline mode off), "Offline mode" (switch on), "No connection" (network lost, switch off). Tapping it opens the Offline sheet (10.6). End: bell icon button; it opens a small sheet with the heading "Notifications" and the text "No notifications yet."
2. Title "Vibes Player": titleXL, Gold.
3. Hero card, 16 dp below the title: full content width, 224 dp high on a 375 dp screen (aspect ratio about 1.46, max height 280 dp), 4 dp radius, Oxblood background with a faint record drawing (section 12). Bottom-start, 24 dp inset: "Featured Playlist" (body, Sand) above "Golden Hits" (titleL, Sleeve). Top-end, 24 dp inset: a 48 dp Gold circle button with an Espresso play icon, labelled "Play Golden Hits". Tapping the card opens Golden Hits. Tapping the button plays Golden Hits from the first song with shuffle off.
   Golden Hits is a built-in playlist: the 30 most-played songs. Until there is play history, use up to 30 songs with year 1989 or earlier, then any songs. Hide the hero when the library has no songs.
4. "Explore Eras" section (40 dp above): heading, 16 dp, then a LazyRow of EraChips: 1950s, 1960s, 1970s, 1980s. Start padding 24 dp; the row scrolls to the screen edge. No chip is selected at first. Tap selects (single select); tapping again clears.
   While a chip is selected, it replaces the "Recently Played" section with a section titled "Songs from the 1950s" (use the chosen decade): up to 10 songs from the whole library with a year in that decade, most played first, then title A to Z. "See All" opens a Collection of that era. If none: use the era empty state in section 17.
5. "Recently Played" section (40 dp above): heading with "See All" (opens RecentlyPlayed: back arrow, title "Recently Played", a list of up to 100 songs). Up to 10 SongRows (72 dp), newest first, each song once.
6. Offline: songs that are not playable offline are hidden (not dimmed) in sections 4 and 5.
7. Loading: 5 skeleton rows. First run with no music: an EmptyState (section 17) replaces the hero and sections.

### 10.2 NOW PLAYING (screenshot 02)
Portrait, top to bottom, centered:
1. Top bar (56 dp high, 8 dp side inset): chevron-down button (closes) at the start; "NOW PLAYING" (labelL, Sand) centered; ellipsis (horizontal) button at the end (opens the song menu).
2. Record, 8 dp below the top bar. Diameter = screen width minus 75 dp, max 320 dp (300 dp on a 375 dp screen). Layers, bottom to top:
   - Disc: circle in Disc color.
   - Grooves: three circles, 1 dp stroke, #1E1B18, at 84%, 66%, and 50% of the record radius.
   - Center cover: circular image, 43% of the record diameter (128 dp at 300 dp). Placeholder art if the song has none.
   - Gold ring: 4 dp stroke on the outer edge. It shows download progress: a full ring when the song is downloaded; an arc starting at 12 o'clock and growing clockwise while downloading; no ring for streaming-only songs and for songs on the phone. The ring does not rotate.
   - Rotation: disc, grooves, and cover rotate together, one full turn every 3.6 s, linear, only while playing. Pausing stops at the current angle; resuming continues from it. Never rotate when animations are disabled (section 7.7).
3. Badge: centered, overlapping the bottom edge of the record by half its height. A pill 32 dp high, Surface fill, 1 dp Border, 12 dp horizontal padding: an 18 dp icon, 8 dp, then the label.
   - Downloaded: circle-check icon and "DOWNLOADED".
   - On the phone: smartphone icon and "ON THIS PHONE".
   - Streaming-only: no badge, but keep its space so nothing jumps.
4. Song title, 32 dp below the badge: titleM, Sleeve, centered, max 2 lines. Artist, 12 dp below: artistL, Sand, centered, one line.
5. Progress, 40 dp below the artist: a 4 dp bar, Divider track, Gold fill, full content width, no thumb at rest. While dragging, show a 12 dp Gold thumb and update the elapsed time live; commit the seek on release. The touch area is 48 dp high. 8 dp below: elapsed time at the start and total time at the end (meta, Sand). Format "mm:ss" with a leading zero ("01:42"), or "h:mm:ss" from one hour.
6. Controls, 40 dp below the time row, 32 dp side padding, SpaceBetween: shuffle (24 dp icon), previous (32 dp skip-back), play/pause (80 dp Gold circle with a 36 dp Espresso icon), next (32 dp skip-forward), repeat (24 dp icon). Each has a 48 dp minimum target.
   - Shuffle and repeat are Sand when off and Gold when on. Repeat cycles Off, All, One, and shows repeat-1 for One.
   - Previous restarts the song if more than 3 s have played; otherwise it goes to the previous song.
   - While buffering, show a 28 dp Espresso circular progress indicator in place of the play/pause icon.
7. Bottom row, 24 dp above the bottom inset (or 32 dp below the controls if space is tight), 40 dp side padding, SpaceBetween: Lyrics (quote), Queue (list), Devices (smartphone). Each is a 24 dp Sleeve icon over a 14 sp Sand label.
   - Lyrics: a sheet with the text in section 17.
   - Queue: a sheet titled "Up next". The current song first (Gold title), then the upcoming songs. Each upcoming row has an x button that removes it. Empty text in section 17.
   - Devices: a sheet titled "Play on" with one row, "This phone", and a Gold circle-check.
8. Landscape or width 600 dp and up: two columns. Left: the record (max 280 dp). Right: title, artist, progress, controls, bottom row.

### 10.3 SEARCH (screenshot 03)
1. Search field, 16 dp below the status bar: 56 dp high, Surface, 4 dp radius, 24 dp side margins. Search icon (24 dp, Sleeve) 12 dp from the start, 12 dp gap, then the text (input style). Placeholder in Sand: "Search songs, artists, or albums". At the end: a clear button (20 dp circle-x in Sand, 48 dp target) shown only when there is text. Keyboard action: Search. Focus the field when the tab opens, but show the keyboard only when the query is empty.
2. Empty field = Recent Searches state: heading "Recent Searches", then a FlowRow of RecentSearchChips (max 10, newest first). Hide the heading when there are none.
3. Typed text = results state. Wait 300 ms after the last keystroke; a new request cancels the old one. Sections, in this order, hidden when empty:
   - "Songs": up to 5 SongRows (Search variant), "VIEW ALL" if there are more. Tapping a song plays the song list from that song.
   - "Artists": a LazyRow of 96 dp circular avatars, 24 dp gaps, with the name below (12 dp gap, body, Sleeve, centered, max 2 lines). "VIEW ALL" if there are more than shown. Tapping opens a Collection of that artist's songs.
   - "Albums": a 2-column grid of square covers (4 dp radius, 16 dp gap) with the title (rowTitle) 8 dp below and the year (meta, Sand) under it. Show 4, with "VIEW ALL" for more. Tapping opens a Collection of that album.
   - "VIEW ALL" opens SearchSection: back arrow, the section name as titleM, and the full list (songs as rows, artists as rows with avatars, albums as a grid).
4. Search covers songs on the phone and downloads immediately (local database), plus the online sources when the app is online and Offline mode is off. While online results load, show a 2 dp Gold indeterminate line under the field. Merge the results and remove duplicates (a downloaded song appears once).
5. When effectively offline, show one Sand meta line under the field (text in section 17).
6. Ranking: exact title match, then title starts with the query, then title contains it, then artist or album matches. Ties: A to Z.
7. No results: an EmptyState (icon: search) with the text in section 17.
8. Save a recent search when the user presses Search on the keyboard or opens a result. Keep the 10 newest with no duplicates (ignore case).

### 10.4 COLLECTION (screenshot 04: a playlist). The same screen is used for user playlists, Golden Hits, Liked songs, albums, artists, and eras.
The whole screen scrolls as one LazyColumn.
1. Top row (48 dp): chevron-left back button at the start, 4 dp from the edge. User playlists also have an ellipsis-vertical button at the end with "Rename playlist" and "Delete playlist".
2. Cover: 256 dp square (about 68% of the width), 4 dp radius, centered, 16 dp below the top row.
3. Title, 32 dp below the cover: titleXL, Gold, centered, max 2 lines. Meta line, 8 dp below: "18 songs • 1h 12m" (body, Sand, centered). Use "1 song". Duration format: "1h 12m", "42m", "2h".
4. Action row, 24 dp below the meta line, 24 dp side margins: Play (52 dp Gold circle, Espresso play icon), 16 dp, Shuffle (52 dp Surface circle, Sleeve shuffle icon), 16 dp, DownloadButton (52 dp), then a spacer, then Add songs (circle-plus, 24 dp icon, 48 dp target) at the end. Add songs shows only on user-created playlists.
   - Play starts at the first song with shuffle off. Shuffle plays the list shuffled.
   - Download queues every song in the list that is not downloaded and not on the phone. While downloading, show the status line "Downloading 12 of 42" with a "Cancel" action. When everything is downloaded, tapping the button asks "Remove downloads?" (text in section 17).
   - Add songs opens a full-height sheet: a search field, a list with checkboxes, and a Gold "Add [N] songs" button that stays disabled until a song is checked.
5. Track list, 32 dp below the action row: SongRows (80 dp). Tapping plays the list from that song.
6. Empty playlist: EmptyState with the text in section 17.

### 10.5 LIBRARY (screenshot 05)
1. Header: "Your Library" (titleM, Gold) at the start. At the end, a round add button: 40 dp Surface circle with a 20 dp Sleeve plus icon, 48 dp tap target. It opens the New playlist dialog.
2. SegmentedControl, 24 dp below: Playlists, Downloads, Liked. Remember the selected segment while the app runs.
3. Storage, 24 dp below: "LOCAL STORAGE" (labelL, Sand) at the start, "[used] of [total]" (bodyMed, Sleeve) at the end. 8 dp below, a 4 dp bar: Divider track, Gold fill. used = total size of downloaded songs. total = the phone's total storage (StatFs on filesDir). Format with one decimal and no trailing ".0": "12.4 GB of 64 GB". Under 1 GB use whole MB: "850 MB".
4. Sort row, 32 dp below: at the start, arrow-down-wide-narrow (24 dp, Sand), 12 dp, then the sort name (body, Sand). At the end, a view toggle (layout-grid in list view, list in grid view; 48 dp target). Tapping the sort label opens a sheet "Sort by" with "Recent added" and "Name (A-Z)", plus "Largest first" on the Downloads tab. Remember the choice.
5. List view: rows 80 dp, cover 48 dp, a 1 dp Divider under each row from the text start to the end margin.
   - Playlists tab: Golden Hits first (built in), then user playlists. Row: name (rowTitle) and "Playlist • 42 tracks" (meta, Sand; "1 track"). Tap opens the Collection. Long-press: "Rename playlist" and "Delete playlist" (not for Golden Hits).
   - Downloads tab: downloaded songs as SongRows. Subline: "Artist • 8.4 MB". The "..." menu is available.
   - Liked tab: liked songs as SongRows.
   - Grid view: a 2-column grid (16 dp gap) of square covers with the title and meta below, like Albums in Search.
6. Pull down to refresh runs the phone scan (and refreshes online data when connected). Show the scan messages from section 17.
7. If audio access is missing, show a banner row above the list: "Allow access to find music on your phone." with a Gold "Allow access" button.
8. Empty states for each tab: section 17.

### 10.6 OTHER SHEETS AND SCREENS (not in the screenshots; same style)
- Offline sheet (Home status text): heading "Offline mode". A row with "Offline mode" (rowTitle), the helper line "Only downloaded and on-device songs play." (meta, Sand), and a Switch (Gold track when on, Border track when off). Below it, a row "Music sources" with chevron-right that opens Settings.
- Song menu (the "..." button or long-press): bottom sheet. Header: 48 dp cover, title, artist. Items are 56 dp rows with a 24 dp icon and body text, shown only when they apply:
  Play next (list-start), Add to queue (list-end), Add to playlist (list-plus), Add to Liked / Remove from Liked (heart), Download (download; online songs not downloaded), Remove download (trash-2; downloaded online songs; the song stays in the library and streams again), Remove from this playlist (only inside a user playlist), Share (share-2; plain text "[title] by [artist]"), Song info (info). "Remove" items use Danger text.
- Add to playlist sheet: heading "Add to playlist". First row "New playlist" (plus), which opens the dialog and then adds the song. Then the user playlists with covers.
- New playlist dialog: heading "New playlist", field label "Playlist name" (max 60 characters), buttons "Cancel" and "Create" (disabled while the name is blank). Rename dialog: "Rename playlist" with "Cancel" and "Save". Delete confirm: text in section 17.
- Song info sheet: Title, Artist, Album, Year, Length, Source ("On this phone", "Jamendo", or "Home server"), and for Jamendo songs the license name and link.
- Permission screen (first launch, and when the user taps "Allow access"): full screen, centered: music icon, heading "Find music on your phone", body text from section 17, a Gold "Continue" button (opens the system permission dialog) and a "Not now" text button.
- Settings (heading "Settings", titleM, with a back arrow). Sections with heading-style titles and rows at least 56 dp high:
  Playback and downloads: "Offline mode" switch; "Download on Wi-Fi only" switch (default on).
  Music on this phone: "Scan now" row with "Last scan: [time], [N] songs"; an "Allow access" row when permission is missing.
  Online sources: "Jamendo" switch (default on). After milestone 7: "Home server" fields "Server address", "Username", "Password", a "Test connection" button that shows "Connected" or the exact error, and "Remove server".
  Storage: "Downloads: [size]" with "Remove all downloads" (confirm dialog).
  About: app version, "Open-source licenses" (libraries, fonts, icons), and the Jamendo attribution text.

## 11. MUSIC SOURCES AND THE PHONE SCAN
### 11.1 Origins
  DEVICE: found on the phone by the scan. Plays offline with no download.
  JAMENDO: online catalog. Streams; can be downloaded when the song allows it.
  SERVER: the user's own Subsonic-compatible server (milestone 7). Streams and downloads.
  A song is playable offline when origin = DEVICE or downloadStatus = DONE.
  Define interface RemoteMusicSource: search(query, limit) returning songs, artists, and albums; songsByArtist; songsByAlbum; streamUrl(song); downloadUrl(song) (null when not allowed). Implement JamendoMusicSource (real) and FakeRemoteMusicSource (sample data for tests and @Preview only).
  Save a remote song to Room when the user plays, likes, downloads, or adds it to a playlist, so it can appear in the library.

### 11.2 Phone scan
- Permissions: Android 13+ (API 33+): READ_MEDIA_AUDIO. Android 12 and older: READ_EXTERNAL_STORAGE (declare with maxSdkVersion 32). On API 21 and 22 permissions are granted at install, so skip the request.
- Show the permission screen first. If denied, show the banner and the Home empty state ("Allow access" button). If permanently denied, the button opens the app's settings page.
- Query MediaStore audio (on API 29+ use getContentUri(VOLUME_EXTERNAL); otherwise EXTERNAL_CONTENT_URI) with IS_MUSIC != 0 and DURATION >= 30000. Read _ID, TITLE, ARTIST, ALBUM, ALBUM_ID, DURATION, YEAR, SIZE, DATE_ADDED. Build the playable URI with ContentUris.withAppendedId. Never use the DATA column.
- Run on a background dispatcher. Upsert into Room (origin DEVICE, remoteId = _ID) and delete rows whose files are gone (with their PlaylistSong rows). Register a ContentObserver on the audio URI while the app runs. Also scan on app start and on pull to refresh.
- Missing title: use the file name without its extension. Missing artist: "Unknown artist". Year 0 means unknown.
- Artwork: API 29+: contentResolver.loadThumbnail(uri, Size(300, 300), null). API 28 and older: content://media/external/audio/albumart/[albumId]. If neither works, use the placeholder (section 12).

### 11.3 Jamendo
- Use the Jamendo API v3.0 (developer.jamendo.com). Check the real parameter and field names in the docs. Needs a free client_id: read it from local.properties into BuildConfig. Never commit it. If it is missing, the Jamendo source is disabled and Settings says "Add a Jamendo client ID to local.properties."
- Use tracks, artists, and albums endpoints for search. Map: audio = stream URL; audiodownload = download URL; audiodownload_allowed = download permission; album_image or image = artwork; releasedate = year; license_ccurl = license link.
- Show a disabled download state with the message in section 17 when downloads are not allowed.
- Do not apply era filtering to Jamendo (its music is mostly recent). Eras use the year field of whatever is in the library.

### 11.4 Home server (milestone 7, only when I say so)
- Subsonic API (works with Navidrome and others). Base: [server]/rest/[method].view with u, t, s, v=1.16.1, c=VibesPlayer, f=json. t = md5(password + s) in lowercase hex, with a new random salt s for each request.
- Methods: ping, search3, getPlaylists, getPlaylist, getArtist, getAlbum, getCoverArt (size 300), stream (id), download (id).
- Store the password encrypted with an Android Keystore AES-GCM key on API 23+. On API 21 and 22 use app-private storage and note it in the README.
- Allow cleartext HTTP (home servers are often HTTP) but show the warning "This connection is not encrypted." in Settings when the address starts with http://.

## 12. ARTWORK
- Real covers come from the phone, Jamendo, or the server. Load with Coil, request exact sizes (about 96 px for 48 dp covers at 2x, 600 px for the record), 150 ms cross-fade.
- Missing artwork: a square (circle for avatars and the record center) filled with a palette color picked by the title hash from Oxblood #7A2E2E, #8C6D3F, #4A3B2A, #B89A6A, with the first letter of the title in Fraunces SemiBold (Sleeve on dark colors, Espresso on #B89A6A).
- Hero card and Golden Hits cover: Oxblood fill with a simple vector record (concentric circles in Sleeve at 12% opacity). No photo.
- Put all of this in one composable, Artwork(model, title, shape, size), so real images can be swapped in later. I may add my own images to res/drawable-nodpi, mapped from one file.

## 13. DOWNLOADS
- Statuses: NONE, QUEUED, DOWNLOADING, DONE, FAILED. Progress 0 to 100 in Room.
- One unique DownloadQueueWorker (WorkManager) processes songs with status QUEUED, two at a time, until none are left. Enqueue it with ExistingWorkPolicy.KEEP; it re-reads Room, so new songs are picked up. Cancelling one song sets it to NONE and cancels its OkHttp call. "Cancel" on a collection cancels all of that collection's queued songs.
- Download with OkHttp into filesDir/downloads/[songId].part, then rename to the final file (extension from the content type or URL, default .mp3). Update progress at most every 500 ms. Check free space first; resume with an HTTP Range request if the server allows it.
- Foreground notification (channel "Downloads", low importance, created on API 26+): "Downloading music", "[N] songs left", progress bar, "Cancel" action. On Android 14+ use the dataSync foreground service type.
- When "Download on Wi-Fi only" is on, require an unmetered network. Songs wait in QUEUED and the row says "Waiting for Wi-Fi".
- Failure: status FAILED with the message and "Try again" action in section 17. Not enough space: the storage message with "Manage storage", which opens Library on the Downloads tab.
- "Remove download" deletes the file, sets NONE, and keeps the song in the library.

## 14. OFFLINE MODE
- offlineMode is a DataStore setting. effectiveOffline = offlineMode OR no connection.
- Track the connection in a NetworkMonitor: API 24+ registerDefaultNetworkCallback; API 21 to 23 registerNetworkCallback with an internet-capable NetworkRequest. Expose it as a StateFlow.
- When effectiveOffline: search skips online sources; songs that are not playable offline are hidden in Search, Recently Played, and era lists, and shown dimmed ("Online only") in playlists, Liked, and Downloads-adjacent lists. Every play action queues only playable songs. Tapping a dimmed song shows the message in section 17 (offline-mode text when the switch is on, no-connection text otherwise).
- If the connection drops during a stream, or Offline mode is switched on, pause and show "Connection lost. Downloaded songs still play." (or the Offline-mode message). If the next queue item cannot play, skip to the next playable one; if none, stop.

## 15. PLAYBACK
- Media3 ExoPlayer in a MediaSessionService. Audio attributes: music, handle audio focus. Pause when headphones are unplugged. WAKE_MODE_NETWORK for streaming.
- A PlayerController (singleton) connects to the service through a MediaController and exposes StateFlows: current song, isPlaying, buffering, position, duration, shuffle, repeat, queue. Position updates every 250 ms only while playing, and only the progress composables collect it.
- Media items carry title, artist, and artwork so the notification, lock screen, and Bluetooth show them. The default Media3 notification with previous, play/pause, and next must work. Use the mediaPlayback foreground service type on Android 14+. On Android 13+, ask for POST_NOTIFICATIONS the first time the user starts playback or a download, after a short explanation; playback still works if denied.
- Count a play (playCount + 1, lastPlayedAt = now) when a song has played for 30 s or half its length.
- Save the queue, index, position, shuffle, and repeat to DataStore every 5 s and on pause or stop. On launch, restore them paused and show the mini player.
- Errors: network error: "Couldn't play this song. Check your connection." and move to the next song. Missing file (deleted from the phone): "This file is no longer on your phone.", remove it from the library, and move on.
- Stop the service when the task is removed and nothing is playing.

## 16. DATA AND MANIFEST
Room entities:
  Song(id, origin, remoteId, title, artist, album, albumId, durationMs, year, license, licenseUrl, artworkUri, contentUri, downloadStatus, downloadProgress, localFilePath, sizeBytes, isLiked, likedAt, addedAt, playCount, lastPlayedAt), unique on (origin, remoteId).
  Playlist(id, name, createdAt)
  PlaylistSong(playlistId, songId, position, addedAt), primary key (playlistId, songId), foreign keys with cascade delete.
  RecentSearch(query, searchedAt), query unique ignoring case.
DataStore: offlineMode, downloadWifiOnly, jamendoEnabled, library sort per tab, selected Library segment, queue and position, server settings.
Manifest: INTERNET, ACCESS_NETWORK_STATE, READ_MEDIA_AUDIO, READ_EXTERNAL_STORAGE (maxSdkVersion 32), POST_NOTIFICATIONS, FOREGROUND_SERVICE, FOREGROUND_SERVICE_MEDIA_PLAYBACK, FOREGROUND_SERVICE_DATA_SYNC, WAKE_LOCK. PlaybackService with foregroundServiceType mediaPlayback. WorkManager's SystemForegroundService with foregroundServiceType dataSync (tools:node="merge"). allowBackup false. usesCleartextTraffic true (only because of home servers; see 11.4).

## 17. TEXT (use exactly; sentence case; put in strings.xml)
Status: "Online", "Offline mode", "No connection"
Offline sheet: "Offline mode", "Only downloaded and on-device songs play.", "Music sources"
Notifications: "Notifications", "No notifications yet."
Home empty: "No music yet" / "Find music on your phone or connect an online source." / buttons "Find music on this phone", "Music sources"
Era empty: "No songs from the [1950s] yet." / "Add music from that era to your phone or server."
Recently played empty: "Songs you play will show up here."
Permission screen: "Find music on your phone" / "Vibes Player needs access to your audio files to play the music already on this phone. Your files stay on your phone." / "Continue" / "Not now"
Permission denied: "Allow access to find music on your phone." / "Allow access"; permanently denied button: "Open settings"
Scan: "Scanning music on this phone..." then "Found [N] songs."; none: "No music found on this phone."
Search: placeholder "Search songs, artists, or albums"; "Recent Searches"; no results: "No songs match "[query]". Check the spelling or try an artist's name."
Offline search line: "Offline mode is on. Showing songs on this phone and downloads." or "No connection. Showing songs on this phone and downloads."
Download: "Downloading [done] of [total]" / "Cancel"; "Downloaded [title]"; "Download removed"; failure: "Couldn't download [title]. Check your connection." / "Try again"; no space: "Not enough space. Free up space to download this song." / "Manage storage"; Wi-Fi wait: "Waiting for Wi-Fi"; not allowed: "Download not allowed for this song."; cancelled: "Downloads cancelled"
Remove downloads dialog: "Remove downloads?" / "Songs from this playlist will no longer play offline. They stay in your library." / "Cancel", "Remove"
Blocked playback: "Turn off Offline mode to play this song." and "No connection. Connect to the internet to play this song."
Connection: "Connection lost. Downloaded songs still play."
Playback errors: "Couldn't play this song. Check your connection." and "This file is no longer on your phone."
Queue: "Up next"; "Playing next"; "Added to queue"; empty: "Nothing in the queue."
Devices: "Play on", "This phone"
Lyrics: "Lyrics aren't available for this song."
Liked: "Added to Liked", "Removed from Liked"; Liked empty: "No liked songs yet." / "Open a song's menu and choose Add to Liked."
Playlists: "Added to [name]", "Already in [name]"; Library empty: "No playlists yet." / "Create playlist"; empty playlist: "This playlist is empty" / "Add songs to start listening." / "Add songs"; Downloads empty: "Nothing is downloaded yet." / "Open any playlist and tap the download button."
Delete playlist: "Delete [name]?" / "The songs stay in your library." / "Cancel", "Delete"
Add songs picker: button "Add [N] songs"
Jamendo missing: "Add a Jamendo client ID to local.properties."
Server: "Connected"; "This connection is not encrypted."

## 18. ACCESSIBILITY, PERFORMANCE, QUALITY
- Every icon-only button has a content description with the song or playlist name ("Download [title]", "More options for [title]", "Play next").
- Use proper semantics: roles for buttons, switches, and tabs; selected state for tabs, chips, and segments; stateDescription for download states ("Downloaded", "Downloading 40 percent", "Online only"); the progress bar exposes its range and supports accessibility seeking; the record has the description "Album art for [title]". Add custom actions for the row menu so TalkBack users can reach it.
- Status is never shown by color alone: downloaded uses an icon and text.
- Text contrast at least 4.5:1 (Gold, Sleeve, and Sand pass on Espresso). Touch targets at least 48 dp. Keyboard focus is visible.
- Lists use LazyColumn or LazyVerticalGrid with stable keys. The scan and downloads never block the UI. Position updates must not recompose whole screens.
- Release build: R8 minify and shrink resources on, with working keep rules. Test the release build.

## 19. TESTS
- Unit: MediaStore cursor to Song mapping; isPlayableOffline rules; offline filtering; search ranking; size and duration formatting; recent-search dedupe; Golden Hits selection; era filtering; download queue states; Subsonic token (md5) with a known example; Jamendo JSON parsing from a sample.
- Compose UI tests: SongRow in every state; MiniPlayer with all controls visible at 320 dp width; Library segmented control; Offline sheet.
- Manual check list in the README: airplane mode, 200% font size, TalkBack, landscape, tablet.

## 20. MILESTONES (stop after each)
1. Setup and design system: Gradle, version catalog, Hilt, theme, fonts, icons, splash, app icon, edge-to-edge, navigation shell with the tab bar and empty screens, and every shared component (section 8) with @Preview in every state. Add a debug-only components gallery.
2. Data and phone scan: Room, DataStore, repositories, permission screen and flow, MediaStore scan with ContentObserver, artwork loading, unit tests.
3. Playback: service, PlayerController, queue persistence, notification, MiniPlayer, Now Playing (record, ring, controls, Lyrics, Queue, Devices sheets).
4. Screens: Home, Search (local data), Collection, Library, song menu, playlist dialogs, add-songs picker, SearchSection, RecentlyPlayed, responsive layouts.
5. Online and downloads: NetworkMonitor, Jamendo source, merged search, download queue, progress ring, Offline mode sheet and rules, all messages.
6. Settings, polish, and QA: Settings screen, accessibility pass, 200% font and 320 dp checks, tablet and landscape, tests on emulators for minSdk, API 26, 29, 33, 34, and the latest, plus one tablet; release build; README.
7. Optional, only when I say so: home server (Subsonic).

## 21. FINISH
- ./gradlew assembleDebug, ./gradlew test, and ./gradlew lintDebug pass with no errors.
- README.md: how to build and install on my phone (USB debugging, adb install), how to add music to an emulator (adb push to /sdcard/Music, then trigger a media scan), where to put the Jamendo client_id, the supported Android versions, and licenses (Jamendo attribution, fonts, icons, libraries).
- docs/ASSUMPTIONS.md: every assumption and deviation.
- A final table with one row per screen: "matches the screenshot" or "differs, and why".
