import React from 'react';
import { Song } from '../data/fixtures';
import { SongRow } from '../components/SongRow';

interface CollectionProps {
  playlistName: string;
  songs: Song[];
  onSongSelect: (song: Song) => void;
  onBack: () => void;
}

interface FormatDuration {
  (ms: number): string;
}

export const Collection: React.FC<CollectionProps> = ({
  playlistName,
  songs,
  onSongSelect,
  onBack,
}) => {
  const formatDuration: FormatDuration = (ms) => {
    const totalSeconds = Math.floor(ms / 1000);
    const m = Math.floor(totalSeconds / 60);
    const s = totalSeconds % 60;
    return `${m}:${String(s).padStart(2, '0')}`;
  };

  const totalDuration = songs.reduce((sum, s) => sum + s.duration, 0);

  return (
    <div style={{ padding: 24, maxWidth: 800, margin: '0 auto' }}>
      <button
        onClick={onBack}
        style={{
          background: 'none',
          border: 'none',
          color: 'var(--color-sand)',
          fontSize: 20,
          cursor: 'pointer',
          display: 'inline-flex',
          alignItems: 'center',
          gap: 4,
          marginBottom: 32,
        }}
      >
        ◀ Back
      </button>

      <div
        style={{
          width: 256,
          height: 256,
          borderRadius: 4,
          backgroundColor: 'var(--color-surface)',
          margin: '0 auto 32px',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          color: 'var(--color-sand)',
          fontSize: 14,
          border: '1px solid var(--color-border)',
        }}
      >
        Cover
      </div>

      <h1 style={{ fontSize: '2rem', color: 'var(--color-gold)', textAlign: 'center', marginBottom: 8 }}>
        {playlistName}
      </h1>
      <p style={{ textAlign: 'center', color: 'var(--color-sand)', fontSize: 14, marginBottom: 32 }}>
        {songs.length} songs • {formatDuration(totalDuration)}
      </p>

      <div style={{ display: 'flex', justifyContent: 'center', gap: 12, marginBottom: 32 }}>
        <button
          style={{
            width: 52,
            height: 52,
            borderRadius: '50%',
            backgroundColor: 'var(--color-gold)',
            color: 'var(--color-espresso)',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            fontSize: 24,
            cursor: 'pointer',
          }}
        >
          ▶
        </button>
        <button
          style={{
            width: 52,
            height: 52,
            borderRadius: '50%',
            backgroundColor: 'var(--color-surface)',
            color: 'var(--color-sleeve)',
            border: '1px solid var(--color-border)',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            fontSize: 20,
            cursor: 'pointer',
          }}
        >
          🔀
        </button>
      </div>

      <div style={{ display: 'flex', flexDirection: 'column', gap: 4 }}>
        {songs.map((song) => (
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
  );
};