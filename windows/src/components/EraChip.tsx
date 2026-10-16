import React from 'react';

interface EraChipProps {
  label: string;
  isSelected: boolean;
  onClick: () => void;
}

export const EraChip: React.FC<EraChipProps> = ({ label, isSelected, onClick }) => {
  return (
    <button
      onClick={onClick}
      className="era-chip"
      style={{
        ...(isSelected && {
          backgroundColor: 'var(--color-gold)',
          color: 'var(--color-espresso)',
          border: 'none',
        }),
      }}
    >
      {label}
    </button>
  );
};