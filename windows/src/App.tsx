import { useState, useCallback } from 'react';
import { Song } from './data/fixtures';
import { Home } from './pages/Home';
import { NowPlaying } from './pages/NowPlaying';
import { Search } from './pages/Search';
import { Library } from './pages/Library';
import { Collection } from './pages/Collection';
import { MiniPlayer } from './components/MiniPlayer';
import { BellIcon, HomeIcon, SearchIcon, LibraryIcon } from './components/icons';
import './index.css';

const TABS = [
  { id: 'home', label: 'Home', Icon: HomeIcon },
  { id: 'search', label: 'Search', Icon: SearchIcon },
  { id: 'library', label: 'Library', Icon: LibraryIcon },
];

type View = 'home' | 'nowplaying' | 'search' | 'library' | 'collection';

const sampleSong: Song = {
  id: '1',
  title: 'Take Five',
  artist: 'Dave Brubeck',
  album: 'Time Out',
  year: 1959,
  duration: 324000,
  isDownloaded: true,
};

export default function App() {
  const [view, setView] = useState<View>('home');
  const [nowPlayingSong, setNowPlayingSong] = useState<Song | null>(sampleSong);
  const [isPlaying, setIsPlaying] = useState(false);
  const [collection, setCollection] = useState<{ name: string; songs: Song[] } | null>(null);

  const handleSongSelect = useCallback((song: Song) => {
    setNowPlayingSong(song);
    setIsPlaying(true);
    setView('nowplaying');
  }, []);

  const handlePlayPause = useCallback(() => setIsPlaying((p) => !p), []);

  return (
    <div className="app-shell">
      <div className="phone-frame">
        <div className="phone-content">
          {/* Top row */}
          <div className="top-row">
            <span className="status-text">Online</span>
            <button
              className="icon-button sleeve"
              style={{ padding: 4 }}
              title="Notifications"
            >
              <BellIcon />
            </button>
          </div>

          {view === 'home' && (
            <Home onSongSelect={handleSongSelect} />
          )}
          {view === 'search' && (
            <Search onSongSelect={handleSongSelect} />
          )}
          {view === 'library' && (
            <Library onSongSelect={handleSongSelect} />
          )}
          {view === 'collection' && collection && (
            <Collection
              playlistName={collection.name}
              songs={collection.songs}
              onSongSelect={handleSongSelect}
              onBack={() => {
                setView('home');
                setCollection(null);
              }}
            />
          )}
          {view === 'nowplaying' && (
            <NowPlaying
              song={nowPlayingSong}
              isPlaying={isPlaying}
              onClose={() => setView('home')}
              onPlayPause={handlePlayPause}
              onPrev={() => {}}
              onNext={() => {}}
            />
          )}
        </div>

        {/* Mini player (above tab bar) */}
        {nowPlayingSong && (
          <div className="mini-player-container">
            <div className="mini-progress" />
            <MiniPlayer
              isPlaying={isPlaying}
              title={nowPlayingSong.title}
              artist={nowPlayingSong.artist}
              coverUrl={nowPlayingSong.coverUrl}
              onPlayPause={handlePlayPause}
              onPrev={() => {}}
              onNext={() => {}}
              onOpenNowPlaying={() => setView('nowplaying')}
            />
          </div>
        )}

        {/* Tab bar (last element at bottom) */}
        <div className="bottom-tab-bar">
          {TABS.map((tab) => {
            const active = view === tab.id;
            const Icon = tab.Icon;
            return (
              <button
                key={tab.id}
                onClick={() => setView(tab.id as View)}
                className={`tab-item${active ? ' active' : ''}`}
              >
                <span style={{ width: 24, height: 24, display: 'inline-flex', alignItems: 'center', justifyContent: 'center', color: active ? 'var(--color-gold)' : 'var(--color-sleeve)' }}>
                  <Icon />
                </span>
                <span style={{ fontSize: 12, fontWeight: 500, color: active ? 'var(--color-gold)' : 'var(--color-sand)' }}>{tab.label}</span>
              </button>
            );
          })}
        </div>
      </div>
    </div>
  );
}