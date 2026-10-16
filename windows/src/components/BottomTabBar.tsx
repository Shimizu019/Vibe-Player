import React from 'react';

interface BottomTabBarProps {
  activeTab: string;
  onTabChange: (tab: string) => void;
}

const TABS = [
  { id: 'home', label: 'Home', icon: '🏠' },
  { id: 'search', label: 'Search', icon: '🔍' },
  { id: 'library', label: 'Library', icon: '📚' },
];

export const BottomTabBar: React.FC<BottomTabBarProps> = ({ activeTab, onTabChange }) => {
  return (
    <div
      style={{
        position: 'fixed',
        bottom: 72,
        left: 0,
        right: 0,
        height: 56,
        backgroundColor: 'var(--color-espresso)',
        borderTop: '1px solid var(--color-border)',
        display: 'flex',
        justifyContent: 'center',
        zIndex: 90,
      }}
    >
      {TABS.map((tab) => (
        <button
          key={tab.id}
          onClick={() => onTabChange(tab.id)}
          style={{
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            gap: 4,
            padding: 8,
            color: activeTab === tab.id ? 'var(--color-gold)' : 'var(--color-sand)',
            background: activeTab === tab.id ? 'var(--color-surface)' : 'transparent',
            border: 'none',
            cursor: 'pointer',
            fontSize: 11,
            minWidth: 60,
          }}
        >
          <span style={{ fontSize: 20 }}>{tab.icon}</span>
          <span>{tab.label}</span>
        </button>
      ))}
    </div>
  );
};