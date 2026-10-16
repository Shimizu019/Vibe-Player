import { useState, useCallback } from 'react';
import { Song } from './data/fixtures';
import { Home } from './pages/Home';
import { NowPlaying } from './pages/NowPlaying';
import { Search } from './pages/Search';
import { Library } from './pages/Library';
import { Collection } from './pages/Collection';
import { MiniPlayer } from './components/MiniPlayer';
import { BottomTabBar } from './components/BottomTabBar';
import './index.css';

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
      <div className="app-content">
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

      {nowPlayingSong && (
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
      )}

      <BottomTabBar
        activeTab={view}
        onTabChange={(tab) => {
          if (tab === 'home') setView('home');
          if (tab === 'search') setView('search');
          if (tab === 'library') setView('library');
        }}
      />
    </div>
  );
}