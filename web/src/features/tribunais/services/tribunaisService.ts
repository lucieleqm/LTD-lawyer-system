import { api } from '@/lib/api/axios';
import { Tribunal, TribunalSummary } from '../types';
import { PageResponse } from '@/types/pageResponse';
import { AtualizarTribunalRequest, CriarTribunalRequest } from '../schema';
import { Option, SliceResponse } from '@/types/sliceResponse';

interface ListarTribunaisParams {
  pageIndex: number;
  pageSize: number;
  termo?: string;
}

export const tribunaisService = {
  listar: async ({
    pageIndex,
    pageSize,
    termo,
  }: ListarTribunaisParams): Promise<PageResponse<Tribunal>> => {
    const { data } = await api.get('/tribunais', {
      params: { page: pageIndex, size: pageSize, nome: termo || undefined },
    });
    return data;
  },

  listarOpcoes: async (
    termo?: string,
  ): Promise<SliceResponse<Option>> => {
    const { data } = await api.get<SliceResponse<TribunalSummary>>(
      'tribunais/opcoes',
      {
        params: { nome: termo || undefined },
      },
    );

    return {
      ...data,
      content: data.content.map((tribunal) => ({
        value: String(tribunal.id),
        label: tribunal.nome,
      })),
    };
  },

  buscarPorId: async (id: number): Promise<Tribunal> => {
    const { data } = await api.get(`/tribunais/${id}`);
    return data;
  },

  criar: async (dados: CriarTribunalRequest): Promise<Tribunal> => {
    const { data } = await api.post('/tribunais', dados);
    return data;
  },

  atualizar: async (
    id: number,
    dados: AtualizarTribunalRequest,
  ): Promise<Tribunal> => {
    const { data } = await api.put(`/tribunais/${id}`, dados);
    return data;
  },
};
