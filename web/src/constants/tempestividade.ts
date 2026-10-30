export const TEMPESTIVIDADE = {
  DENTRO_DO_PRAZO: 'DENTRO_DO_PRAZO',
  FORA_DO_PRAZO: 'FORA_DO_PRAZO',
};

export type Tempestividade =
  (typeof TEMPESTIVIDADE)[keyof typeof TEMPESTIVIDADE];

export const tempestividadeLabel: Record<Tempestividade, string> = {
  [TEMPESTIVIDADE.DENTRO_DO_PRAZO]: 'Dentro do Prazo',
  [TEMPESTIVIDADE.FORA_DO_PRAZO]: 'Fora do Prazo',
};

export const tempestividadeColor: Record<Tempestividade, string> = {
  [TEMPESTIVIDADE.DENTRO_DO_PRAZO]: 'teal',
  [TEMPESTIVIDADE.FORA_DO_PRAZO]: 'red',
};
