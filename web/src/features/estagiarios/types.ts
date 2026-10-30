import { Pessoa } from '@/types/pessoa';
import { Usuario } from '../usuarios/types';

export interface Estagiario {
  id: number;
  matricula: string;
  periodoEstagio: string;
  pessoa: Pessoa;
  usuario: Usuario;
}

export interface EstagiarioOption {
  id: number;
  nome: string;
}

export interface EstagiarioListItem {
  id: number;
  nome: string;
  matricula: string;
  periodoEstagio: string;
  usuarioStatus: string;
}
