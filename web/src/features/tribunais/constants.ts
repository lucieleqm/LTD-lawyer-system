export const ESFERA_JUSTICA = {
  ESTADUAL: 'ESTADUAL',
  FEDERAL: 'FEDERAL',
  TRABALHISTA: 'TRABALHISTA',
  ELEITORAL: 'ELEITORAL',
} as const;

export type EsferaJustica =
  (typeof ESFERA_JUSTICA)[keyof typeof ESFERA_JUSTICA];

export const esferaJusticaLabel: Record<EsferaJustica, string> = {
  [ESFERA_JUSTICA.ESTADUAL]: 'Estadual',
  [ESFERA_JUSTICA.FEDERAL]: 'Federal',
  [ESFERA_JUSTICA.TRABALHISTA]: 'Trabalhista',
  [ESFERA_JUSTICA.ELEITORAL]: 'Eleitoral',
};
