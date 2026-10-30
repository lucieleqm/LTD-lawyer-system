import { Endereco } from '@/types/endereco';
import { Pessoa } from '@/types/pessoa';
import { Usuario } from '../usuarios/types';

export interface Advogado {
  id: number;
  dataDeNascimento: string;
  endereco: Endereco | null;
  pessoa: Pessoa;
  usuario: Usuario;
}

export interface AdvogadoOption {
  id: number;
  nome: string;
}

export interface AdvogadoListItem {
  id: number;
  nome: string;
  email: string;
  telefone: string;
  usuarioStatus: string;
}
