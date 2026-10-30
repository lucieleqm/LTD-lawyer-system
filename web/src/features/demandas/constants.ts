export const ETAPA_DEMANDA = {
  ELABORACAO: 'ELABORACAO',
  REVISAO_PROFESSOR: 'REVISAO_PROFESSOR',
  VALIDACAO_ADVOGADO: 'VALIDACAO_ADVOGADO',
  PROTOCOLADA: 'PROTOCOLADA',
  ARQUIVADA: 'ARQUIVADA',
};

export type EtapaDemanda = (typeof ETAPA_DEMANDA)[keyof typeof ETAPA_DEMANDA];

export const etapaDemandaLabel: Record<EtapaDemanda, string> = {
  [ETAPA_DEMANDA.ELABORACAO]: 'Aguardando Estagiário',
  [ETAPA_DEMANDA.REVISAO_PROFESSOR]: 'Aguardando Professor',
  [ETAPA_DEMANDA.VALIDACAO_ADVOGADO]: 'Aguardando Advogado',
  [ETAPA_DEMANDA.PROTOCOLADA]: 'Protocolada',
  [ETAPA_DEMANDA.ARQUIVADA]: 'Arquivada',
};

export const etapaDemandaColor: Record<EtapaDemanda, string> = {
  [ETAPA_DEMANDA.ELABORACAO]: 'institucional',
  [ETAPA_DEMANDA.REVISAO_PROFESSOR]: 'institucional',
  [ETAPA_DEMANDA.VALIDACAO_ADVOGADO]: 'institucional',
  [ETAPA_DEMANDA.PROTOCOLADA]: 'institucional',
  [ETAPA_DEMANDA.ARQUIVADA]: 'gray',
};
