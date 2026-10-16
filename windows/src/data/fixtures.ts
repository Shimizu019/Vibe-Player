/* Sample fixtures for W1 UI development — not real data sources */
export interface Song {
  id: string;
  title: string;
  artist: string;
  album?: string;
  year?: number;
  duration: number;
  coverUrl?: string;
  isDownloaded: boolean;
}

export const GOLDEN_HITS: Song[] = [
  { id: '1', title: 'Take Five', artist: 'Dave Brubeck', album: 'Time Out', year: 1959, duration: 324000, isDownloaded: true },
  { id: '2', title: 'What a Wonderful World', artist: 'Louis Armstrong', album: 'What a Wonderful World', year: 1967, duration: 136000, isDownloaded: true },
  { id: '3', title: 'Stand by Me', artist: 'Ben E. King', album: 'Don\'t Play That Song!', year: 1961, duration: 177000, isDownloaded: false },
  { id: '4', title: 'Imagine', artist: 'John Lennon', album: 'Imagine', year: 1971, duration: 183000, isDownloaded: true },
  { id: '5', title: 'Hotel California', artist: 'Eagles', album: 'Hotel California', year: 1976, duration: 390000, isDownloaded: false },
  { id: '6', title: 'Bohemian Rhapsody', artist: 'Queen', album: 'A Night at the Opera', year: 1975, duration: 354000, isDownloaded: true },
  { id: '7', title: 'Billie Jean', artist: 'Michael Jackson', album: 'Thriller', year: 1982, duration: 294000, isDownloaded: false },
  { id: '8', title: 'Sweet Child O\' Mine', artist: 'Guns N\' Roses', album: 'Appetite for Destruction', year: 1987, duration: 356000, isDownloaded: false },
];

export const RECENTLY_PLAYED: Song[] = [
  { id: 'r1', title: 'Stand by Me', artist: 'Ben E. King', year: 1961, duration: 177000, isDownloaded: false },
  { id: 'r2', title: 'Imagine', artist: 'John Lennon', year: 1971, duration: 183000, isDownloaded: true },
  { id: 'r3', title: 'Hotel California', artist: 'Eagles', year: 1976, duration: 390000, isDownloaded: false },
];

export const PLAYLISTS = [
  { id: 'p1', name: 'Golden 60s', songCount: 12, totalDurationMs: 2400000, coverColor: '#6B4A2E' },
  { id: 'p2', name: 'Sunday Jazz', songCount: 8, totalDurationMs: 1800000, coverColor: '#4A5D59' },
  { id: 'p3', name: 'Vinyl Classics', songCount: 20, totalDurationMs: 4500000, coverColor: '#8B4A4A' },
];

export const ERAS = ['1950s', '1960s', '1970s', '1980s'];