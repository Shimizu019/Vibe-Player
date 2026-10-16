import React from 'react';
import { HomeIcon, SearchIcon, LibraryIcon } from './icons';

interface BottomTabBarProps {
  activeTab: string;
  onTabChange: (tab: string) => void;
}

const TABS = [
  { id: 'home', label: 'Home', Icon: HomeIcon },
  { id: 'search', label: 'Search', Icon: SearchIcon },
  { id: 'library', label: 'Library', Icon: LibraryIcon },
];

const TabIcon: React.FC<{ Icon: (props: React.SVGProps<SVGSVGElement>) => React.JSX.Element; active: boolean }> = ({ Icon, active }) => (
  <span style={{ width: 20, height: 20, display: 'inline-flex', alignItems: 'center', justifyContent: 'center', color: active ? 'var(--color-gold)' : 'var(--color-sand)' }}>
    <Icon />
  </span>
);

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
      {TABS.map((tab) => {
        const active = activeTab === tab.id;
        const Icon = tab.Icon;
        return (
          <button
            key={tab.id}
            onClick={() => onTabChange(tab.id)}
            style={{
              display: 'flex',
              flexDirection: 'column',
              alignItems: 'center',
              gap: 4,
              padding: 8,
              color: active ? 'var(--color-gold)' : 'var(--color-sand)',
              background: active ? 'var(--color-surface)' : 'transparent',
              border: 'none',
              cursor: 'pointer',
              fontSize: 11,
              minWidth: 60,
            }}
          >
            <TabIcon Icon={Icon} active={active} />
            <span>{tab.label}</span>
          </button>
        );
      })}
    </div>
  );
};