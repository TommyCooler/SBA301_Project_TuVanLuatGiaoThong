'use client';

import { useTheme } from '@/context/ThemeContext';
import { FiSun, FiMoon } from 'react-icons/fi';

export function ThemeToggle_C() {
  const { theme, toggleTheme } = useTheme();

  return (
    <button
      onClick={toggleTheme}
      className="p-2 rounded-lg bg-gray-200 dark:bg-gray-700 hover:bg-gray-300 dark:hover:bg-gray-600 transition-colors duration-200"
      aria-label="Toggle theme"
    >
      {theme === 'light' ? (
        <FiMoon className="h-5 w-5 text-gray-700 dark:text-gray-300" />
      ) : (
        <FiSun className="h-5 w-5 text-gray-700 dark:text-gray-300" />
      )}
    </button>
  );
}