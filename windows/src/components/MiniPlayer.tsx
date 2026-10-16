import React from 'react';

interface MiniPlayerProps {
  isPlaying: boolean;
  title: string;
  artist: string;
  coverUrl?: string;
  onPlayPause: () => void;
  onPrev: () => void;
  onNext: () => void;
  onOpenNowPlaying: () => void;
}

export const MiniPlayer: React.FC<MiniPlayerProps> = ({
  isPlaying,
  title,
  artist,
  coverUrl,
  onPlayPause,
  onPrev,
  onNext,
  onOpenNowPlaying,
}) => {
  return (
    <div
      style={{
        position: 'fixed',
        bottom: 0,
        left: 0,
        right: 0,
        height: 72,
        backgroundColor: 'var(--color-surface)',
        borderTop: '1px solid var(--color-border)',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
        padding: '0 24px',
        zIndex: 100,
        gap: 16,
      }}
    >
      <button
        onClick={onOpenNowPlaying}
        style={{
          display: 'flex',
          alignItems: 'center',
          gap: 12,
          flex: 1,
          minWidth: 0,
        }}
      >
        <div
          style={{
            width: 48,
            height: 48,
            borderRadius: 4,
            backgroundColor: coverUrl ? 'transparent' : 'var(--color-surface)',
            flexShrink: 0,
            overflow: 'hidden',
            border: '1px solid var(--color-border)',
          }}
        >
          {coverUrl ? (
            <img src={coverUrl} alt="Cover" style={{ width: '100%', height: '100%', objectFit: 'cover' }} />
          ) : (
            <div style={{ width: '100%', height: '100%', display: 'flex', alignItems: 'center', justifyContent: 'center', color: 'var(--color-sand)', fontSize: 10 }}>No Art</div>
          )}
        </div>
        <div style={{ minWidth: 0 }}>
          <div style={{ fontSize: 14, fontWeight: 500, whiteSpace: 'nowrap', overflow: 'hidden', textOverflow: 'ellipsis' }}>{title}</div>
          <div style={{ fontSize: 12, color: 'var(--color-sand)', whiteSpace: 'nowrap', overflow: 'hidden', textOverflow: 'ellipsis' }}>{artist}</div>
        </div>
      </button>

      <div style={{ display: 'flex', alignItems: 'center', gap: 16 }}>
        <button
          onClick={onPrev}
          style={{ color: 'var(--color-sand)', fontSize: 20, padding: 4 }}
          title="Previous"
        >
          ⏮
        </button>
        <button
          onClick={onPlayPause}
          style={{
            width: 48,
            height: 48,
            borderRadius: '50%',
            backgroundColor: 'var(--color-gold)',
            color: 'var(--color-espresso)',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            fontSize: 24,
            lineHeight: 1,
          }}
          title={isPlaying ? 'Pause' : 'Play'}
        >
          {isPlaying ? '⏸' : '▶'}
        </button>
        <button
          onClick={onNext}
          style={{ color: 'var(--color-sand)', fontSize: 20, padding: 4 }}
          title="Next"
        >
          ⏭
        </button>
      </div>
    </div>
  );
};