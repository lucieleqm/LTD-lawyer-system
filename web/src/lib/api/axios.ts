import axios from 'axios';
import { getToken, getTokenTipo } from '../storage/tokenStorage';

export const api = axios.create({
  baseURL: import.meta.env.VITE_API,
  paramsSerializer: {
    indexes: null,
  },
  timeout: 10000,
});

api.interceptors.request.use((config) => {
  const token = getToken();
  const tipo = getTokenTipo();
  if (token && config.headers) {
    config.headers.Authorization = `${tipo} ${token}`;
  }
  return config;
});
