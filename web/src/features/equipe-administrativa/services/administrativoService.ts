import { api } from '@/lib/api/axios';
import { CriarAdministrativoRequest } from '../schema';
import { Administrativo, AdministrativoListItem } from '../types';
import { PageResponse } from '@/types/pageResponse';

interface ListarAdministrativoParams {
  pageIndex: number;
  pageSize: number;
}

export const administrativoService = {
  listar: async ({
    pageIndex,
    pageSize,
  }: ListarAdministrativoParams): Promise<
    PageResponse<AdministrativoListItem>
  > => {
    const { data } = await api.get('/administrativos', {
      params: { page: pageIndex, size: pageSize },
    });
    return data;
  },

  buscarPorId: async (id: number): Promise<Administrativo> => {
    const { data } = await api.get(`/administrativos/${id}`);
    return data;
  },

  criar: async (dados: CriarAdministrativoRequest): Promise<void> => {
    await api.post('/administrativos', dados);
  },

  // atualizar: async (
  //   id: number,
  //   dados: AtualizarAdministativoRequest,
  // ): Promise<Professor> => {
  //   const { data } = await api.put(`/professores/${id}`, dados);
  //   return data;
  // },
};
