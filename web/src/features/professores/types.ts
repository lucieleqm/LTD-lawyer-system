import { Pessoa } from '@/types/pessoa';
import { Usuario } from '../usuarios/types';

export interface Professor {
  id: number;
  pessoa: Pessoa;
  usuario: Usuario
}

export interface ProfessorOption {
  id: number;
  nome: string;
}

export interface ProfessorListItem {
  id: number;
  nome: string;
  email: string;
  telefone: string;
  usuarioStatus: string;
}
