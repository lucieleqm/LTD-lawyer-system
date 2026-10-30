import { AreaDireito, ProcessoStatus, Tribunal } from './constants';

export interface ParteEnvolvida {
  id: number;
  nome: string;
  papel: string;
  especificacaoPapel?: string;
}

export interface Processo {
  id: number;
  numeroProcesso: string;
  numeroProcessoPje: string;
  areaDireito: AreaDireito;
  tribunal: Tribunal;
  assunto: string;
  vara: string;
  status: ProcessoStatus;
  estagiarioId: number;
  estagiarioNome: string;
  advogadoId: number;
  advogadoNome: string;
  assistidoId: number;
  assistidoNome: string;
  partesEnvolvidas: ParteEnvolvida[];
  prazoFinal: string;
  atualizadoEm: string;
  criadoEm: string;
}

export interface ListarProcessosParams {
  pageIndex: number;
  pageSize: number;
  termo?: string;
  areaDireito?: AreaDireito;
  tribunal?: Tribunal;
  status?: ProcessoStatus;
  sort?: string[];
}

export interface ProcessoListItem {
  id: number;
  numeroProcesso: string;
  numeroProcessoPje: string;
  tribunal: Tribunal;
  areaDireito: AreaDireito;
  status: ProcessoStatus;
  assistidoNome: string;
  advogadoNome: string;
  estagiarioNome: string;
  prazoFinal: string;
}
