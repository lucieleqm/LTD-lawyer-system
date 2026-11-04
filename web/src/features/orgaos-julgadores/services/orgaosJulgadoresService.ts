import { api } from '@/lib/api/axios';
import { OrgaoJulgador, OrgaoJulgadorSummary } from '../types';
import { PageResponse } from '@/types/pageResponse';
import { OrgaoJulgadorRequest } from '../schema';

interface ListarOrgaosJulgadoresParams {
  pageIndex: number;
  pageSize: number;
  termo?: string;
}

export const orgaosJulgadoresService = {
  listar: async ({
    pageIndex,
    pageSize,
    termo,
  }: ListarOrgaosJulgadoresParams): Promise<PageResponse<OrgaoJulgador>> => {
    const { data } = await api.get('/orgaos-julgadores', {
      params: { page: pageIndex, size: pageSize, nome: termo || undefined },
    });
    return data;
  },

  listarOpcoes: async (
    termo?: string,
  ): Promise<{ value: string; label: string }[]> => {
    const { data } = await api.get('orgaos-julgadores/opcoes', {
      params: { nome: termo || undefined },
    });

    return data.map((orgaoJulgador: OrgaoJulgadorSummary) => ({
      value: String(orgaoJulgador.id),
      label: orgaoJulgador.nome,
    }));
  },

  buscarPorId: async (id: number): Promise<OrgaoJulgador> => {
    const { data } = await api.get(`/orgaos-julgadores/${id}`);
    return data;
  },

  criar: async (dados: OrgaoJulgadorRequest): Promise<OrgaoJulgador> => {
    const { data } = await api.post('/orgaos-julgadores', dados);
    return data;
  },

  atualizar: async (
    id: number,
    dados: OrgaoJulgadorRequest,
  ): Promise<OrgaoJulgador> => {
    const { data } = await api.put(`/orgaos-julgadores/${id}`, dados);
    return data;
  },
};
