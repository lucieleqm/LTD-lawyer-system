// ==========================================
// ÁREA DO DIREITO
// ==========================================
export const AREA_DIREITO = {
  CIVIL: 'CIVIL',
  TRABALHISTA: 'TRABALHISTA',
  PREVIDENCIARIO: 'PREVIDENCIARIO',
} as const;

export type AreaDireito = (typeof AREA_DIREITO)[keyof typeof AREA_DIREITO];

export const areaDireitoLabel: Record<AreaDireito, string> = {
  [AREA_DIREITO.CIVIL]: 'Direito Civil',
  [AREA_DIREITO.TRABALHISTA]: 'Direito Trabalhista',
  [AREA_DIREITO.PREVIDENCIARIO]: 'Direito Previdenciário',
  // TODO: Verificar necessidade da adição das demais áreas
  // FAMILIA_E_SUCESSOES: 'Direito de Família e Sucessões',
  // CONSUMIDOR: 'Direito do Consumidor',
  // PENAL: 'Direito Penal',
  // TRIBUTARIO: 'Direito Tributário',
  // EMPRESARIAL: 'Direito Empresarial',
  // ADMINISTRATIVO: 'Direito Administrativo',
  // CONSTITUCIONAL: 'Direito Constitucional',
  // DIGITAL: 'Direito Digital',
  // AMBIENTAL: 'Direito Ambiental',
  // ELEITORAL: 'Direito Eleitoral',
  // INTERNACIONAL: 'Direito Internacional',
};

// ==========================================
// PAPEL NO PROCESSO
// ==========================================
export const PAPEL_NO_PROCESSO = {
  AUTOR_REQUERENTE: 'AUTOR_REQUERENTE',
  REU_REQUERIDO: 'REU_REQUERIDO',
  TESTEMUNHA: 'TESTEMUNHA',
  JUIZ: 'JUIZ',
  LITISCONSORTE_ATIVO: 'LITISCONSORTE_ATIVO',
  LITISCONSORTE_PASSIVO: 'LITISCONSORTE_PASSIVO',
  TERCEIRO_INTERESSADO: 'TERCEIRO_INTERESSADO',
  MINISTERIO_PUBLICO: 'MINISTERIO_PUBLICO',
  OUTROS: 'OUTROS',
} as const;

export type PapelNoProcesso =
  (typeof PAPEL_NO_PROCESSO)[keyof typeof PAPEL_NO_PROCESSO];

export const papelNoProcessoLabel: Record<PapelNoProcesso, string> = {
  [PAPEL_NO_PROCESSO.AUTOR_REQUERENTE]: 'Autor / Requerente',
  [PAPEL_NO_PROCESSO.REU_REQUERIDO]: 'Réu / Requerido',
  [PAPEL_NO_PROCESSO.TESTEMUNHA]: 'Testemunha',
  [PAPEL_NO_PROCESSO.JUIZ]: 'Juiz',
  [PAPEL_NO_PROCESSO.LITISCONSORTE_ATIVO]: 'Litisconsorte Ativo',
  [PAPEL_NO_PROCESSO.LITISCONSORTE_PASSIVO]: 'Litisconsorte Passivo',
  [PAPEL_NO_PROCESSO.TERCEIRO_INTERESSADO]: 'Terceiro Interessado',
  [PAPEL_NO_PROCESSO.MINISTERIO_PUBLICO]: 'Ministério Público',
  [PAPEL_NO_PROCESSO.OUTROS]: 'Outros',
};

// ==========================================
// STATUS DO PROCESSO
// ==========================================
export const PROCESSO_STATUS = {
  TRAMITANDO: 'TRAMITANDO',
  SUSPENSO: 'SUSPENSO',
  ARQUIVADO: 'ARQUIVADO',
} as const;

export type ProcessoStatus =
  (typeof PROCESSO_STATUS)[keyof typeof PROCESSO_STATUS];

export const processoStatusLabel: Record<ProcessoStatus, string> = {
  [PROCESSO_STATUS.TRAMITANDO]: 'Tramitando',
  [PROCESSO_STATUS.SUSPENSO]: 'Suspenso',
  [PROCESSO_STATUS.ARQUIVADO]: 'Arquivado',
};

// ==========================================
// TRIBUNAL
// ==========================================
export const TRIBUNAL = {
  // STF: 'STF',
  // STJ: 'STJ',
  TJMA: 'TJMA',
  TRF1: 'TRF1',
  TRT16: 'TRT16',
} as const;

export type Tribunal = (typeof TRIBUNAL)[keyof typeof TRIBUNAL];

export const tribunalLabel: Record<Tribunal, string> = {
  // [TRIBUNAL.STF]: 'Supremo Tribunal Federal',
  // [TRIBUNAL.STJ]: 'Superior Tribunal de Justiça',
  [TRIBUNAL.TJMA]: 'Tribunal de Justiça do Maranhão',
  [TRIBUNAL.TRF1]: 'Tribunal Regional Federal da 1ª Região',
  [TRIBUNAL.TRT16]: 'Tribunal Regional do Trabalho da 16ª Região',
};
