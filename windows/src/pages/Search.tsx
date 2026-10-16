import React, { useState } from 'react';
import { Song } from '../data/fixtures';
import { SongRow } from '../components/SongRow';
import { SearchIcon, CircleXIcon } from '../components/icons';

interface SearchProps {
  onSongSelect: (song: Song) => void;
}

export const Search: React.FC<SearchProps> = ({ onSongSelect }) => {
  const [query, setQuery] = useState('');

  const songs: Song[] = [
    { id: '1', title: 'Take Five', artist: 'Dave Brubeck', album: 'Time Out', year: 1959, duration: 324000, isDownloaded: true },
    { id: '2', title: 'What a Wonderful World', artist: 'Louis Armstrong', album: 'What a Wonderful World', year: 1967, duration: 136000, isDownloaded: true },
    { id: '3', title: 'Stand by Me', artist: 'Ben E. King', album: "Don't Play That Song!", year: 1961, duration: 177000, isDownloaded: false },
    { id: '4', title: 'Imagine', artist: 'John Lennon', album: 'Imagine', year: 1971, duration: 183000, isDownloaded: true },
    { id: '5', title: 'Hotel California', artist: 'Eagles', album: 'Hotel California', year: 1976, duration: 390000, isDownloaded: false },
    { id: '6', title: 'Bohemian Rhapsody', artist: 'Queen', album: 'A Night at the Opera', year: 1975, duration: 354000, isDownloaded: true },
    { id: '7', title: 'Billie Jean', artist: 'Michael Jackson', album: 'Thriller', year: 1982, duration: 294000, isDownloaded: false },
    { id: '8', title: 'Sweet Child O\' Mine', artist: 'Guns N\' Roses', album: 'Appetite for Destruction', year: 1987, duration: 356000, isDownloaded: false },
  ];

  const filtered = query
    ? songs.filter(
        (s) =>
          s.title.toLowerCase().includes(query.toLowerCase()) ||
          s.artist.toLowerCase().includes(query.toLowerCase()) ||
          s.album?.toLowerCase().includes(query.toLowerCase()),
      )
    : [];

  return (
    <div className="screen">
      <div style={{ marginBottom: 24 }}>
        <div
          style={{
            position: 'relative',
            width: '100%',
            maxWidth: 600,
          }}
        >
          <input
            type="text"
            placeholder="Search songs, artists, or albums"
            value={query}
            onChange={(e) => setQuery(e.target.value)}
            style={{
              width: '100%',
              height: 56,
              padding: '0 16px 0 48px',
              borderRadius: 4,
              border: '1px solid var(--color-border)',
              backgroundColor: 'var(--color-surface)',
              color: 'var(--color-sleeve)',
              fontSize: 16,
              fontFamily: 'inherit',
            }}
          />
          <div style={{ position: 'absolute', left: 16, top: '50%', transform: 'translateY(-50%)', color: 'var(--color-sand)', display: 'inline-flex', alignItems: 'center', justifyContent: 'center', width: 24, height: 24 }}>
            <SearchIcon style={{ fill: 'currentColor', stroke: 'none', color: 'var(--color-sand)' }} />
          </div>
          {query && (
            <button
              style={{
                position: 'absolute',
                right: 12,
                top: '50%',
                transform: 'translateY(-50%)',
                background: 'none',
                border: 'none',
                color: 'var(--color-sand)',
                cursor: 'pointer',
                padding: 4,
              }}
              onClick={() => setQuery('')}
            >
              <span style={{ display: 'inline-flex', alignItems: 'center', justifyContent: 'center', width: 20, height: 20, color: 'var(--color-sand)' }}>
                <CircleXIcon style={{ fill: 'currentColor', stroke: 'none' }} />
              </span>
            </button>
          )}
        </div>
        {query === '' && (
          <p style={{ marginTop: 8, color: 'var(--color-sand)', fontSize: 12 }}>
            Type to search your library and online sources.
          </p>
        )}
      </div>

      {query === '' ? (
        <>
          <div style={{ fontSize: 16, fontWeight: 600, color: 'var(--color-gold)', marginBottom: 16 }}>
            Recent Searches
          </div>
          <div style={{ color: 'var(--color-sand)', fontSize: 14, fontStyle: 'italic' }}>
            No recent searches yet.
          </div>
        </>
      ) : filtered.length === 0 ? (
        <div style={{ color: 'var(--color-sand)', fontSize: 14, marginTop: 24 }}>
          No songs match "{query}". Check the spelling or try an artist's name.
        </div>
      ) : (
        <>
          <div style={{ fontSize: 16, fontWeight: 600, color: 'var(--color-gold)', marginBottom: 16 }}>
            Songs ({filtered.length})
          </div>
          <div style={{ display: 'flex', flexDirection: 'column', gap: 4 }}>
            {filtered.map((song) => (
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