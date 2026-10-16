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
        borderRadius: 20,
        backgroundColor: isSelected ? '#C5A059' : 'transparent',
        color: isSelected ? '#2B1E16' : '#E6D8CC',
        border: isSelected ? 'none' : '1px solid #3D2E24',
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