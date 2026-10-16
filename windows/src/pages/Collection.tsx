import React from 'react';
import { Song } from '../data/fixtures';
import { SongRow } from '../components/SongRow';
import { ChevronLeftIcon, PlayIcon, ShuffleIcon } from '../components/icons';

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
    <div style={{ padding: 24, width: '100%' }}>
      <button
        onClick={onBack}
        style={{ padding: 4, marginBottom: 32 }}
        title="Back"
      >
        <ChevronLeftIcon style={{ fill: 'currentColor', stroke: 'none', color: 'var(--color-sleeve)' }} />
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
          border: '1px solid var(--color-border)',
        }}
      >
        <div className="letter-tile letter-tile-oxblood">{playlistName.charAt(0).toUpperCase()}</div>
      </div>

      <h1 className="size-title-xl" style={{ textAlign: 'center', marginBottom: 8 }}>
        {playlistName}
      </h1>
      <p style={{ textAlign: 'center', color: 'var(--color-sand)', fontSize: 14, marginBottom: 32 }}>
        {songs.length} songs • {formatDuration(totalDuration)}
      </p>

      <div style={{ display: 'flex', justifyContent: 'center', gap: 12, marginBottom: 32 }}>
        <button style={{ width: 52, height: 52 }} title="Play">
          <PlayIcon style={{ fill: 'currentColor', stroke: 'none', color: 'var(--color-espresso)' }} />
        </button>
        <button style={{ width: 52, height: 52 }} title="Shuffle">
          <ShuffleIcon style={{ fill: 'currentColor', stroke: 'none', color: 'var(--color-sleeve)' }} />
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
            onClick={() => onSongSelect(song)}
          />
        ))}
      </div>
    </div>
  );
};