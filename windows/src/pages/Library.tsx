import React from 'react';
import { Song } from '../data/fixtures';
import { SongRow } from '../components/SongRow';

interface LibraryProps {
  onSongSelect: (song: Song) => void;
}

interface Playlist {
  id: string;
  name: string;
  songCount: number;
  totalDurationMs: number;
}

export const Library: React.FC<LibraryProps> = ({ onSongSelect }) => {
  const playlists: Playlist[] = [
    { id: 'p1', name: 'Golden 60s', songCount: 12, totalDurationMs: 2400000 },
    { id: 'p2', name: 'Sunday Jazz', songCount: 8, totalDurationMs: 1800000 },
    { id: 'p3', name: 'Vinyl Classics', songCount: 20, totalDurationMs: 4500000 },
  ];

  const allSongs: Song[] = [
    { id: '1', title: 'Take Five', artist: 'Dave Brubeck', album: 'Time Out', year: 1959, duration: 324000, isDownloaded: true },
    { id: '2', title: 'What a Wonderful World', artist: 'Louis Armstrong', album: 'What a Wonderful World', year: 1967, duration: 136000, isDownloaded: true },
    { id: '3', title: 'Stand by Me', artist: 'Ben E. King', album: "Don't Play That Song!", year: 1961, duration: 177000, isDownloaded: false },
    { id: '4', title: 'Imagine', artist: 'John Lennon', album: 'Imagine', year: 1971, duration: 183000, isDownloaded: true },
    { id: '5', title: 'Hotel California', artist: 'Eagles', album: 'Hotel California', year: 1976, duration: 390000, isDownloaded: false },
  ];

  const formatDuration = (ms: number) => {
    const totalSeconds = Math.floor(ms / 1000);
    const h = Math.floor(totalSeconds / 3600);
    const m = Math.floor((totalSeconds % 3600) / 60);
    const s = totalSeconds % 60;
    if (h > 0) return `${h}:${String(m).padStart(2, '0')}:${String(s).padStart(2, '0')}`;
    return `${m}:${String(s).padStart(2, '0')}`;
  };

  return (
    <div className="screen">
      <div style={{ display: 'flex', flexDirection: 'column', gap: 32 }}>
        <div>
          <h2 style={{ fontSize: '1.375rem', color: 'var(--color-gold)', marginBottom: 8 }}>Playlists</h2>
          <p style={{ color: 'var(--color-sand)', fontSize: 14 }}>{playlists.length} playlists in your library</p>
        </div>

        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(280px, 1fr))', gap: 16 }}>
          {playlists.map((pl) => (
            <div
              key={pl.id}
              style={{
                backgroundColor: 'var(--color-surface)',
                border: '1px solid var(--color-border)',
                borderRadius: 4,
                padding: 20,
                cursor: 'pointer',
                transition: 'border-color 0.2s',
              }}
              onMouseEnter={(e) => (e.currentTarget.style.borderColor = 'var(--color-gold)')}
              onMouseLeave={(e) => (e.currentTarget.style.borderColor = 'var(--color-border)')}
            >
              <h3 style={{ fontSize: '1.125rem', color: 'var(--color-sleeve)', marginBottom: 8 }}>{pl.name}</h3>
              <div style={{ color: 'var(--color-sand)', fontSize: 13 }}>
                {pl.songCount} songs • {formatDuration(pl.totalDurationMs)}
              </div>
            </div>
          ))}
        </div>

        <div>
          <h2 style={{ fontSize: '1.375rem', color: 'var(--color-gold)', marginBottom: 16 }}>All Songs</h2>
          <div style={{ display: 'flex', flexDirection: 'column', gap: 4 }}>
            {allSongs.map((song) => (
              <SongRow
                key={song.id}
                title={song.title}
                artist={song.artist}
                album={song.album}
                year={song.year}
                duration={song.duration}
                onClick={() => onSongSelect(song)}
              />
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};