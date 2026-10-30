import { api } from '@/lib/api/axios';
import { Usuario } from '../types';
import { UsuarioStatus } from '@/constants/usuarioStatus';

export const usuariosService = {
  me: async (): Promise<Usuario> => {
    const { data } = await api.get('/usuarios/me');
    return data;
  },

  alterarStatus: async (
    id: number,
    usuarioStatus: UsuarioStatus,
  ): Promise<void> => {
    await api.patch(`/usuarios/${id}/status`, usuarioStatus, {
      headers: { 'Content-Type': 'application/json' },
    });
  },
};
