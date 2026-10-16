import React from 'react';

interface RecordPlayerProps {
  isPlaying: boolean;
  coverUrl?: string;
  width?: number;
}

export const RecordPlayer: React.FC<RecordPlayerProps> = ({
  isPlaying,
  coverUrl,
  width = 300,
}) => {
  const recordSize = width;
  const centerCoverSize = recordSize * 0.43;
  const rotation = isPlaying ? 'rotate 3.6s linear infinite' : 'none';

  return (
    <div
      style={{
        position: 'relative',
        width: recordSize,
        height: recordSize,
        margin: '0 auto',
      }}
    >
      {/* Disc */}
      <div
        style={{
          width: recordSize,
          height: recordSize,
          borderRadius: '50%',
          backgroundColor: '#1A120E',
          border: '1px solid #3D2E24',
          position: 'absolute',
          top: 0,
          left: 0,
          zIndex: 1,
          animation: rotation,
          animationPlayState: isPlaying ? 'running' : 'paused',
        }}
      />
      {/* Grooves */}
      {[84, 66, 50].map((percent) => (
        <div
          key={percent}
          style={{
            position: 'absolute',
            top: '50%',
            left: '50%',
            width: `${(recordSize * percent) / 100}px`,
            height: `${(recordSize * percent) / 100}px`,
            borderRadius: '50%',
            border: '1px solid #1E1B18',
            transform: 'translate(-50%, -50%)',
            zIndex: 2,
            pointerEvents: 'none',
          }}
        />
      ))}
      {/* Center cover */}
      <div
        style={{
          width: centerCoverSize,
          height: centerCoverSize,
          borderRadius: '50%',
          backgroundColor: coverUrl ? 'transparent' : '#3D2E24',
          position: 'absolute',
          top: '50%',
          left: '50%',
          transform: 'translate(-50%, -50%)',
          zIndex: 3,
          animation: rotation,
          animationPlayState: isPlaying ? 'running' : 'paused',
          overflow: 'hidden',
          border: '1px solid #3D2E24',
        }}
      >
        {coverUrl ? (
          <img
            src={coverUrl}
            alt="Album art"
            style={{
              width: '100%',
              height: '100%',
              objectFit: 'cover',
            }}
          />
        ) : (
          <div style={{ width: '100%', height: '100%', display: 'flex', alignItems: 'center', justifyContent: 'center', color: '#665544', fontSize: 12 }}>
            No Art
          </div>
        )}
      </div>
      {/* Gold ring */}
      <div
        style={{
          width: recordSize,
          height: recordSize,
          borderRadius: '50%',
          border: '4px solid transparent',
          borderTop: `4px solid #C5A059`,
          position: 'absolute',
          top: 0,
          left: 0,
          zIndex: 4,
          pointerEvents: 'none',
          animation: rotation,
          animationPlayState: isPlaying ? 'running' : 'paused',
        }}
      />
    </div>
  );
};