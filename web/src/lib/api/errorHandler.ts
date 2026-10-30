import { AxiosError } from 'axios';
import { ProblemDetail } from '@/types/problemDetail';

export interface AppError {
  status: number;
  title: string;
  message: string;
  fields: Record<string, string>;
}

export function normalizeError(error: unknown): AppError {
  if (isAxiosError(error) && error.response?.data) {
    const problem = error.response.data as ProblemDetail;

    const fields: Record<string, string> = {};
    problem.fields?.forEach((f) => {
      fields[f.field] = f.message;
    });

    return {
      status: problem.status ?? error.response.status,
      title: problem.title ?? 'Erro',
      message: problem.detail ?? 'Ocorreu um erro inesperado.',
      fields,
    };
  }

  return {
    status: 0,
    title: 'Erro de conexão',
    message: 'Não foi possível se conectar ao servidor. Verifique sua internet.',
    fields: {},
  };
}

function isAxiosError(error: unknown): error is AxiosError {
  return (error as AxiosError)?.isAxiosError === true;
}
