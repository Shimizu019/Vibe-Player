import React from 'react';

interface SectionHeaderProps {
  title: string;
  seeAllLabel?: string;
  onSeeAll?: () => void;
}

export const SectionHeader: React.FC<SectionHeaderProps> = ({
  title,
  seeAllLabel = 'See All',
  onSeeAll,
}) => {
  return (
    <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: 16 }}>
      <h3 className="screen-heading">{title}</h3>
      {onSeeAll && (
        <button
          onClick={onSeeAll}
          style={{
            background: 'none',
            border: 'none',
            color: 'var(--color-sand)',
            fontSize: 14,
            cursor: 'pointer',
            textDecoration: 'underline',
            textUnderlineOffset: '2px',
          }}
        >
          {seeAllLabel}
        </button>
      )}
    </div>
  );
};