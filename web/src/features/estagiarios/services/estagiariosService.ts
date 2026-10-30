import { api } from '@/lib/api/axios';
import { Estagiario, EstagiarioListItem, EstagiarioOption } from '../types';
import { PageResponse } from '@/types/pageResponse';
import { AtualizarEstagiarioRequest, CriarEstagiarioRequest } from '../schema';

interface ListarEstagiariosParams {
  pageIndex: number;
  pageSize: number;
  termo?: string;
  sort?: string[];
}

export const estagiariosService = {
  listar: async ({
    pageIndex,
    pageSize,
    termo,
    sort,
  }: ListarEstagiariosParams): Promise<PageResponse<EstagiarioListItem>> => {
    const { data } = await api.get('/estagiarios', {
      params: {
        page: pageIndex,
        size: pageSize,
        termo: termo,
        sort: sort,
      },
    });
    return data;
  },

  listarOpcoes: async (
    termo?: string,
  ): Promise<{ value: string; label: string }[]> => {
    const { data } = await api.get('estagiarios/opcoes', {
      params: { nome: termo || undefined },
    });

    return data.map((estagiario: EstagiarioOption) => ({
      value: String(estagiario.id),
      label: estagiario.nome,
    }));
  },

  buscarPorId: async (id: number): Promise<Estagiario> => {
    const { data } = await api.get(`/estagiarios/${id}`);
    return data;
  },

  criar: async (dados: CriarEstagiarioRequest): Promise<Estagiario> => {
    const { data } = await api.post('/estagiarios', dados);
    return data;
  },

  atualizar: async (
    id: number,
    dados: AtualizarEstagiarioRequest,
  ): Promise<Estagiario> => {
    const { data } = await api.put(`/estagiarios/${id}`, dados);
    return data;
  },
};
