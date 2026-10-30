import { api } from '@/lib/api/axios';
import { AtualizarProcessoRequest, CriarProcessoRequest } from '../schema';
import { ListarProcessosParams, Processo, ProcessoListItem } from '../types';
import { PageResponse } from '@/types/pageResponse';

export const processosService = {
  listar: async ({
    pageIndex,
    pageSize,
    termo,
    areaDireito,
    tribunal,
    status,
    sort,
  }: ListarProcessosParams): Promise<PageResponse<ProcessoListItem>> => {
    const { data } = await api.get('/processos', {
      params: {
        page: pageIndex,
        size: pageSize,
        termo: termo,
        areaDireito: areaDireito,
        tribunal: tribunal,
        status: status,
        sort: sort,
      },
    });
    return data;
  },

  buscarPorId: async (id: number): Promise<Processo> => {
    const { data } = await api.get(`/processos/${id}`);
    return data;
  },

  criar: async (dados: CriarProcessoRequest): Promise<Processo> => {
    const { data } = await api.post('/processos', dados);
    return data;
  },

  atualizar: async (
    id: number,
    dados: AtualizarProcessoRequest,
  ): Promise<Processo> => {
    const { data } = await api.put(`/processos/${id}`, dados);
    return data;
  },
};
