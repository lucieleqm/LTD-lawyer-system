export const SITUACAO_FILTRO = {
  ATIVOS: 'ATIVOS',
  ARQUIVADOS: 'ARQUIVADOS',
  TODOS: 'TODOS',
} as const;

export type SituacaoFiltro =
  (typeof SITUACAO_FILTRO)[keyof typeof SITUACAO_FILTRO];

export const situacaoFiltroLabel: Record<SituacaoFiltro, string> = {
  [SITUACAO_FILTRO.ATIVOS]: 'Ativos',
  [SITUACAO_FILTRO.ARQUIVADOS]: 'Arquivados',
  [SITUACAO_FILTRO.TODOS]: 'Todos',
};

export const SITUACAO_REGISTRO = {
  ativo: { label: 'Ativo', color: 'green' },
  arquivado: { label: 'Arquivado', color: 'gray' },
} as const;

export const situacaoRegistro = (ativo: boolean) =>
  ativo ? SITUACAO_REGISTRO.ativo : SITUACAO_REGISTRO.arquivado;
