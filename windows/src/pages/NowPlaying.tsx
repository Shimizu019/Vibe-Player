import React from 'react';
import { Song } from '../data/fixtures';
import { RecordPlayer } from '../components/RecordPlayer';

interface NowPlayingProps {
  song: Song | null;
  isPlaying: boolean;
  onClose: () => void;
  onPlayPause: () => void;
  onPrev: () => void;
  onNext: () => void;
}

export const NowPlaying: React.FC<NowPlayingProps> = ({
  song,
  isPlaying,
  onClose,
  onPlayPause,
  onPrev,
  onNext,
}) => {
  if (!song) return null;

  return (
    <div style={{ padding: 24, maxWidth: 500, margin: '0 auto', textAlign: 'center' }}>
      <button
        onClick={onClose}
        style={{
          position: 'absolute',
          top: 16,
          left: 16,
          background: 'none',
          border: 'none',
          color: 'var(--color-sand)',
          fontSize: 20,
          cursor: 'pointer',
        }}
      >
        ⌄
      </button>

      <div style={{ marginTop: 40 }}>
        <RecordPlayer isPlaying={isPlaying} coverUrl={song.coverUrl} width={300} />
      </div>

      <div style={{ marginTop: 32, marginBottom: 12 }}>
        <span
          style={{
            display: 'inline-block',
            padding: '6px 12px',
            borderRadius: 12,
            backgroundColor: 'var(--color-surface)',
            border: '1px solid var(--color-border)',
            fontSize: 12,
            color: 'var(--color-gold)',
          }}
        >
          DOWNLOADED
        </span>
      </div>

      <h2 style={{ fontSize: '1.5rem', color: 'var(--color-sleeve)', marginBottom: 8 }}>{song.title}</h2>
      <p style={{ fontSize: '1.125rem', color: 'var(--color-sand)' }}>{song.artist}</p>

      {/* Progress bar */}
      <div style={{ marginTop: 40 }}>
        <div
          style={{
            width: '100%',
            height: 4,
            backgroundColor: 'var(--color-divider)',
            borderRadius: 2,
            position: 'relative',
            cursor: 'pointer',
          }}
        >
          <div
            style={{
              width: '35%',
              height: '100%',
              backgroundColor: 'var(--color-gold)',
              borderRadius: 2,
            }}
          />
        </div>
        <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: 12, color: 'var(--color-sand)', marginTop: 8 }}>
          <span>01:42</span>
          <span>03:42</span>
        </div>
      </div>

      {/* Controls */}
      <div style={{ marginTop: 40, display: 'flex', alignItems: 'center', justifyContent: 'center', gap: 32 }}>
        <button
          onClick={onPrev}
          style={{ color: 'var(--color-sand)', fontSize: 24, background: 'none', border: 'none', cursor: 'pointer' }}
          title="Previous"
        >
          ⏮
        </button>
        <button
          onClick={onPlayPause}
          style={{
            width: 80,
            height: 80,
            borderRadius: '50%',
            backgroundColor: 'var(--color-gold)',
            color: 'var(--color-espresso)',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            fontSize: 36,
            lineHeight: 1,
            cursor: 'pointer',
          }}
          title={isPlaying ? 'Pause' : 'Play'}
        >
          {isPlaying ? '⏸' : '▶'}
        </button>
        <button
          onClick={onNext}
          style={{ color: 'var(--color-sand)', fontSize: 24, background: 'none', border: 'none', cursor: 'pointer' }}
          title="Next"
        >
          ⏭
        </button>
      </div>

      {/* Shuffle / Repeat */}
      <div style={{ marginTop: 24, display: 'flex', justifyContent: 'center', gap: 24, color: 'var(--color-sand)' }}>
        <button style={{ background: 'none', border: 'none', cursor: 'pointer', fontSize: 20 }} title="Shuffle">
          🔀
        </button>
        <button style={{ background: 'none', border: 'none', cursor: 'pointer', fontSize: 20 }} title="Repeat">
          🔁
        </button>
      </div>
    </div>
  );
};