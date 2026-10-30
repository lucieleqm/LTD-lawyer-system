import {
  IconArchive,
  IconArrowLeft,
  IconArrowRight,
  IconCheck,
  IconFilePlus,
  IconGavel,
  IconNotes,
} from '@tabler/icons-react';

export const TIPO_TRAMITACAO = {
  ABERTURA: 'ABERTURA',
  ENVIO_PARA_REVISAO: 'ENVIO_PARA_REVISAO',
  DEVOLUCAO_AO_ESTAGIARIO: 'DEVOLUCAO_AO_ESTAGIARIO',
  APROVACAO_DO_PROFESSOR: 'APROVACAO_DO_PROFESSOR',
  DEVOLUCAO_AO_PROFESSOR: 'DEVOLUCAO_AO_PROFESSOR',
  PROTOCOLO_REALIZADO: 'PROTOCOLO_REALIZADO',
  ARQUIVAMENTO_DEMANDA: 'ARQUIVAMENTO_DEMANDA',
  DESPACHO_INTERNO: 'DESPACHO_INTERNO',
} as const;

export type TipoTramitacao =
  (typeof TIPO_TRAMITACAO)[keyof typeof TIPO_TRAMITACAO];

export const tipoTramitacaoLabel: Record<TipoTramitacao, string> = {
  [TIPO_TRAMITACAO.ABERTURA]: 'Abertura',
  [TIPO_TRAMITACAO.ENVIO_PARA_REVISAO]: 'Envio para Revisão do Professor',
  [TIPO_TRAMITACAO.DEVOLUCAO_AO_ESTAGIARIO]:
    'Devolução para Ajustes do Estagiário',
  [TIPO_TRAMITACAO.APROVACAO_DO_PROFESSOR]: 'Aprovação e Envio ao Advogado',
  [TIPO_TRAMITACAO.DEVOLUCAO_AO_PROFESSOR]:
    'Devolução para Revisão do Professor',
  [TIPO_TRAMITACAO.PROTOCOLO_REALIZADO]: 'Petição Protocolada',
  [TIPO_TRAMITACAO.ARQUIVAMENTO_DEMANDA]: 'Arquivamento da Demanda',
  [TIPO_TRAMITACAO.DESPACHO_INTERNO]: 'Anotação/ Juntada de Documentos',
};

export const tipoTramitacaoColor: Record<TipoTramitacao, string> = {
  [TIPO_TRAMITACAO.ABERTURA]: 'institucional',
  [TIPO_TRAMITACAO.ENVIO_PARA_REVISAO]: 'yellow',
  [TIPO_TRAMITACAO.DEVOLUCAO_AO_ESTAGIARIO]: 'red',
  [TIPO_TRAMITACAO.APROVACAO_DO_PROFESSOR]: 'teal',
  [TIPO_TRAMITACAO.DEVOLUCAO_AO_PROFESSOR]: 'red',
  [TIPO_TRAMITACAO.PROTOCOLO_REALIZADO]: 'violet',
  [TIPO_TRAMITACAO.ARQUIVAMENTO_DEMANDA]: 'gray',
  [TIPO_TRAMITACAO.DESPACHO_INTERNO]: 'institucional',
};

export const tipoTramitacaoIcon: Record<TipoTramitacao, React.ReactNode> = {
  [TIPO_TRAMITACAO.ABERTURA]: <IconFilePlus size={12} />,
  [TIPO_TRAMITACAO.ENVIO_PARA_REVISAO]: <IconArrowRight size={12} />,
  [TIPO_TRAMITACAO.DEVOLUCAO_AO_ESTAGIARIO]: <IconArrowLeft size={12} />,
  [TIPO_TRAMITACAO.APROVACAO_DO_PROFESSOR]: <IconCheck size={12} />,
  [TIPO_TRAMITACAO.DEVOLUCAO_AO_PROFESSOR]: <IconArrowLeft size={12} />,
  [TIPO_TRAMITACAO.PROTOCOLO_REALIZADO]: <IconGavel size={12} />,
  [TIPO_TRAMITACAO.ARQUIVAMENTO_DEMANDA]: <IconArchive size={12} />,
  [TIPO_TRAMITACAO.DESPACHO_INTERNO]: <IconNotes size={12} />,
};
