import { createContext, useState, useEffect, ReactNode } from 'react';

import { Usuario } from '@/features/usuarios/types';
import { clearToken, getToken, setToken } from '@/lib/storage/tokenStorage';
import { authService } from '../services/authService';
import { LoginRequest } from '../schema';
import { usuariosService } from '@/features/usuarios/services/usuariosService';

interface AuthContextData {
  user: Usuario | null;
  isAuthenticated: boolean;
  isLoading: boolean;
  login: (dados: LoginRequest) => Promise<void>;
  logout: () => void;
}

export const AuthContext = createContext<AuthContextData | undefined>(
  undefined,
);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<Usuario | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    async function carregarUsuario() {
      const token = getToken();
      if (!token) {
        setIsLoading(false);
        return;
      }

      try {
        const usuario = await usuariosService.me();
        setUser(usuario);
      } catch {
        clearToken();
        setUser(null);
      } finally {
        setIsLoading(false);
      }
    }
    carregarUsuario();
  }, []);

  async function login(dados: LoginRequest) {
    const { token, tipo, expiraEm } = await authService.login(dados);
    setToken(token, tipo, expiraEm);

    const usuario = await usuariosService.me();
    setUser(usuario);
  }

  function logout() {
    clearToken();
    // await authService.logout();
    setUser(null);
  }

  return (
    <AuthContext.Provider
      value={{
        user,
        isAuthenticated: !!user,
        isLoading,
        login,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};
