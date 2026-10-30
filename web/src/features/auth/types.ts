import { Usuario } from '../usuarios/types';

export interface LoginResponse {
  token: string;
  tipo: string;
  expiraEm: string;
}
export interface AuthState {
  user: Usuario | null;
  isAuthenticated: boolean;
  isLoading: boolean;
}
