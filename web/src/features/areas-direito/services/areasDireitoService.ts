import { api } from '@/lib/api/axios';
import { AreaDireito, AreaDireitoSummary } from '../types';
import { PageResponse } from '@/types/pageResponse';
import { AreaDireitoRequest } from '../schema';

interface ListarAreasDireitoParams {
  pageIndex: number;
  pageSize: number;
  termo?: string;
}

export const areasDireitoService = {
  listar: async ({
    pageIndex,
    pageSize,
    termo,
  }: ListarAreasDireitoParams): Promise<PageResponse<AreaDireito>> => {
    const { data } = await api.get('/areas-direito', {
      params: { page: pageIndex, size: pageSize, nome: termo || undefined },
    });
    return data;
  },

  listarOpcoes: async (
    termo?: string,
  ): Promise<{ value: string; label: string }[]> => {
    const { data } = await api.get('areas-direito/opcoes', {
      params: { nome: termo || undefined },
    });

    return data.map((orgaoJulgador: AreaDireitoSummary) => ({
      value: String(orgaoJulgador.id),
      label: orgaoJulgador.nome,
    }));
  },

  buscarPorId: async (id: number): Promise<AreaDireito> => {
    const { data } = await api.get(`/areas-direito/${id}`);
    return data;
  },

  criar: async (dados: AreaDireitoRequest): Promise<AreaDireito> => {
    const { data } = await api.post('/areas-direito', dados);
    return data;
  },

  atualizar: async (
    id: number,
    dados: AreaDireitoRequest,
  ): Promise<AreaDireito> => {
    const { data } = await api.put(`/areas-direito/${id}`, dados);
    return data;
  },
};
