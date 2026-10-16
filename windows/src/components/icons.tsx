/* Lucide-style line icons: 24px, 2px stroke, round caps/joins, currentColor */
export const HomeIcon = () => (
  <svg viewBox="0 0 24 24" aria-hidden="true">
    <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z" />
    <polyline points="9 22 9 12 15 12 15 22" />
  </svg>
);

export const SearchIcon = (props: React.SVGProps<SVGSVGElement>) => (
  <svg {...props} viewBox="0 0 24 24" aria-hidden="true">
    <circle cx="11" cy="11" r="8" />
    <path d="M21 21l-4.35-4.35" />
  </svg>
);

export const LibraryIcon = (props: React.SVGProps<SVGSVGElement>) => (
  <svg {...props} viewBox="0 0 24 24" aria-hidden="true">
    <path d="M2 21a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h8l5 5v11a2 2 0 0 1-2 2z" />
  </svg>
);

export const ChevronDownIcon = (props: React.SVGProps<SVGSVGElement>) => (
  <svg {...props} viewBox="0 0 24 24" aria-hidden="true">
    <polyline points="6 9 12 15 18 9" />
  </svg>
);

export const ChevronLeftIcon = (props: React.SVGProps<SVGSVGElement>) => (
  <svg {...props} viewBox="0 0 24 24" aria-hidden="true">
    <polyline points="15 18 9 12 15 6" />
  </svg>
);

export const ChevronRightIcon = (props: React.SVGProps<SVGSVGElement>) => (
  <svg {...props} viewBox="0 0 24 24" aria-hidden="true">
    <polyline points="9 18 15 12 9 6" />
  </svg>
);

export const EllipsisIcon = (props: React.SVGProps<SVGSVGElement>) => (
  <svg {...props} viewBox="0 0 24 24" aria-hidden="true">
    <circle cx="12" cy="5" r="1" />
    <circle cx="12" cy="12" r="1" />
    <circle cx="12" cy="19" r="1" />
  </svg>
);

export const EllipsisVerticalIcon = (props: React.SVGProps<SVGSVGElement>) => (
  <svg {...props} viewBox="0 0 24 24" aria-hidden="true">
    <circle cx="12" cy="5" r="1" />
    <circle cx="12" cy="12" r="1" />
    <circle cx="12" cy="19" r="1" />
  </svg>
);

export const ShuffleIcon = (props: React.SVGProps<SVGSVGElement>) => (
  <svg {...props} viewBox="0 0 24 24" aria-hidden="true">
    <path d="M17 2v2a7 7 0 0 0-7 7h2a5 5 0 0 1 5-5M2 22v-2a7 7 0 0 1 7-7h2a5 5 0 0 0-5 5" />
  </svg>
);

export const SkipBackIcon = (props: React.SVGProps<SVGSVGElement>) => (
  <svg {...props} viewBox="0 0 24 24" aria-hidden="true">
    <polygon points="1 15 15 8 1 1" />
    <path d="M5 15v14l6-7-6-7z" />
  </svg>
);

export const SkipForwardIcon = (props: React.SVGProps<SVGSVGElement>) => (
  <svg {...props} viewBox="0 0 24 24" aria-hidden="true">
    <polygon points="23 15 9 8 23 1" />
    <path d="M19 15v14l6-7 6-7z" />
  </svg>
);

export const PlayIcon = (props: React.SVGProps<SVGSVGElement>) => (
  <svg {...props} viewBox="0 0 24 24" aria-hidden="true">
    <polygon points="5 3 19 12 5 21" />
  </svg>
);

export const PauseIcon = (props: React.SVGProps<SVGSVGElement>) => (
  <svg {...props} viewBox="0 0 24 24" aria-hidden="true">
    <rect x="6" y="4" width="4" height="16" rx="1" />
    <rect x="14" y="4" width="4" height="16" rx="1" />
  </svg>
);

export const RepeatIcon = () => (
  <svg viewBox="0 0 24 24" aria-hidden="true">
    <path d="M17 1v2a7 7 0 0 0-7 7h2a5 5 0 0 1 5-5v-2M7 21v-2a7 7 0 0 1 7-7h2a5 5 0 0 0-5 5v2" />
  </svg>
);

export const CircleXIcon = (props: React.SVGProps<SVGSVGElement>) => (
  <svg {...props} viewBox="0 0 24 24" aria-hidden="true">
    <circle cx="12" cy="12" r="10" />
    <path d="M15 9l-6 6" />
    <path d="M9 9l6 6" />
  </svg>
);

export const BellIcon = () => (
  <svg viewBox="0 0 24 24" aria-hidden="true">
    <path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9" />
    <path d="M13.73 21a2 2 0 0 1-3.46 0" />
  </svg>
);

export const DownloadIcon = () => (
  <svg viewBox="0 0 24 24" aria-hidden="true">
    <path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4" />
    <polyline points="7 10 12 15 17 10" />
    <line x1="12" y1="15" x2="12" y2="3" />
  </svg>
);

export const CirclePlusIcon = () => (
  <svg viewBox="0 0 24 24" aria-hidden="true">
    <circle cx="12" cy="12" r="10" />
    <path d="M12 8v8" />
    <path d="M8 12h8" />
  </svg>
);

export const CircleCheckIcon = () => (
  <svg viewBox="0 0 24 24" aria-hidden="true">
    <circle cx="12" cy="12" r="10" />
    <path d="M9 12l2 2 4-4" />
  </svg>
);

