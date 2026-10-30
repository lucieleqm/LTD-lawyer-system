import { Tempestividade } from '@/constants/tempestividade';
import { EtapaDemanda } from './constants';
import { Role } from '@/constants/roles';
import { TipoTramitacao } from '@/constants/tipoTramitacao';

export interface Demanda {
  id: number;
  descricao: string;
  prazoFinal: string;
  prazoDocumentos: string;
  etapaAtual: EtapaDemanda;
  tempestividade: Tempestividade;
  dataAbertura: string;
  ultimaAtualizacao: string;
  advogadoId: number;
  advogadoNome: string;
  estagiarioId: number;
  estagiarioNome: string;
  professorId: number;
  professorNome: string;
}

export interface DemandaListItem {
  id: number;
  descricao: string;
  prazoFinal: string;
  prazoDocumentos: string;
  nomeAdvogado: string;
  nomeEstagiario: string;
  nomeProfessor: string;
  etapaAtual: EtapaDemanda;
  tempestividade: Tempestividade;
}

export interface Tramitacao {
  id: number;
  responsavelNome: string;
  responsavelRole: Role;
  tipoTramitacao: TipoTramitacao;
  observacoes: string;
  linkAnexo: string | null;
  criadoEm: string;
}

export interface TramitacaoDisponivel {
  tipo: TipoTramitacao;
  descricao: string;
}
