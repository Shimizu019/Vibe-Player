import React from 'react';
import { EllipsisVerticalIcon } from './icons';

interface SongRowProps {
  title: string;
  artist: string;
  album?: string;
  year?: number;
  onClick?: () => void;
  isSelected?: boolean;
}

export const SongRow: React.FC<SongRowProps> = ({
  title,
  artist,
  album,
  year,
  onClick,
  isSelected = false,
}) => {
  const coverPlaceholder = title.charAt(0).toUpperCase();
  const subline = [artist, album, year].filter(Boolean).join(' • ');

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
        height: 72,
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
      <div style={{ width: 48, height: 48, borderRadius: 4, flexShrink: 0 }}>
        <div className="letter-tile letter-tile-oxblood">{coverPlaceholder}</div>
      </div>
      <div style={{ flex: 1, minWidth: 0 }}>
        <div className="size-row-title" style={{ whiteSpace: 'nowrap', overflow: 'hidden', textOverflow: 'ellipsis' }}>{title}</div>
        <div style={{ fontSize: 'var(--size-meta)', color: 'var(--color-sand)', whiteSpace: 'nowrap', overflow: 'hidden', textOverflow: 'ellipsis' }}>{subline}</div>
      </div>
      <button
        onClick={(e) => {
          e.stopPropagation();
        }}
        className="icon-button sleeve"
        style={{ width: 48, height: 48, flexShrink: 0 }}
        title="More options"
      >
        <EllipsisVerticalIcon />
      </button>
    </button>
  );
};