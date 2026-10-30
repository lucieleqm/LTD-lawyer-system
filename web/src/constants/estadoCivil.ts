// no momento apenas 'Assistido' utiliza esse enum/constant
export const ESTADO_CIVIL = {
  SOLTEIRO: 'SOLTEIRO',
  CASADO: 'CASADO',
  DIVORCIADO: 'DIVORCIADO',
  VIUVO: 'VIUVO',
  SEPARADO_JUDICIALMENTE: 'SEPARADO_JUDICIALMENTE',
} as const;

export type EstadoCivil = (typeof ESTADO_CIVIL)[keyof typeof ESTADO_CIVIL];

export const estadoCivilLabel: Record<EstadoCivil, string> = {
  [ESTADO_CIVIL.SOLTEIRO]: 'Solteiro(a)',
  [ESTADO_CIVIL.CASADO]: 'Casado(a)',
  [ESTADO_CIVIL.DIVORCIADO]: 'Divorciado(a)',
  [ESTADO_CIVIL.VIUVO]: 'Viúvo(a)',
  [ESTADO_CIVIL.SEPARADO_JUDICIALMENTE]: 'Separado(a) Judicialmente',
};
