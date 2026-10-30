import { Role } from '@/constants/roles';
import { UsuarioStatus } from '@/constants/usuarioStatus';

export interface Usuario {
  id: number;
  login: string;
  role: Role;
  status: UsuarioStatus;
  criadoEm: string;
  desativadoEm: string;
  bloqueadoEm: string;
}
