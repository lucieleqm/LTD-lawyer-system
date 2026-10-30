import { api } from '@/lib/api/axios';
import { LoginResponse } from '../types';
import {
  EsqueciSenhaRequest,
  LoginRequest,
  RedefinirSenhaRequest,
} from '../schema';

export const authService = {
  login: async (dados: LoginRequest): Promise<LoginResponse> => {
    const { data } = await api.post('/auth/login', dados);
    return data;
  },

  logout: async (): Promise<void> => {
    await api.post('/auth/logout');
  },

  esqueciSenha: async (dados: EsqueciSenhaRequest): Promise<void> => {
    await api.post('/auth/esqueci-senha', dados);
  },

  redefinirSenha: async (
    token: string,
    dados: RedefinirSenhaRequest,
  ): Promise<void> => {
    await api.post('/auth/redefinir-senha', {
      token,
      novaSenha: dados.novaSenha,
    });
  },
};
