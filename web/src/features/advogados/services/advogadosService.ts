import { api } from '@/lib/api/axios';
import { AtualizarAdvogadoRequest, CriarAdvogadoRequest } from '../schema';
import { Advogado, AdvogadoListItem, AdvogadoOption } from '../types';
import { PageResponse } from '@/types/pageResponse';

interface ListarAdvogadosParams {
  pageIndex: number;
  pageSize: number;
  nome?: string;
}

export const advogadosService = {
  listar: async ({
    pageIndex,
    pageSize,
    nome,
  }: ListarAdvogadosParams): Promise<PageResponse<AdvogadoListItem>> => {
    const { data } = await api.get('/advogados', {
      params: { page: pageIndex, size: pageSize, termo: nome || undefined },
    });
    return data;
  },

  listarOpcoes: async (
    termo?: string,
  ): Promise<{ value: string; label: string }[]> => {
    const { data } = await api.get('advogados/opcoes', {
      params: { nome: termo || undefined },
    });

    return data.map((advogado: AdvogadoOption) => ({
      value: String(advogado.id),
      label: advogado.nome,
    }));
  },

  buscarPorId: async (id: number): Promise<Advogado> => {
    const { data } = await api.get(`/advogados/${id}`);
    return data;
  },

  criar: async (dados: CriarAdvogadoRequest): Promise<void> => {
    await api.post('/advogados', dados);
  },

  atualizar: async (
    id: number,
    dados: AtualizarAdvogadoRequest,
  ): Promise<Advogado> => {
    const { data } = await api.put(`/advogados/${id}`, dados);
    return data;
  },
};
