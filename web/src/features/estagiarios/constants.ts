export const PERIODO_ESTAGIO = {
  ESTAGIO_I: 'ESTAGIO_I',
  ESTAGIO_II: 'ESTAGIO_II',
  ESTAGIO_III: 'ESTAGIO_III',
  ESTAGIO_IV: 'ESTAGIO_IV',
};

export type PeriodoEstagio =
  (typeof PERIODO_ESTAGIO)[keyof typeof PERIODO_ESTAGIO];

export const periodoEstagioLabel: Record<PeriodoEstagio, string> = {
  [PERIODO_ESTAGIO.ESTAGIO_I]: 'Estágio I',
  [PERIODO_ESTAGIO.ESTAGIO_II]: 'Estágio II',
  [PERIODO_ESTAGIO.ESTAGIO_III]: 'Estágio III',
  [PERIODO_ESTAGIO.ESTAGIO_IV]: 'Estágio IV',
};
