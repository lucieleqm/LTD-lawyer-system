import { notifications } from '@mantine/notifications';
import { clearToken } from '../storage/tokenStorage';
import { api } from './axios';

api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      clearToken();
      window.location.href = '/login';
    }

    if (error.response?.status === 403) {
      const detail = error.response?.data?.detail;

      notifications.show({
        title: error.response?.data?.title ?? 'Acesso negado',
        message: detail ?? 'Você não tem permissão para isso.',
        color: 'red',
      });
    }

    // if (error.response?.status === 500) {
    //   const detail = error.response?.data?.detail;

    //   notifications.show({
    //     title: error.response?.data?.title ?? 'Erro no Servidor',
    //     message: detail ?? 'Erro interno inesperado.',
    //     color: 'red',
    //   });
    // }

    return Promise.reject(error);
  },
);
