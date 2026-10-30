import { createTheme, MantineColorsTuple } from '@mantine/core';

// cor customizada precisa de 10 tons (do mais claro ao mais escuro),
// o Mantine usa isso pra hover, texto, fundo, etc automaticamente
// https://mantine.dev/colors-generator/ -> use isso.
const institucional: MantineColorsTuple = [
  '#eef2f6', // 0 - mais claro (fundos sutis)
  '#dbe4ec',
  '#b3c5d6',
  '#89a5c0',
  '#6389ad',
  '#4a75a0',
  '#3d6a9a',
  '#2f5786', // 6 - O tom padrão usado
  '#254a78',
  '#173963', // 9 - mais escuro
];

export const theme = createTheme({
  primaryColor: 'institucional',
  colors: {
    institucional,
  },

  black: '#2b2d31',

  fontFamily: 'Inter, sans-serif',
  headings: {
    fontFamily: 'Inter, sans-serif',
    fontWeight: '600',
  },

  defaultRadius: 'md',

  spacing: {
    xs: '0.5rem',
    sm: '0.75rem',
    md: '1rem',
    lg: '1.5rem',
    xl: '2rem',
  },

  shadows: {
    sm: '0 1px 3px rgba(0, 0, 0, 0.08)',
    md: '0 4px 12px rgba(0, 0, 0, 0.1)',
  },
});
