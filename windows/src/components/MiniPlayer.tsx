import React from 'react';
import { SkipBackIcon, SkipForwardIcon, PlayIcon, PauseIcon } from './icons';

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
  const coverPlaceholder = title.charAt(0).toUpperCase();
  return (
    <div className="mini-player-container">
      <div className="mini-progress" />
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
            width: 40,
            height: 40,
            borderRadius: 4,
            flexShrink: 0,
            overflow: 'hidden',
            border: '1px solid var(--color-border)',
          }}
        >
          {coverUrl ? (
            <img src={coverUrl} alt="Cover" style={{ width: '100%', height: '100%', objectFit: 'cover' }} />
          ) : (
            <div className="letter-tile letter-tile-espresso-dark">{coverPlaceholder}</div>
          )}
        </div>
        <div style={{ minWidth: 0 }}>
          <div
            className="size-row-title"
            style={{ whiteSpace: 'nowrap', overflow: 'hidden', textOverflow: 'ellipsis' }}
          >
            {title}
          </div>
          <div
            style={{
              fontSize: 'var(--size-meta)',
              color: 'var(--color-sand)',
              whiteSpace: 'nowrap',
              overflow: 'hidden',
              textOverflow: 'ellipsis',
            }}
          >
            {artist}
          </div>
        </div>
      </button>

      <div style={{ display: 'flex', alignItems: 'center', gap: 12 }}>
        <button
          onClick={onPrev}
          style={{ width: 48, height: 48 }}
          title="Previous"
        >
          <SkipBackIcon style={{ fill: 'currentColor', stroke: 'none', color: 'var(--color-sand)' }} />
        </button>
        <button
          onClick={onPlayPause}
          style={{
            width: 44,
            height: 44,
            borderRadius: '50%',
            backgroundColor: 'var(--color-sleeve)',
            color: 'var(--color-espresso)',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            cursor: 'pointer',
          }}
          title={isPlaying ? 'Pause' : 'Play'}
        >
          {isPlaying ? (
            <PauseIcon style={{ fill: 'currentColor', stroke: 'none', color: 'var(--color-espresso)' }} />
          ) : (
            <PlayIcon style={{ fill: 'currentColor', stroke: 'none', color: 'var(--color-espresso)' }} />
          )}
        </button>
        <button
          onClick={onNext}
          className="icon-button sleeve"
          style={{ width: 48, height: 48 }}
          title="Next"
        >
          <SkipForwardIcon style={{ fill: 'currentColor', stroke: 'none', color: 'var(--color-sand)' }} />
        </button>
      </div>
    </div>
  );
};