export const SmartphoneIcon = () => (
  <svg viewBox="0 0 24 24" aria-hidden="true">
    <path d="M18 12h3a1 1 0 0 0 1-1v-8a1 1 0 0 0-1-1H3a1 1 0 0 0-1 1v16a1 1 0 0 0 1 1h9a1 1 0 0 0 1-1v-7a1 1 0 0 0-1-1z" />
  </svg>
);

export const MusicIcon = () => (
  <svg viewBox="0 0 24 24" aria-hidden="true">
    <path d="M9 18V5l12-2v13" />
    <circle cx="6" cy="18" r="3" />
    <circle cx="18" cy="16" r="3" />
  </svg>
);

export const HeartIcon = () => (
  <svg viewBox="0 0 24 24" aria-hidden="true">
    <path d="M19 14c1.49-1.46 3-3.21 3-5.5A5.5 5.5 0 0 0 16.5 3c-1.76 0-3.05 1.56-3 3.41 0.05-1.25 1.1-3 3-3 1.9 0 3 1.5 3 3 0 1.23-0.46 2.52-1 3.57-0.54-1.05-1.45-1.57-2-1.57-0.55 0-1.45 0.52-2 1.57-0.54-1.05-1.45-1.57-2-1.57-1.9 0-3 1.77-3 3 0 2.29 1.51 4.04 3 5.5" />
  </svg>
);

export const Trash2Icon = () => (
  <svg viewBox="0 0 24 24" aria-hidden="true">
    <polyline points="3 6 5 6 21 6" />
    <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2" />
    <line x1="10" y1="11" x2="10" y2="17" />
    <line x1="14" y1="11" x2="14" y2="17" />
  </svg>
);

export const InfoIcon = () => (
  <svg viewBox="0 0 24 24" aria-hidden="true">
    <circle cx="12" cy="12" r="10" />
    <path d="M11 17h2v-2h-2z" />
    <path d="M11 9h2V7h-2z" />
  </svg>
);

export const SettingsIcon = () => (
  <svg viewBox="0 0 24 24" aria-hidden="true">
    <path d="M19.4 15.1c.5-.5.5-1.3 0-1.8l-1.4-1.4c.6-.6.6-1.6 0-2.2l1.4-1.4c-.5-.5-1.3-.5-1.8 0l-1.4-1.4c-.6-.6-1.6-.6-2.2 0L13.4 8c-.6-.6-1.6-.6-2.2 0l-1.4 1.4c-.5.5-.5 1.3 0 1.8l-1.4 1.4c-.6.6-.6 1.6 0 2.2l1.4 1.4c.5.5 1.3.5 1.8 0l1.4-1.4c.6.6 1.6.6 2.2 0l1.4 1.4z" />
    <circle cx="12" cy="12" r="3" />
  </svg>
);

export const RotateCcwIcon = () => (
  <svg viewBox="0 0 24 24" aria-hidden="true">
    <path d="M1 4v6h6" />
    <path d="M14 4A8 8 0 0 0 4 20a8 8 0 0 0 8-8h6" />
  </svg>
);

export const LayoutGridIcon = () => (
  <svg viewBox="0 0 24 24" aria-hidden="true">
    <rect x="3" y="3" width="7" height="7" rx="1" />
    <rect x="14" y="3" width="7" height="7" rx="1" />
    <rect x="14" y="14" width="7" height="7" rx="1" />
    <rect x="3" y="14" width="7" height="7" rx="1" />
  </svg>
);

export const CloudOffIcon = () => (
  <svg viewBox="0 0 24 24" aria-hidden="true">
    <path d="M22.61 16.95A10 10 0 0 0 18 2v-2C18.43 1.37 22.11 2.92 22.61 16.95" />
    <path d="M12 13a3 3 0 0 1-3-3V7a8 8 0 0 1 8-8h3M21 19v-2a6 6 0 0 0-2.35-5.12" />
  </svg>
);

export const ListStartIcon = () => (
  <svg viewBox="0 0 24 24" aria-hidden="true">
    <line x1="8" y1="6" x2="16" y2="6" />
    <line x1="13" y1="6" x2="13" y2="12" />
    <line x1="8" y1="12" x2="16" y2="12" />
    <line x1="16" y1="6" x2="16" y2="12" />
    <line x1="8" y1="18" x2="16" y2="18" />
    <line x1="13" y1="18" x2="13" y2="12" />
  </svg>
);

export const Share2Icon = () => (
  <svg viewBox="0 0 24 24" aria-hidden="true">
    <circle cx="18" cy="5" r="3" />
    <circle cx="6" cy="12" r="3" />
    <circle cx="18" cy="19" r="3" />
    <line x1="8.59" y1="13.51" x2="15.42" y2="17.49" />
    <line x1="15.41" y1="6.51" x2="8.59" y2="10.49" />
  </svg>
);

export const ListPlusIcon = () => (
  <svg viewBox="0 0 24 24" aria-hidden="true">
    <line x1="12" y1="5" x2="19" y2="5" />
    <line x1="12" y1="19" x2="19" y2="19" />
    <line x1="5" y1="12" x2="19" y2="12" />
    <line x1="12" y1="7" x2="12" y2="17" />
  </svg>
);

export const AudioLinesIcon = () => (
  <svg viewBox="0 0 24 24" aria-hidden="true">
    <path d="M1 13h4M1 18h4M12 1v4M12 17h4M17 9v6M21 3v12h-4" />
  </svg>
);