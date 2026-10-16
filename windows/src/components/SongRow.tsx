import React from 'react';

interface SongRowProps {
  title: string;
  artist: string;
  album?: string;
  year?: number;
  duration?: number;
  onClick?: () => void;
  isSelected?: boolean;
}

export const SongRow: React.FC<SongRowProps> = ({
  title,
  artist,
  album,
  year,
  duration,
  onClick,
  isSelected = false,
}) => {
  const durationStr = duration
    ? new Date(duration).toISOString().substr(14, 5)
    : '--:--';

  return (
    <button
      onClick={onClick}
      className="song-row"
      style={{
        display: 'flex',
        alignItems: 'center',
        gap: 16,
        padding: '12px 16px',
        borderRadius: 4,
        backgroundColor: isSelected ? 'var(--color-surface)' : 'transparent',
        border: '1px solid transparent',
        width: '100%',
        textAlign: 'left',
        color: isSelected ? 'var(--color-gold)' : 'var(--color-sleeve)',
        transition: 'all 0.2s ease',
      }}
      onMouseEnter={(e) => {
        if (!isSelected) {
          e.currentTarget.style.backgroundColor = 'var(--color-surface)';
        }
      }}
      onMouseLeave={(e) => {
        if (!isSelected) {
          e.currentTarget.style.backgroundColor = 'transparent';
        }
      }}
    >
      <div style={{ width: 48, height: 48, borderRadius: 4, backgroundColor: 'var(--color-surface)', flexShrink: 0 }} />
      <div style={{ flex: 1, minWidth: 0 }}>
        <div style={{ fontSize: 14, fontWeight: 500, whiteSpace: 'nowrap', overflow: 'hidden', textOverflow: 'ellipsis' }}>{title}</div>
        <div style={{ fontSize: 12, color: 'var(--color-sand)', whiteSpace: 'nowrap', overflow: 'hidden', textOverflow: 'ellipsis' }}>
          {artist}
          {album ? ` • ${album}` : ''}
          {year ? ` • ${year}` : ''}
        </div>
      </div>
      <div style={{ fontSize: 12, color: 'var(--color-sand)', whiteSpace: 'nowrap' }}>{durationStr}</div>
    </button>
  );
};