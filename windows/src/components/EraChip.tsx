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
      style={{
        display: 'inline-flex',
        alignItems: 'center',
        padding: '6px 12px',
        borderRadius: '9999px',
        backgroundColor: isSelected ? 'var(--color-gold)' : 'transparent',
        color: isSelected ? 'var(--color-espresso)' : 'var(--color-sleeve)',
        border: isSelected ? 'none' : '1px solid var(--color-border)',
        fontSize: 14,
        fontWeight: 500,
        cursor: 'pointer',
        transition: 'all 0.2s ease',
      }}
    >
      {label}
    </button>
  );
};