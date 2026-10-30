import { EstadoCivil } from '@/constants/estadoCivil';
import { Endereco } from '@/types/endereco';
import { Pessoa } from '@/types/pessoa';

export interface Assistido {
  id: number;
  matricula: string;
  profissao: string;
  nacionalidade: string;
  naturalidade: string;
  estadoCivil: EstadoCivil;
  criadoEm: string;
  pessoa: Pessoa;
  endereco: Endereco | null;
}

export interface AssistidoListItem {
  id: number;
  nome: string;
  matricula: string;
  telefone: string;
}

export interface AssistidoOption {
  id: number;
  nome: string;
}
