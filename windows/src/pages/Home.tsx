import React, { useState } from 'react';
import { Song } from '../data/fixtures';
import { SongRow } from '../components/SongRow';
import { EraChip } from '../components/EraChip';
import { SectionHeader } from '../components/SectionHeader';
import { PlayIcon } from '../components/icons';

interface HomeProps {
  onSongSelect: (song: Song) => void;
}

export const Home: React.FC<HomeProps> = ({ onSongSelect }) => {
  const [selectedEra, setSelectedEra] = useState<string | null>(null);

  const eras = ['1950s', '1960s', '1970s', '1980s'];
  const goldenHits: Song[] = [
    { id: '1', title: 'Take Five', artist: 'Dave Brubeck', album: 'Time Out', year: 1959, duration: 324000, isDownloaded: true },
    { id: '2', title: 'What a Wonderful World', artist: 'Louis Armstrong', album: 'What a Wonderful World', year: 1967, duration: 136000, isDownloaded: true },
    { id: '3', title: 'Stand by Me', artist: 'Ben E. King', album: "Don't Play That Song!", year: 1961, duration: 177000, isDownloaded: false },
    { id: '4', title: 'Imagine', artist: 'John Lennon', album: 'Imagine', year: 1971, duration: 183000, isDownloaded: true },
    { id: '5', title: 'Hotel California', artist: 'Eagles', album: 'Hotel California', year: 1976, duration: 390000, isDownloaded: false },
  ];

  const recentlyPlayed: Song[] = [
    { id: 'r1', title: 'Stand by Me', artist: 'Ben E. King', year: 1961, duration: 177000, isDownloaded: false },
    { id: 'r2', title: 'Imagine', artist: 'John Lennon', year: 1971, duration: 183000, isDownloaded: true },
    { id: 'r3', title: 'Hotel California', artist: 'Eagles', year: 1976, duration: 390000, isDownloaded: false },
  ];

  const eraSongs = selectedEra
    ? goldenHits.filter((s) => String(s.year).startsWith(selectedEra[0]))
    : [];

  return (
    <div className="screen">
      <h1 className="size-title-xl" style={{ marginBottom: 16 }}>Vibes Player</h1>

      {/* Hero card */}
      <div
        style={{
          backgroundColor: 'var(--color-oxblood)',
          borderRadius: 4,
          height: 224,
          position: 'relative',
          overflow: 'hidden',
          marginBottom: 40,
          border: '1px solid var(--color-border)',
        }}
      >
        <div style={{ position: 'absolute', top: 24, left: 24, bottom: 24, right: 24, display: 'flex', flexDirection: 'column', justifyContent: 'flex-end', gap: 8 }}>
          <span style={{ fontSize: 16, color: 'var(--color-sand)' }}>Featured Playlist</span>
          <h2 className="size-title-l" style={{ margin: 0, color: 'var(--color-sleeve)' }}>Golden Hits</h2>
        </div>
        {/* Faint vinyl record artwork */}
        <svg
          style={{
            position: 'absolute',
            left: '75%',
            top: '55%',
            transform: 'translate(-50%, -50%)',
            width: 200,
            height: 200,
            pointerEvents: 'none',
          }}
          viewBox="0 0 200 200"
        >
          <circle cx="100" cy="100" r="100" fill="none" stroke="currentColor" strokeWidth={1.5} strokeOpacity={0.12} />
          <circle cx="100" cy="100" r="75" fill="none" stroke="currentColor" strokeWidth={1.5} strokeOpacity={0.12} />
          <circle cx="100" cy="100" r="50" fill="none" stroke="currentColor" strokeWidth={1.5} strokeOpacity={0.12} />
          <circle cx="100" cy="100" r="25" fill="none" stroke="currentColor" strokeWidth={1.5} strokeOpacity={0.12} />
          <circle cx="100" cy="100" r="6" fill="currentColor" fillOpacity={0.12} />
        </svg>
        <button
          style={{
            position: 'absolute',
            top: 24,
            right: 24,
            width: 48,
            height: 48,
            borderRadius: '50%',
            backgroundColor: 'var(--color-gold)',
            color: 'var(--color-espresso)',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            cursor: 'pointer',
          }}
          onClick={() => onSongSelect(goldenHits[0])}
          title="Play Golden Hits"
        >
          <PlayIcon style={{ fill: 'currentColor', stroke: 'none', color: 'var(--color-espresso)' }} />
        </button>
      </div>

      {/* Explore Eras */}
      <SectionHeader title="Explore Eras" />
      <div style={{ display: 'flex', gap: 12, overflowX: 'auto', paddingBottom: 12, marginBottom: 40 }}>
        {eras.map((era) => (
          <EraChip
            key={era}
            label={era}
            isSelected={selectedEra === era}
            onClick={() => setSelectedEra(selectedEra === era ? null : era)}
          />
        ))}
      </div>

      {selectedEra ? (
        <>
          <SectionHeader title={`Songs from the ${selectedEra}`} />
          <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(200px, 1fr))', gap: 16 }}>
            {eraSongs.length > 0 ? (
              eraSongs.map((song) => (
                <SongRow
                  key={song.id}
                  title={song.title}
                  artist={song.artist}
                  album={song.album}
                  year={song.year}
                  onClick={() => onSongSelect(song)}
                />
              ))
            ) : (
              <div style={{ gridColumn: '1 / -1', color: 'var(--color-sand)', fontSize: 14, padding: 24 }}>
                No songs from the {selectedEra} yet.
              </div>
            )}
          </div>
        </>
      ) : (
        <>
          {/* Recently Played */}
          <SectionHeader title="Recently Played" seeAllLabel="See All" />
          <div style={{ display: 'flex', flexDirection: 'column', gap: 8 }}>
            {recentlyPlayed.map((song) => (
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
        </>
      )}
    </div>
  );
};