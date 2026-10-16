import React from 'react';
import { Song } from '../data/fixtures';
import { RecordPlayer } from '../components/RecordPlayer';
import { SkipBackIcon, SkipForwardIcon, PlayIcon, PauseIcon, ShuffleIcon, RepeatIcon, ChevronDownIcon } from '../components/icons';

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
        className="icon-button sleeve"
        onClick={onClose}
        style={{
          position: 'absolute',
          top: 16,
          left: 16,
          padding: 4,
        }}
        title="Close"
      >
        <ChevronDownIcon />
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

      <h2 className="size-title-m" style={{ marginBottom: 8 }}>{song.title}</h2>
      <p className="size-artist-l">{song.artist}</p>

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
        <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: 'var(--size-meta)', color: 'var(--color-sand)', marginTop: 8 }}>
          <span>01:42</span>
          <span>03:42</span>
        </div>
      </div>

      {/* Controls */}
      <div style={{ marginTop: 40, display: 'flex', alignItems: 'center', justifyContent: 'center', gap: 32 }}>
        <button className="icon-button sleeve" onClick={onPrev} title="Previous">
          <SkipBackIcon />
        </button>
        <button className="icon-button gold" onClick={onPlayPause} title={isPlaying ? 'Pause' : 'Play'}>
          {isPlaying ? <PauseIcon /> : <PlayIcon />}
        </button>
        <button className="icon-button sleeve" onClick={onNext} title="Next">
          <SkipForwardIcon />
        </button>
      </div>

      {/* Shuffle / Repeat */}
      <div style={{ marginTop: 24, display: 'flex', justifyContent: 'center', gap: 24, color: 'var(--color-sand)' }}>
        <button className="icon-button sleeve" style={{ width: 40, height: 40 }} title="Shuffle">
          <ShuffleIcon />
        </button>
        <button className="icon-button sleeve" style={{ width: 40, height: 40 }} title="Repeat">
          <RepeatIcon />
        </button>
      </div>
    </div>
  );
};